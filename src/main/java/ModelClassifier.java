import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ModelClassifier {

   // private Attribute customer;
    private Attribute age;
    private Attribute gender;
    private Attribute merchant;
    private Attribute category;
    private Attribute amount;

    private ArrayList<Attribute> attributes;
    private ArrayList<String> classVal;
    private Instances dataRaw;


    public ModelClassifier() {
        //customer = new Attribute  ("customer"); //
        age = new Attribute("age");
        gender = new Attribute("gender");
        merchant = new Attribute("merchant");
        category = new Attribute("category");
        amount = new Attribute("amount");


        attributes = new ArrayList<>();
        classVal = new ArrayList<>();
        classVal.add("0");
        classVal.add("1");

        //attributes.add(customer);
        attributes.add(age);
        attributes.add(gender);
        attributes.add(merchant);
        attributes.add(category);
        attributes.add(amount);



        attributes.add(new Attribute("fraud", classVal));
        dataRaw = new Instances("TestInstances", attributes, 0);
        dataRaw.setClassIndex(dataRaw.numAttributes() - 1);
    }

//4,M,M348934600,es_transportation,4.55,0
    public Instances createInstance(double age, String gender, String merchant, String category, double amount) {
        dataRaw.clear();
        Instance inst = new DenseInstance(6);



        inst.setValue(this.age, age);
        inst.setValue(this.gender, gender);
        inst.setValue(this.merchant, merchant);
        inst.setValue(this.category, category);
        inst.setValue(this.amount, amount);
        inst.setClassMissing();

        inst.setDataset(dataRaw);

        dataRaw.add(inst);




        return dataRaw;
    }


    public String classifyOneInstance(Instances insts, String path) {
        String result = "Not classified!!";
        Classifier cls = null;
        try {
            cls = (J48) SerializationHelper.read(path);
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
