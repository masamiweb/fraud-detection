import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;


public class Test {

    public static final String TRAINING_DATA = "src/main/resources/train.arff";
    public static final String TESTING_DATA = "src/main/resources/test.arff";

    public static final String MODElPATH = "src/main/resources/pay.bin";

    public static void main(String[] args) throws Exception {

        // load training data
        DataSource train = new DataSource(TRAINING_DATA);
        Instances trainData = train.getDataSet();

        // set the index for the class column i.e. fraud column
        // this should always be the last column in the data file
        trainData.setClassIndex(trainData.numAttributes() - 1);

        // get the number of classes
        int numClasses = trainData.numClasses();

        // print out class values in the training data
        for (int i = 0; i < numClasses; i++ ){
            String classValue = trainData.classAttribute().value(i);
            System.out.println("Class Value at " + i + " is " + classValue);
        }

        // creat and build the classifier
        NaiveBayes nb = new NaiveBayes();
        nb.buildClassifier(trainData);

        // now load the test data to check the classifier
        DataSource test = new DataSource(TESTING_DATA);
        Instances testData = test.getDataSet();
        // set index to last value - for the column of class attribute
        testData.setClassIndex(testData.numAttributes() - 1);

        // loop through the test data to make predictions
        int numOfTestDataLines = testData.numInstances();
        double actualClass;
        String actual;
        Instance newInst;
        System.out.println("====================================");
        System.out.println("Actual Class,  NB Predicted,         Probability");
        for (int i =0; i < 10; i++ ){
            // get class double value for current instance
            actualClass = testData.instance(i).classValue();
            actual = testData.classAttribute().value((int)actualClass);

            // get instance object of current  instance
            newInst = testData.instance(i);

            // call classifyInstance method - this returns a double value for the class
            double predictedNB = nb.classifyInstance(newInst);


            // use this retuned value to get the string value of the predicted class - i.e. 0 for n fraud
            // and 1 for fraud
            String predString = testData.classAttribute().value((int) predictedNB);
            System.out.println(actual + "                  " + predString + "               " + nb.getClassEstimator().getProbability(predictedNB));


        }

    }

}
