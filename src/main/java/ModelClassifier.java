import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.SerializationHelper;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ModelClassifier {

    private Attribute customer;
    private Attribute age;
    private Attribute gender;
    private Attribute merchant;
    private Attribute category;
    private Attribute amount;

    private ArrayList<Attribute> attributes;
    private ArrayList<String> classVal;
    private Instances dataRaw;


    public ModelClassifier() {
        customer = new Attribute  ("customer"); //
        age = new Attribute("age");
        gender = new Attribute("gender");
        merchant = new Attribute("merchant");
        category = new Attribute("category");
        amount = new Attribute("amount");


        attributes = new ArrayList<>();
        classVal = new ArrayList<>();
        classVal.add("0");
        classVal.add("1");

        attributes.add(customer);
        attributes.add(age);
        attributes.add(gender);
        attributes.add(merchant);
        attributes.add(category);
        attributes.add(amount);

        attributes.add(new Attribute("fraud", classVal));
        dataRaw = new Instances("TestInstances", attributes, 0);
        dataRaw.setClassIndex(dataRaw.numAttributes() - 1);
    }


    public Instances createInstance(double age, double gender, double category, double amount, double result) {
        dataRaw.clear();
        double[] instanceValue1 = new double[]{age, gender, category, amount, 9};
        dataRaw.add(new DenseInstance(1.0, instanceValue1));
        return dataRaw;
    }


    public String classifiy(Instances insts, String path) {
        String result = "Not classified!!";
        Classifier cls = null;
        try {
            cls = (NaiveBayes) SerializationHelper.read(path);
            result = (String) classVal.get((int) cls.classifyInstance(insts.firstInstance()));
        } catch (Exception ex) {
            Logger.getLogger(ModelClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }


    public Instances getInstance() {
        return dataRaw;
    }


}
