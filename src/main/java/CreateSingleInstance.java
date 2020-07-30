import java.io.*;

public class CreateSingleInstance {


    public CreateSingleInstance(){

    }

    // this is not great coding - but until I figure out the weka API for creating a single instance for a J48 decision tree
    // this will be ok for testing - will create one by hand and save to file for testing purposes
    public void formatInstance(String customer, int age, String gender, String merchant, String category, double amount, String filePath) throws IOException {

        File fout = new File(filePath);
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        bw.write("@relation test");
        bw.newLine();

        bw.write("@attribute customer {"+customer+"}");
        bw.newLine();

        bw.write("@attribute age {"+age+"}");
        bw.newLine();

        bw.write("@attribute gender {F,M,U}");
        bw.newLine();

        bw.write("@attribute merchant {"+merchant+"}");
        bw.newLine();

        bw.write("@attribute category {es_transportation,es_wellnessandbeauty,es_food,es_fashion,es_otherservices,es_hyper,es_health,es_sportsandtoys,es_home,es_barsandrestaurants,es_contents,es_tech,es_hotelservices,es_leisure,es_travel}");
        bw.newLine();

        bw.write("@attribute amount {"+amount+"}");
        bw.newLine();

        bw.write("@attribute fraud {0,1}");
        bw.newLine();

        bw.write("@data "+customer+","+age+","+gender+","+merchant+","+category+","+amount+","+"?");

        bw.close();
    }
}
