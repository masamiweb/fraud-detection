import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DataCruncher {


    // do not modify this method - just use it to get all the Transactions that are in scope for the exercise
    public List<Transaction> readAllTransactions() throws Exception {
        return Files.readAllLines(Paths.get("src/main/resources/payments.csv"), StandardCharsets.UTF_8)
                .stream()
                .skip(1)
                .map(line -> {
                    var commaSeparatedLine = List.of(line
                            .replaceAll("'", "")
                            .split(",")
                    );
                    var ageString = commaSeparatedLine.get(2);
                    var ageInt = "U".equals(ageString) ? 0 : Integer.parseInt(ageString);
                    return new Transaction(commaSeparatedLine.get(1),
                            ageInt,
                            commaSeparatedLine.get(3),
                            commaSeparatedLine.get(4),
                            commaSeparatedLine.get(5),
                            commaSeparatedLine.get(6),
                            commaSeparatedLine.get(7),
                            Double.parseDouble(commaSeparatedLine.get(8)),
                            "1".equals(commaSeparatedLine.get(9)));
                })
                .collect(Collectors.toList()); // TODO test with and without this line JUnit benchmark, JMH, java micro-benchmark
    }

    // example
    public List<Transaction> readAllTransactionsAge0() throws Exception {
        return readAllTransactions().stream()
                .filter(transaction -> transaction.getAge() == 0)
                .collect(Collectors.toList());

    }

    // task 1
    public Set<String> getUniqueMerchantIds() throws Exception {
        return readAllTransactions().stream()
                .map(Transaction::getMerchantId)
                .collect(Collectors.toSet());
    }

    // task 2
    public long getTotalNumberOfFraudulentTransactions() throws Exception {
        return readAllTransactions().stream()
                .filter(Transaction::isFraud).count();
    }

    // task 3
    public long getTotalNumberOfTransactions(boolean isFraud) throws Exception {
            return readAllTransactions().stream()
                    .filter(transaction -> transaction.isFraud() == isFraud).count();
    }

    // task 4
    public Set<Transaction> getFraudulentTransactionsForMerchantId(String merchantId) throws Exception {
        return readAllTransactions().stream()
                .filter(transaction -> transaction.getMerchantId().equals(merchantId))
                .filter(Transaction::isFraud)
                .collect(Collectors.toSet());
    }

    // task 5
    public Set<Transaction> getTransactionsForMerchantId(String merchantId, boolean isFraud) throws Exception {
            return readAllTransactions().stream()
                    .filter(transaction -> transaction.getMerchantId().equals(merchantId))
                    .filter(transaction -> transaction.isFraud() == isFraud)
                    .collect(Collectors.toSet());
    }

    // task 6
    public List<Transaction> getAllTransactionsSortedByAmount() throws Exception {
        return readAllTransactions().stream()
                .sorted(Comparator.comparing(Transaction::getAmount))
                .collect(Collectors.toList());
    }

    // task 7
    public double getFraudPercentageForMen() throws Exception {
        return (double) (readAllTransactions().stream()
                .filter(transaction -> transaction.getGender().toLowerCase()
                        .equals("m") && transaction.isFraud()).count()) /
                (readAllTransactions().stream().filter(Transaction::isFraud).count());
    }

    // task 8
    // changed the return type to Set<String>
    // having return type of Set<Transaction> was not correct for this task - so changed to return type of Set<String>
    // because all that is asked for is the set of customer IDs, so don't need to return a Set of Transactions
    public Set<String> getCustomerIdsWithNumberOfFraudulentTransactions(int numberOfFraudulentTransactions) throws Exception {
        return readAllTransactions().stream().filter(Transaction::isFraud).collect(
                Collectors.groupingBy(
                        Transaction::getCustomerId, Collectors.counting()
                )).entrySet().stream().filter(fraud -> fraud.getValue() >= numberOfFraudulentTransactions).
                map(Map.Entry::getKey).
                collect(Collectors.toSet());
    }

    // task 9
    public Map<String, Integer> getCustomerIdToNumberOfTransactions() throws Exception {
        return readAllTransactions().stream().filter((Transaction::isFraud)).collect(
                Collectors.groupingBy(
                        Transaction::getCustomerId, Collectors.counting()
                )
        ).entrySet().stream()
                .collect(Collectors
                        .toMap(Map.Entry::getKey, stringLongEntry -> stringLongEntry.getValue().intValue()));
    }

    // task 10
    public Map<String, Double> getMerchantIdToTotalAmountOfFraudulentTransactions() throws Exception {
        return readAllTransactions().stream().filter((Transaction::isFraud)).collect(
                Collectors.groupingBy(
                        Transaction::getMerchantId, Collectors.counting()
                )
        ).entrySet().stream()
                .collect(Collectors
                        .toMap(Map.Entry::getKey, stringLongEntry -> stringLongEntry.getValue().doubleValue()));
    }

    // bonus
    public double getRiskOfFraudFigure(Transaction transaction) throws Exception {

        final String TRAINING_DATA = "src/main/resources/train.arff";
        final String TESTING_DATA = "src/main/resources/checkOneInstance.arff";
        final String MODElPATH = "src/main/resources/risk OfFraud-model.bin"; // save the model for later use in weka

        // create the test data file in the correct format and save to file
        CreateSingleInstance inst = new CreateSingleInstance();
        inst.formatInstance(transaction.getCustomerId(),
                transaction.getAge(), transaction.getGender().toUpperCase(), transaction.getMerchantId(),
                transaction.getCategory().toLowerCase(), transaction.getAmount(),TESTING_DATA );

        // load the training data
        ConverterUtils.DataSource train = new ConverterUtils.DataSource(TRAINING_DATA);
        Instances trainData = train.getDataSet();

        // set the index for the class column i.e. "fraud" column is last column in the file in this case
        trainData.setClassIndex(trainData.numAttributes() - 1);

        // create and build the classifier - using decision tree for this - see J48 in weka API
        J48 nb = new J48();
        nb.setOptions(new String[] { "-C", "0.5", "-M", "2", "-A", "-Q", "2"}); // J48 custome parameters

        // build the model
        nb.buildClassifier(trainData);

        // save the model - this can be used later - without having to run the classifier every time
        SerializationHelper.write(MODElPATH,nb);

        // now load the test data to check the classifier
        ConverterUtils.DataSource test = new ConverterUtils.DataSource(TESTING_DATA);
        Instances testData = test.getDataSet();
        // set index to last value - for the column of class attribute
        testData.setClassIndex(testData.numAttributes() - 1);

        Instance newInst;

        newInst = testData.instance(0);

        // returns probabilities array - for actual and predicted
        // predicted probability will be at array index 1
        double [] distances = nb.distributionForInstance(newInst);

        // call classifyInstance method - this returns a double value for the class (i.e. 0 or 1)
        double predictedNB = nb.classifyInstance(newInst);

        // use this returned value to get the string value of the predicted class (0 = noFraud : 1 = Fraud)
        String predString = testData.classAttribute().value((int) predictedNB);
        if (predString.equals("0"))
            return 1 - distances[1]; // i.e not a fraudulent transaction (1 - probability it IS a fraud)
        else // a fraudulent transaction
            return distances[1];

    }


}
