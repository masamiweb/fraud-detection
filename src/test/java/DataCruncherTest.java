import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

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
        // check the list is sorted in ascending order of Amount
        assertTrue(isSorted(allTransactionsSortedByAmount, Transaction::getAmount));

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
        Set<String> customerIdsWithNumberOfFraudulentTransactions = dataCruncher.getCustomerIdsWithNumberOfFraudulentTransactions(3);
        assertTrue(checkTwoSetsAreIdentical(customerIdsWithNumberOfFraudulentTransactions, 3));

        customerIdsWithNumberOfFraudulentTransactions = dataCruncher.getCustomerIdsWithNumberOfFraudulentTransactions(0);
        assertTrue(checkTwoSetsAreIdentical(customerIdsWithNumberOfFraudulentTransactions, 0));

    }

    // task9
    @Test
    public void getCustomerIdToNumberOfTransactions() throws Exception {
        Map<String, Integer> customerIdToNumberOfTransactions = dataCruncher.getCustomerIdToNumberOfTransactions();
        assertTrue(checkTwoSetsAreIdenticalMerchantIDInteger(customerIdToNumberOfTransactions));
    }

    // task10
    @Test
    public void getMerchantIdToTotalAmountOfFraudulentTransactions() throws Exception {
        Map<String, Double> merchantIdToTotalAmountOfFraudulentTransactions = dataCruncher.getMerchantIdToTotalAmountOfFraudulentTransactions();
        assertTrue(checkTwoSetsAreIdenticalMerchantIDDouble(merchantIdToTotalAmountOfFraudulentTransactions));
    }


    // task11 bonus task
    // This test will check a valid probability is returned i.e is within range of 0 to 1
    @Test
    public void getRiskOfFraudFigure() throws Exception {
        String customerId = "C2054744914";
        int age = 2;
        String gender = "M";
        String customerZipCode = "28007";
        String merchantId = "M348934600";
        String merchantZipCode = "28007";
        String category = "es_health";
        double amount = 3.72;
        boolean isFraud = true;

        assertEquals(0.50, dataCruncher
                .getRiskOfFraudFigure(new Transaction(customerId, age, gender, customerZipCode, merchantId, merchantZipCode, category, amount, isFraud)), 0.50);

    }

    // ########################################################################################################## //
    /** HELPER METHODS USED BY THE TESTS **/

    // test 8 helper method
    // check both Sets are equal or not
    private boolean checkTwoSetsAreIdentical(Set<String> returnedSet, int numberOfFraudulentTransactions) throws Exception {

        Set<String> toCheck = new HashSet<>();

        Map<String, Integer> allCustomersAndFraudTransactionTotals = dataCruncher.readAllTransactions()
                .stream().filter((Transaction::isFraud)).collect(
                        Collectors.groupingBy(
                                Transaction::getCustomerId, Collectors.counting()
                        )
                ).entrySet().stream()
                .collect(Collectors
                        .toMap(Map.Entry::getKey, stringLongEntry -> stringLongEntry
                                .getValue().intValue()));


        allCustomersAndFraudTransactionTotals.forEach((k, v) -> {
            if (v >= numberOfFraudulentTransactions)
                toCheck.add(k);
        });

        // returns true if both sets are same size AND contain the same values exactly
        return returnedSet.equals(toCheck);

    }

    // test 9 helper method
    // check both Maps are equal or not - make it generic to handle any type of key and value
    private boolean checkTwoSetsAreIdenticalMerchantIDInteger(Map<String, Integer> toCheck) throws Exception {

        Map<String, Integer> allCustomersAndFraudTransactionTotals = dataCruncher.readAllTransactions()
                .stream().filter((Transaction::isFraud)).collect(
                        Collectors.groupingBy(
                                Transaction::getCustomerId, Collectors.counting()
                        )
                ).entrySet().stream()
                .collect(Collectors
                        .toMap(Map.Entry::getKey, stringLongEntry -> stringLongEntry
                                .getValue().intValue()));


        // returns true if both Maps are same size AND contain the same values exactly
        return toCheck.equals(allCustomersAndFraudTransactionTotals);

    }

    // test 10 helper method
    // check both Maps are equal or not - make it generic to handle any type of key and value
    private boolean checkTwoSetsAreIdenticalMerchantIDDouble(Map<String, Double> toCheck) throws Exception {

        Map<String, Double> allMerchantAndFraudTransactionTotals = dataCruncher.readAllTransactions()
                .stream().filter((Transaction::isFraud)).collect(
                        Collectors.groupingBy(
                                Transaction::getMerchantId, Collectors.counting()
                        )
                ).entrySet().stream()
                .collect(Collectors
                        .toMap(Map.Entry::getKey, stringLongEntry -> stringLongEntry
                                .getValue().doubleValue()));

        // returns true if both Maps are same size AND contain the same values exactly
        return toCheck.equals(allMerchantAndFraudTransactionTotals);
    }


    // test 6 helper method
    private  <T, R extends Comparable<? super R>> boolean isSorted(List<T> list, Function<T, R> f) {
        Comparator<T> comp = Comparator.comparing(f);
        for (int i = 0; i < list.size() - 1; ++i) {
            T left = list.get(i);
            T right = list.get(i + 1);
            if (comp.compare(left, right) > 0) { // check if left greater than right
                return false;
            }
        }
        return true;
    }


}