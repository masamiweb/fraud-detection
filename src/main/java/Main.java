import java.util.List;

public class Main {

    private List<Transaction> lt;
    public static void main(String[] args) {


    }

    public void getTransactionsList(){
        DataCruncher dc = new DataCruncher();
        try {
            lt = dc.readAllTransactions();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
