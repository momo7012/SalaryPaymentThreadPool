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
    // int partitionSize = 100;
    // final ArrayList counter = new ArrayList();
    //create Final Balance File Threading
//================================================================
    public static String createFinalBalanceFileThreading (List < BalanceVO > depositBalances) throws IOException {

        String resultFinalBalance = "";
        Path pathBalanceUpdate = Paths.get(FILE_PATH_PREFIX + "BalanceUpdateThreading.txt");
        Files.createFile(pathBalanceUpdate);
        writeFinalBalanceVOToFileThreading(balanceVOs);
        resultFinalBalance += PaymentTransactionApp.DEBTOR_DEPOSIT_NUMBER + "\t" + PaymentTransactionApp.CREDITOR_DEPOSIT_NUMBER_PREFIX + "\t" + depositBalances + "\n";
        // printFinalBalanceVOsToConsole(balanceVOs);
        return resultFinalBalance;
    }

    private static void writeFinalBalanceVOToFileThreading (List < BalanceVO > balanceVOs) throws IOException {
        PrintWriter printWriter = new PrintWriter(PaymentTransactionApp.BALANCE_UPDATE_FILE_PATH);
        for (BalanceVO balanceVO : balanceVOs) {
            printWriter.println(balanceVO.toString());
        }
        printWriter.close();
    }

//==============================================================
// Java program to merge all files of a directory

    public static String createFinalBalanceFileThreadingNew (List < BalanceVO > depositBalances) throws IOException
    {

        // create instance of directory
        File dir = new File(FILE_PATH_PREFIX + "BalanceUpdateThreading.txt");

        // create obejct of PrintWriter for output file
        PrintWriter pw = new PrintWriter("BalanceUpdateThreadingNew.txt");

        // Get list of all the files in form of String Array
        String[] fileNames = dir.list();

        // loop for reading the contents of all the files
        // in the directory GeeksForGeeks
        for (String fileName : fileNames != null ? fileNames : new String[0]) {
            System.out.println("Reading from " + fileName);

            // create instance of file from Name of
            // the file stored in string Array
            File f = new File(dir, fileName);

            // create object of BufferedReader
            BufferedReader br = new BufferedReader(new FileReader(f));
            pw.println("Contents of file " + fileName);

            // Read from current file
            String line = br.readLine();
            while (line != null) {

                // write to the output file
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
        // for (PaymentVO paymentVO : paymentVOs) {
        List<PaymentVO> th1 = paymentVOs.subList(1, 201);
        List<PaymentVO> th2 = paymentVOs.subList(201, 401);
        List<PaymentVO> th3 = paymentVOs.subList(401, 601);
        List<PaymentVO> th4 = paymentVOs.subList(601, 801);
        List<PaymentVO> th5 = paymentVOs.subList(801, 1000);

        // }
        // for (BalanceVO balanceVO : balanceVOs) {
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

//}
