public class Main {
    /**
     * quick test to check the returned probability value after training classfifier
     *
     */
    public static void main(String[] args) throws Exception {
        Main m = new Main();

        m.getTransactionsList();

    }

    public void getTransactionsList() throws Exception {
        DataCruncher dc = new DataCruncher();


        // pass in all the data needed minus the last field from a transaction and check probability it was fraudulent or not
        String category = "es_transportation";
        boolean isFraud = true;
        double amount = 24.23;
        String merchantZipCode = "28007";
        String merchantId = "M1823072687";
        String customerZipCode = "28007";
        String gender = "F";
        int age = 4;
        String customerId = "C564451627";
        double probability = dc.getRiskOfFraudFigure(new Transaction(customerId, age, gender, customerZipCode, merchantId, merchantZipCode, category, amount, isFraud));

        System.out.println("Probability of fraud: " + probability);

    }
}
