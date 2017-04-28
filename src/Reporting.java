

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by AdamKummer on 4/27/17.
 * Charles Frank
 */
public class Reporting
{
    private File transactionList;
    private String[] fileContents;
    private File userfile;
    private String[][] transactions;
    private String[] inputData;
    private final String LINETAG = "<Line/>";
    private final String COLTAG = "<Col/>";



    /**
     *  Updates the transactions 2d array with content from transactions.txt
     */
    private void updateInventory() {
        inputData = null;
        setArray();
        fillArray();
    }

    /**
     *  Fills the transactions 2d array with the contents from the transactions.txt file
     */
    private void fillArray() {
        for(int j = 0; j < inputData.length; j++) {
            String currentLine = inputData[j];
            String[] tmpLine = currentLine.split(COLTAG);
            for(int i = 0; i < tmpLine.length; i++) {
                String tmp = tmpLine[i];
                transactions[j][i] = tmp;
            }
        }
    }

    /**
     *  Sets the transactions 2d array to the appropriate size
     */
    private void setArray() {
        int rows = 0;
        int fields = 0;
        Scanner in = null;
        String content = "";
        try {
            in = new Scanner(userfile);
          } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        in.nextLine();

        while (in.hasNextLine()) {
            content = content + in.nextLine();
        }
        inputData = content.split(LINETAG);
        rows = inputData.length;
        fields = inputData[0].split(COLTAG).length;
        transactions = new String[rows][fields];
        in.close();
    }

}
