import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DataCruncherTest {
    private final DataCruncher dataCruncher = new DataCruncher();

    // ignore
    @Test
    public void readAllTransactions() throws Exception {
        var transactions = dataCruncher.readAllTransactions();
        assertEquals(594643, transactions.size());
    }

    // example
    @Test
    public void readAllTransactionsAge0() throws Exception {
        var transactions = dataCruncher.readAllTransactionsAge0();
        assertEquals(3630, transactions.size());
    }

    // task1
    @Test
    public void getUniqueMerchantIds() throws Exception {
        var transactions = dataCruncher.getUniqueMerchantIds();
        assertEquals(50, transactions.size());
    }

    // task2
    @Test
    public void getTotalNumberOfFraudulentTransactions() throws Exception {
        var totalNumberOfFraudulentTransactions = dataCruncher.getTotalNumberOfFraudulentTransactions();
        assertEquals(297508, totalNumberOfFraudulentTransactions);
    }

    // task3
    @Test
    public void getTotalNumberOfTransactions() throws Exception {
        assertEquals(297508, dataCruncher.getTotalNumberOfTransactions(true));
        assertEquals(297135, dataCruncher.getTotalNumberOfTransactions(false));
    }

    // task4
    @Test
    public void getFraudulentTransactionsForMerchantId() throws Exception {
        Set<Transaction> fraudulentTransactionsForMerchantId = dataCruncher.getFraudulentTransactionsForMerchantId("M1823072687");
        assertEquals(149001, fraudulentTransactionsForMerchantId.size());
    }

    // task5
    @Test
    public void getTransactionForMerchantId() throws Exception {
        assertEquals(102588, dataCruncher.getTransactionsForMerchantId("M348934600", true).size());
        assertEquals(102140, dataCruncher.getTransactionsForMerchantId("M348934600", false).size());
    }

    // task6
    @Test
    public void getAllTransactionSortedByAmount() throws Exception {
        List<Transaction> allTransactionsSortedByAmount = dataCruncher.getAllTransactionsSortedByAmount();
        fail();
    }

    // task7
    @Test
    public void getFraudPercentageForMen() throws Exception {
        double fraudPercentageForMen = dataCruncher.getFraudPercentageForMen();
        assertEquals(0.45, fraudPercentageForMen, 0.01);
    }

    // task8
    @Test
    public void getCustomerIdsWithNumberOfFraudulentTransactions() throws Exception {
        Set<Transaction> customerIdsWithNumberOfFraudulentTransactions = dataCruncher.getCustomerIdsWithNumberOfFraudulentTransactions(3);
        fail();
    }

    // task9
    @Test
    public void getCustomerIdToNumberOfTransactions() throws Exception {
        Map<String, Integer> customerIdToNumberOfTransactions = dataCruncher.getCustomerIdToNumberOfTransactions();
        fail();
    }

    // task10
    @Test
    public void getMerchantIdToTotalAmountOfFraudulentTransactions() throws Exception {
        Map<String, Double> merchantIdToTotalAmountOfFraudulentTransactions = dataCruncher.getMerchantIdToTotalAmountOfFraudulentTransactions();
        fail();
    }
}