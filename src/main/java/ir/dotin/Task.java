package ir.dotin;

import ir.dotin.files.BalanceVO;

import java.io.*;
import java.util.List;

public class Task implements Runnable {

    @Override
    public void run() {
        File file = new File(PaymentTransactionApp.BALANCE_FILE_PATH);
//creates a buffer reader input stream
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            int r = 0;
            while (true) {

                if (!((r = br.read()) != -1)) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void writeFinalBalanceVOToFile(List<BalanceVO> balanceVOs) throws IOException {
        PrintWriter printWriter = new PrintWriter(PaymentTransactionApp.BALANCE_UPDATE_FILE_PATH);
        for (BalanceVO balanceVO : balanceVOs) {
            printWriter.println(balanceVO.toString());
        }
        printWriter.close();
    }
}
//============================================================



    /*
    //Parallel
    static Map<String, BigDecimal> lastState;
    static Map<String, BigDecimal> map;

    public Task(Map<String, BigDecimal> map) {
        this.map = map;
        this.lastState = new HashMap<>(map); }
    @Override
    public void run() {
        Map<String, BigDecimal> currentState = new HashMap<>(map);
        if(currentState.equals(lastState)) {
            System.out.println("No change");
        } else {
            System.out.println("Change!");
        }lastState.clear();
        lastState.putAll(currentState);
    }
    }
//-------------------------------------------------------------------

    /*
    private String taskName;

   public Task(String taskName) {
        super();
        this.taskName = taskName;
    }*/
   /* List<PaymentVO> paymentVOs;
    public Task(List<PaymentVO> paymentVOs){
        this.paymentVOs = paymentVOs;
    }*/
  /* List<PaymentVO> paymentVOs = new ArrayList<>();
    @Override
    public void run() {
        System.out.println("Starting "+taskName);
        for (int i = 1; i <= 10; i++) {
            System.out.println("Executing "+taskName+" with "+Thread.currentThread().getName()+"===="+i);
        }
        System.out.println("Ending "+taskName);
        System.out.println(Thread.currentThread().getName());
    }

}
*/
//----------------------------------------------------------------------
   /* List<PaymentVO> paymentVOs = new ArrayList<>();

    public static String createFinalBalanceFileThreading(List<BalanceVO> depositBalances) throws IOException {

        String resultFinalBalance = "";
        Path pathBalanceUpdate = Paths.get(FILE_PATH_PREFIX + "BalanceUpdateThreading.txt");
        Files.createFile(pathBalanceUpdate);
        writeFinalBalanceVOToFileThreading(balanceVOs);
        resultFinalBalance += PaymentTransactionApp.DEBTOR_DEPOSIT_NUMBER + "\t" + PaymentTransactionApp.CREDITOR_DEPOSIT_NUMBER_PREFIX + "\t" + depositBalances + "\n";
        printBalanceVOsToConsole(balanceVOs);
        return resultFinalBalance;
    }

    private static void writeFinalBalanceVOToFileThreading(List<BalanceVO> balanceVOs) throws IOException {
        PrintWriter printWriter = new PrintWriter(PaymentTransactionApp.BALANCE_UPDATE_FILE_PATH);
        for (BalanceVO balanceVO : balanceVOs) {
            printWriter.println(balanceVO.toString());
        }
        printWriter.close();
    }

    private static void printBalanceVOsToConsole(List<BalanceVO> balanceVOS) {
        System.out.println("*********************** BALANCETHREAD *************************");
        for (BalanceVO balanceVO : balanceVOS)
            System.out.println(balanceVO.toString());
        System.out.println("***********************************************************");
    }
//==============================================================
*//*
    public static String createFinalBalanceFileThreadingNew(List<BalanceVO> depositBalances) throws IOException {

        File dir = new File(FILE_PATH_PREFIX + "BalanceUpdateThreading.txt");

        PrintWriter pw = new PrintWriter("BalanceUpdate.txt");

        String[] fileNames = dir.list();

        for (String fileName : fileNames != null ? fileNames : new String[0]) {
            System.out.println("Reading from " + fileName);

            File f = new File(dir, fileName);

            BufferedReader br = new BufferedReader(new FileReader(f));
            pw.println("Contents of file " + fileName);

            String line = br.readLine();
            while (line != null) {

                pw.println(line);
                line = br.readLine();
            }
            pw.flush();
        }


        System.out.println("Reading from all files" +
                " in directory " + dir.getName() + " Completed");
        return dir.getName();
    }*//*


    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }

           *//* for (BalanceVO balanceVO : balanceVOs) {
                try {


                    List<BalanceVO> balance1 = balanceVOs.subList(0, 200);
                    {
                        createFinalBalanceFileThreading(balance1);
                    }
                    List<BalanceVO> balance2 = balanceVOs.subList(200, 400);
                    {
                        createFinalBalanceFileThreading(balance2);
                    }
                    List<BalanceVO> balance3 = balanceVOs.subList(400, 600);
                    {
                        createFinalBalanceFileThreading(balance3);
                    }
                    List<BalanceVO> balance4 = balanceVOs.subList(600, 800);
                    {
                        createFinalBalanceFileThreading(balance4);
                    }
                    List<BalanceVO> balance5 = balanceVOs.subList(800, 1000);
                    {
                        createFinalBalanceFileThreading(balance5);
                    }

                    createFinalBalanceFileThreadingNew(balanceVOs);
*//*
 *//*  } catch (IOException e) {
                    e.printStackTrace();

                }*//*
          //  }



}
*/