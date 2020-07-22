import java.util.List;

public class Main {

    private List<Transaction> lt;

    private final String customerId = "C2054744914";
    private final int age = 4;
    private final String gender = "F";
    private final String customerZipCode = "28007";
    private final String merchantId = "M348934600";
    private final String merchantZipCode = "28007";
    private final String category = "es_transportation";
    private final double amount = 35.72;
    private final boolean isFraud = true;



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
        //System.out.println("MAP: " + dc.getCustomerIdsWithNumberOfFraudulentTransactions(3).size());

        dc.getRiskOfFraudFigure(new Transaction(customerId, age, gender, customerZipCode, merchantId, merchantZipCode, category, amount, isFraud));


    }
}
