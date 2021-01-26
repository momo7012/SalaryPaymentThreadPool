package ir.dotin;

import ir.dotin.business.TransactionProcessor;
import ir.dotin.files.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


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


    public static BigDecimal generateRandomAmount() {
        return BigDecimal.valueOf(random.nextInt((MAX_AMOUNT - MIN_AMOUNT) + 1) + MIN_AMOUNT);
    }

    public static void main(String[] args) {
        //  ExecutorService pool = Executors.newFixedThreadPool(5);

        //  Task task = new Task();
        //  List<PaymentVO> paymentVOs = new ArrayList<>();
        //Thread thread1 = new Thread(task);
        // Thread thread2 = new Thread(task);
        //Thread thread3 = new Thread(task);
        // Thread thread4 = new Thread(task);
        // Thread thread5 = new Thread(task);

        // pool.execute(thread1);
        //  pool.execute(thread2);
        // pool.execute(thread3);
        //  pool.execute(thread4);
        // pool.execute(thread5);
//------------------------------------------------
        //List<Future<PaymentVO>> resultList = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 1; i <= 10; i++) {
            Runnable task = new Task("Task " + i);
            Thread thread1 = new Thread(task);
            Future<BalanceVO> future1 = (Future<BalanceVO>) executorService.submit(thread1);
            Thread thread2 = new Thread(task);
            Future<BalanceVO> future2 = (Future<BalanceVO>) executorService.submit(thread2);
            Thread thread3 = new Thread(task);
            Future<BalanceVO> future3 = (Future<BalanceVO>) executorService.submit(thread3);
            Thread thread4 = new Thread(task);
            Future<BalanceVO> future4 = (Future<BalanceVO>) executorService.submit(thread4);
            Thread thread5 = new Thread(task);
            Future<BalanceVO> future5 = (Future<BalanceVO>) executorService.submit(thread5);

//------------------------------------------------

            try {
                List<PaymentVO> paymentVOs = PaymentFileHandler.createPaymentFile(DEBTOR_DEPOSIT_NUMBER, CREDITOR_DEPOSIT_NUMBER_PREFIX, CREDITOR_COUNT);
                List<BalanceVO> depositBalances = BalanceFileHandler.createInitialBalanceFile(balanceVOs);
                List<TransactionVO> transactionVOS = TransactionProcessor.processPaymentRecords(depositBalances, paymentVOs);
                TransactionFileHandler.createTransactionFile(transactionVOS, depositBalances);
//------------------------------
                List<BalanceVO> depositBalances1 = BalanceFileHandler.createFinalBalanceFile( future1);
                List<BalanceVO> depositBalances2 =  BalanceFileHandler.createFinalBalanceFile(future2);
                List<BalanceVO> depositBalances3 =  BalanceFileHandler.createFinalBalanceFile( future3);
                List<BalanceVO> depositBalances4 =  BalanceFileHandler.createFinalBalanceFile( future4);
                List<BalanceVO> depositBalances5 =  BalanceFileHandler.createFinalBalanceFile(future5);
                 BalanceFileHandler.createFinalBalanceFileThreadingNew(depositBalances1,depositBalances2,depositBalances3,depositBalances4,depositBalances5);
//-----------------------------
               // BalanceFileHandler.createFinalBalanceFile(depositBalances);


            } catch (Exception e) {
                e.printStackTrace();
            }
//------------------------------------------------------------------------------------------
            // thread1.start();
            //thread2.start();
            // thread3.start();
            // thread4.start();
            // thread5.start();

            //  pool.shutdown();

        }

    }
}

