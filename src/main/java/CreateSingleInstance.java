import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;

public class CreateSingleInstance {

    private Attribute customer;
    private Attribute age;
    private Attribute gender;
    private Attribute merchant;
    private Attribute category;
    private Attribute amount;

    private ArrayList<Attribute> attributes;
    private ArrayList<String> classVal;
    private Instances dataRaw;

    public CreateSingleInstance(){

    }

    public Instance formatInstance(String c, int a, String g, String m, String cat, double amt){

        ArrayList<String> attributes = null;
        customer = new Attribute("customer",0);
        age = new Attribute("age" ,1);
        gender = new Attribute("gender", 2);
        merchant = new Attribute("merchant", 3);
        category = new Attribute("category", 4);
        amount = new Attribute("amount", 5);


        Instance inst = new DenseInstance(7);

        inst.setValue(customer,"C251602943" );
        inst.setValue(age, a);
        inst.setValue(gender, g);
        inst.setValue(merchant, m);
        inst.setValue(category, cat);
        inst.setValue(amount, amt);

        inst.setClassMissing();
        return inst;

        // C251602943,2,F,M348934600,es_transportation,13.08,1
    }
}
