package ir.dotin;

import ir.dotin.files.BalanceVO;
import ir.dotin.files.PaymentVO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static ir.dotin.PaymentTransactionApp.FILE_PATH_PREFIX;
import static ir.dotin.PaymentTransactionApp.balanceVOs;

public class Task implements Runnable {
    List<PaymentVO> paymentVOs = new ArrayList<>();

    public static String createFinalBalanceFileThreading (List < BalanceVO > depositBalances) throws IOException {

        String resultFinalBalance = "";
        Path pathBalanceUpdate = Paths.get(FILE_PATH_PREFIX + "BalanceUpdateThreading.txt");
        Files.createFile(pathBalanceUpdate);
        writeFinalBalanceVOToFileThreading(balanceVOs);
        resultFinalBalance += PaymentTransactionApp.DEBTOR_DEPOSIT_NUMBER + "\t" + PaymentTransactionApp.CREDITOR_DEPOSIT_NUMBER_PREFIX + "\t" + depositBalances + "\n";
        printBalanceVOsToConsole(balanceVOs);
        return resultFinalBalance;
    }

    private static void writeFinalBalanceVOToFileThreading (List < BalanceVO > balanceVOs) throws IOException {
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

    public static String createFinalBalanceFileThreadingNew (List < BalanceVO > depositBalances) throws IOException
    {

        File dir = new File(FILE_PATH_PREFIX + "BalanceUpdateThreading.txt");

        PrintWriter pw = new PrintWriter("BalanceUpdateThreadingNew.txt");

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
    }




    //==============================================================
    @Override
    public void run () {
         for (PaymentVO paymentVO : paymentVOs) {
        List<PaymentVO> th1 = paymentVOs.subList(1, 201);
        List<PaymentVO> th2 = paymentVOs.subList(201, 401);
        List<PaymentVO> th3 = paymentVOs.subList(401, 601);
        List<PaymentVO> th4 = paymentVOs.subList(601, 801);
        List<PaymentVO> th5 = paymentVOs.subList(801, 1000);

         }
        for (BalanceVO balanceVO : balanceVOs) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


}
