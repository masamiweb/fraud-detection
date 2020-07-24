import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;

import java.text.DecimalFormat;


public class Test {




    private static DecimalFormat df2 = new DecimalFormat("#.##");
    public static final String TRAINING_DATA = "src/main/resources/train.arff";
    public static final String TESTING_DATA = "src/main/resources/checkOneInstance.arff";

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
        J48 nb = new J48();
        nb.setOptions(new String[] { "-C", "0.5", "-M", "2", "-A", "-Q", "2"});

        nb.buildClassifier(trainData);

        // save the model - so we can then use it to test single instances
        SerializationHelper.write(MODElPATH,nb);

        // now load the test data to check the classifier
        DataSource test = new DataSource(TESTING_DATA);
        Instances testData = test.getDataSet();
        // set index to last value - for the column of class attribute
        testData.setClassIndex(testData.numAttributes() - 1);

        Instance newInst;

        System.out.println("====================================");
        System.out.println("Predicted         Probability");

            newInst = testData.instance(0);

            double [] distances = nb.distributionForInstance(newInst);
            // call classifyInstance method - this returns a double value for the class
            double predictedNB = nb.classifyInstance(newInst);


            // use this returned value to get the string value of the predicted class - i.e. 0 for n fraud
            // and 1 for fraud
            String predString = testData.classAttribute().value((int) predictedNB);
            if (predString.equals("0"))
                predString = "(0) No Fraud";
            else
                predString = "(1) Fraud";
            System.out.println(predString + "               " + df2.format(distances[1]));


    }

}
