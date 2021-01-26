package ir.dotin.files;

import ir.dotin.PaymentTransactionApp;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Future;

import static ir.dotin.PaymentTransactionApp.FILE_PATH_PREFIX;
import static ir.dotin.PaymentTransactionApp.balanceVOs;

public class BalanceFileHandler {


    public static List<BalanceVO> createInitialBalanceFile(List<BalanceVO> balanceVOs) throws IOException {
//--------------------------------------------------------------------------
        //To Test Transaction Processor
        String input1 = "10099999999900000";
        BigDecimal b = new BigDecimal(input1);
        BigDecimal a = PaymentTransactionApp.generateRandomAmount().add(b);
        balanceVOs.add(new BalanceVO(PaymentTransactionApp.DEBTOR_DEPOSIT_NUMBER, a));
//---------------------------------------------------------------------------
        // balanceVOs.add(new BalanceVO(PaymentTransactionApp.DEBTOR_DEPOSIT_NUMBER, PaymentTransactionApp.generateRandomAmount()));
        for (int i = 1; i <= 1000; i++) {
            balanceVOs.add(new BalanceVO(PaymentTransactionApp.CREDITOR_DEPOSIT_NUMBER_PREFIX + i, PaymentTransactionApp.generateRandomAmount()));
        }
        writeBalanceVOToFile(balanceVOs);
        printBalanceVOsToConsole(balanceVOs);
        return balanceVOs;
    }
//---------------------------------------------------------------

    public static List<BalanceVO> createFinalBalanceFileThreadPool(List<BalanceVO> depositBalances) throws IOException {
        String resultFinalBalance = "";
        List<BalanceVO> balance1 = balanceVOs.subList(1, 200);
        {
            writeBalanceVOToFile(balance1);
            printFinalBalanceVOsToConsole(balance1);

        }

        List<BalanceVO> balance2 = balanceVOs.subList(200, 400);
        {
            writeBalanceVOToFile(balance2);
            printFinalBalanceVOsToConsole(balance2);

        }

        List<BalanceVO> balance3 = balanceVOs.subList(400, 600);
        {
            writeBalanceVOToFile(balance3);
            printFinalBalanceVOsToConsole(balance3);

        }

        List<BalanceVO> balance4 = balanceVOs.subList(600, 800);
        {
            writeBalanceVOToFile(balance4);
            printFinalBalanceVOsToConsole(balance4);

        }

        List<BalanceVO> balance5 = balanceVOs.subList(800, 1001);
        {
            writeBalanceVOToFile(balance5);
            printFinalBalanceVOsToConsole(balance5);

        }

//--------------------------------------------------

        return balanceVOs;

    }
//==========================================================

    public static String createFinalBalanceFileThreadingNew(List<BalanceVO> depositBalances1,List<BalanceVO> depositBalances2,List<BalanceVO> depositBalances3,List<BalanceVO> depositBalances4,List<BalanceVO> depositBalances5) throws IOException {
        String sourceFile1Path = PaymentTransactionApp.FILE_PATH_PREFIX + "BalanceUpdate.txt1";
        String sourceFile2Path = PaymentTransactionApp.FILE_PATH_PREFIX + "BalanceUpdate.txt2";
        String sourceFile3Path = PaymentTransactionApp.FILE_PATH_PREFIX + "BalanceUpdate.txt3";
        String sourceFile4Path = PaymentTransactionApp.FILE_PATH_PREFIX + "BalanceUpdate.txt4";
        String sourceFile5Path = PaymentTransactionApp.FILE_PATH_PREFIX + "BalanceUpdate.txt5";
        String mergedFilePath = PaymentTransactionApp.FILE_PATH_PREFIX + "BalanceUpdateThreading.txt";

        File[] files = new File[5];
        files[0] = new File(sourceFile1Path);
        files[1] = new File(sourceFile2Path);
        files[2] = new File(sourceFile3Path);
        files[3] = new File(sourceFile4Path);
        files[4] = new File(sourceFile5Path);

        File mergedFile = new File(mergedFilePath);

        mergeFiles(files, mergedFile);
        return mergedFilePath;
    }
    public static void mergeFiles(File[] files, File mergedFile) {

        FileWriter fstream = null;
        BufferedWriter out = null;
        try {
            fstream = new FileWriter(mergedFile, true);
            out = new BufferedWriter(fstream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        for (File f : files) {
            System.out.println("merging: " + f.getName());
            FileInputStream fis;
            try {
                fis = new FileInputStream(f);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));

                String aLine;
                while ((aLine = in.readLine()) != null) {
                    out.write(aLine);
                    out.newLine();
                }

                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //----------------------------------------------------------------
    private static void writeBalanceVOToFile(List<BalanceVO> balanceVOs) throws IOException {
        PrintWriter printWriter = new PrintWriter(PaymentTransactionApp.BALANCE_FILE_PATH);
            for (BalanceVO balanceVO : balanceVOs) {
                printWriter.println(balanceVO.toString());
            }
            printWriter.close();
        }


    private static void printBalanceVOsToConsole(List<BalanceVO> balanceVOS) {
        System.out.println("*********************** BALANCE *************************");
        for (BalanceVO balanceVO : balanceVOS)
            System.out.println(balanceVO.toString());
        System.out.println("***********************************************************");
    }


    public static List<BalanceVO> createFinalBalanceFile(Future<BalanceVO> depositBalances)
            throws IOException {
        String resultFinalBalance = "";
      for (int i = 1; i <= 5; i++) {
            Path pathBalanceUpdate = Paths.get(PaymentTransactionApp.FILE_PATH_PREFIX + "BalanceUpdate.txt"+i);
            Files.createFile(pathBalanceUpdate);
        }
        createFinalBalanceFileThreadPool(balanceVOs);
      // createFinalBalanceFileThreadingNew(balanceVOs);
        // writeFinalBalanceVOToFile(balanceVOs);
        resultFinalBalance += PaymentTransactionApp.DEBTOR_DEPOSIT_NUMBER + "\t" + PaymentTransactionApp.CREDITOR_DEPOSIT_NUMBER_PREFIX + "\t" + depositBalances + "\n";
        printFinalBalanceVOsToConsole(balanceVOs);
      //  return resultFinalBalance;

        return null;
    }

    private static void writeFinalBalanceVOToFile(List<BalanceVO> balanceVOs) throws IOException {
        PrintWriter printWriter = new PrintWriter(PaymentTransactionApp.BALANCE_UPDATE_FILE_PATH);
        for (BalanceVO balanceVO : balanceVOs) {
            printWriter.println(balanceVO.toString());
        }
        printWriter.close();
    }

    private static void printFinalBalanceVOsToConsole(List<BalanceVO> balanceVOS) {
        System.out.println("*********************** FinalBALANCE **********************");
        for (BalanceVO balanceVO : balanceVOS)
            System.out.println(balanceVO.toString());
        System.out.println("***********************************************************");
    }
}




