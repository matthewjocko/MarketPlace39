/**
 * Final Project C212
 * Due: 4-28-17
 *
 * @Author Matthew Lieberman
 * @Author Adam Kummer
 * @Author Charles Frank
 *
 * Last Updated: 4-28-17
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Reporting
{
    private File transactionList;
    private String[] fileContents;
    private File userfile;
    private String[][] users;
    private String[] inputData;
    private Integer lastID;
    private java.lang.String ROWTAG;
    private java.lang.String LINETAG;



    /**
     *  Updates the users 2d array with content from users.txt
     */
    private void updateUsers() {
        inputData = null;
        setArray();
        fillArray();
        lastID = Integer.valueOf(users[users.length - 1][0]);
    }

    /**
     *  Fills the users 2d array with the contents from the users.txt file
     */
    private void fillArray() {
        for(int j = 0; j < inputData.length; j++) {
            String currentLine = inputData[j];
            String[] tmpLine = currentLine.split(ROWTAG);
            for(int i = 0; i < tmpLine.length; i++) {
                String tmp = tmpLine[i];
//                if (tmp.charAt(0) == " ".charAt(0)) {
//                    tmp = tmp.substring(1);
//                }
//                if (tmp.charAt(tmp.length() - 1) == " ".charAt(0)) {
//                    tmp = tmp.substring(0, tmp.length() - 2);
//                }
                users[j][i] = tmp;
            }
        }
    }

    /**
     *  Sets the users 2d array to the appropriate size
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
//        while(in.hasNextLine()) {
//            String nextLine = in.nextLine();
//            if(nextLine.charAt(0) != ",".charAt(0)) {
//                inputData.add(nextLine);
//            }
//        }
        while (in.hasNextLine()) {
            content = content + in.nextLine();
        }
        inputData = content.split(LINETAG);
        rows = inputData.length;
        fields = inputData[0].split(ROWTAG).length;
        users = new String[rows][fields];
        in.close();
    }

}
