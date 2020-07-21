import java.util.List;

public class Main {

    private List<Transaction> lt;
    public static void main(String[] args) throws Exception {
        Main m = new Main();
        m.getTransactionsList();

    }

    public void getTransactionsList() throws Exception {
        DataCruncher dc = new DataCruncher();
//        try {
//            lt = dc.readAllTransactions();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        lt = dc.getAllTransactionsSortedByAmount();
//
//        for (int i = 0; i < lt.size(); i++){
//            System.out.println(lt.get(i));
//        }
        //System.out.println("Customer ID and total: " + dc.getMerchantIdToTotalAmountOfFraudulentTransactions());
        System.out.println("MAP: " + dc.getCustomerIdsWithNumberOfFraudulentTransactions(3).size());


    }
}
