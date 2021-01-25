package ir.dotin;

import ir.dotin.business.TransactionProcessor;
import ir.dotin.files.*;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PaymentTransactionApp {
    public static final String FILE_PATH_PREFIX = "E://";
    public static final String BALANCE_FILE_PATH = FILE_PATH_PREFIX + "Balance.txt";
    public static final String PAYMENT_FILE_PATH = FILE_PATH_PREFIX + "Payment.txt";
    public static final String TRANSACTION_FILE_PATH = FILE_PATH_PREFIX + "Transactions.txt";
    public static final String BALANCE_UPDATE_FILE_PATH = FILE_PATH_PREFIX + "BalanceUpdate.txt";
    public static final String DEBTOR_DEPOSIT_NUMBER = "1.10.100.1";
    public static final String CREDITOR_DEPOSIT_NUMBER_PREFIX = "1.20.100.";
    public static List<BalanceVO> balanceVOs = new ArrayList<>();
    private static final int CREDITOR_COUNT = 1000;
    private static final int MIN_AMOUNT = 100;
    private static final int MAX_AMOUNT = 10000;
    private static final Random random = new Random();
    //-------------------------------------------------
    private static Runnable th1;
    private static Runnable th2;
    private static Runnable th3;
    private static Runnable th4;
    private static Runnable th5;
    //----------------------------------------------------------------------------
    //ok
    public static BigDecimal generateRandomAmount() {
        return BigDecimal.valueOf(random.nextInt((MAX_AMOUNT - MIN_AMOUNT) + 1) + MIN_AMOUNT);
    }

    public static void main(String[] args) {
//------------------------------------------------------
        Task task = new Task();

        Thread thread1 = new Thread(th1);
        Thread thread2 = new Thread(th2);
        Thread thread3 = new Thread(th3);
        Thread thread4 = new Thread(th4);
        Thread thread5 = new Thread(th5);

        {
//--------------------------------------------------------------------------------------
            //ok
            try {
                List<PaymentVO> paymentVOs = PaymentFileHandler.createPaymentFile(DEBTOR_DEPOSIT_NUMBER, CREDITOR_DEPOSIT_NUMBER_PREFIX, CREDITOR_COUNT);
                List<BalanceVO> depositBalances = BalanceFileHandler.createInitialBalanceFile(balanceVOs);
                List<TransactionVO> transactionVOS = TransactionProcessor.processPaymentRecords(depositBalances, paymentVOs);
                BalanceFileHandler.createFinalBalanceFile(depositBalances);
                Task.createFinalBalanceFileThreadingNew(depositBalances);
                TransactionFileHandler.createTransactionFile(transactionVOS, depositBalances);

            } catch (Exception e) {
                e.printStackTrace();
            }
            ExecutorService executor = Executors.newFixedThreadPool(5);
            //  while (true) {

            executor.execute(thread1);
            executor.execute(thread2);
            executor.execute(thread3);
            executor.execute(thread4);
            executor.execute(thread5);
            System.out.println(Thread.currentThread().getName());

            //}

        }
    }

}
