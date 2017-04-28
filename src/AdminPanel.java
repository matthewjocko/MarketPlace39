
/**
 * Created by AdamKummer on 4/27/17.
 * Charles Frank
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class AdminPanel extends JPanel implements ActionListener {

    private static JTextArea usersTextArea = new JTextArea();
    private static JTextArea inventoryTextArea = new JTextArea();
    private static JTextArea transactionHistoryTextArea = new JTextArea();

    private String[][] transactions;
    private String[] inputData;
    private final String LINETAG = "<Line/>";
    private final String COLTAG = "<Col/>";

    private final String transactionHistoryFile = "TransactionHistory.txt";
    private final String userFile = "Users.txt";
    private final String inventoryFile = "Inventory.txt";

    private JButton updateUsersBtn = new JButton("Update UserInfo");
    private JButton updateInventoryBtn = new JButton("Update Inventory");
    private JButton updateTransactionHistoryBtn = new JButton("Update TransactionHistory");
    private JButton generateTransactionHistoryReport = new JButton("Generate Transaction History Report");
    private JButton generateUserHistroyReport = new JButton("Generate User History Report");

    private JPanel usersPanel = new JPanel();
    private JPanel inventoryPanel = new JPanel();
    private JPanel transactionHistoryPanel = new JPanel();
    private JPanel reportingPanel = new JPanel();

    public AdminPanel() {

        generateTransactionHistoryReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateReport_TransactionHistory();
            }
        });
        generateUserHistroyReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                generateReport_Seller();
            }
        });
        updateUsersBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeTextFile(usersTextArea, userFile);
            }
        });

        updateInventoryBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeTextFile(inventoryTextArea, inventoryFile);
            }
        });

        updateTransactionHistoryBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeTextFile(transactionHistoryTextArea, transactionHistoryFile);
            }
        });

        usersPanel.setLayout(new BorderLayout());
        usersPanel.add(usersTextArea, BorderLayout.CENTER);
        usersPanel.add(updateUsersBtn, BorderLayout.SOUTH);
        readTextFile(usersTextArea, userFile);

        inventoryPanel.setLayout(new BorderLayout());
        inventoryPanel.add(inventoryTextArea, BorderLayout.CENTER);
        inventoryPanel.add(updateInventoryBtn, BorderLayout.SOUTH);
        readTextFile(inventoryTextArea, inventoryFile);

        transactionHistoryPanel.setLayout(new BorderLayout());
        transactionHistoryPanel.add(transactionHistoryTextArea, BorderLayout.CENTER);
        transactionHistoryPanel.add(updateTransactionHistoryBtn, BorderLayout.SOUTH);
        readTextFile(transactionHistoryTextArea, transactionHistoryFile);

        reportingPanel.setLayout(new BorderLayout());
        reportingPanel.add(generateTransactionHistoryReport, BorderLayout.WEST);
        reportingPanel.add(generateUserHistroyReport, BorderLayout.EAST);

        JPanel container = new JPanel();

        container.add(usersPanel);
        container.add(inventoryPanel);
        container.add(transactionHistoryPanel);
        container.add(reportingPanel);

        container.setLayout(new GridLayout(4, 1, 1, 1));


        add(container);


        this.setBounds(50, 50, 1000, 500);
    }

    private void generateReport_Seller() {
        String destLocation = JOptionPane.showInputDialog(this, "Enter file destination from home directory: ");
        File destFile = new File(System.getProperty("user.home"), destLocation);
        String userID = JOptionPane.showInputDialog(this, "Enter userID: ");
        String header = "Market Place Transaction History - userID: " + userID +"\n" + new SimpleDateFormat().format(new Date()) + "\n\n";

        if (!destLocation.equals("")) {
            try {
                FileWriter fw = new FileWriter(destFile);
                PrintWriter out = new PrintWriter(fw);
                out.print(header);

                out.printf("%15s%15s%15s%15s%15s%15s%15s\n", "Transaction ID", "Buyer ID", "Seller ID", "Item ID", "Name", "Price", "Quantity");
                out.println();
                if (isUser(userID)) {
                    for (String[] transaction : transactions) {
                        if (transaction[2].equals(userID) || transaction[1].equals(userID)) {
                            out.printf("%15s%15s%15s%15s%15s%15s%15s\n", transaction[0], transaction[1], transaction[2], transaction[3], transaction[4], transaction[5], transaction[7]);
                        }
                    }
                } else {
                    out.printf("%15s", "No history for user.");
                }
                out.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        }
    }

    private boolean isUser(String userID) {

        for (String[] transaction : transactions) {
            if (transaction[2].equals(userID) || transaction[1].equals(userID)) {
                return true;
            }
        }

        return false;
    }

    private void generateReport_TransactionHistory() {
        String destLocation = JOptionPane.showInputDialog(this, "Enter file destination from home directory: ");
        File destFile = new File(System.getProperty("user.home"), destLocation);
        String header = "Market Place Transaction History\n" + new SimpleDateFormat().format(new Date()) + "\n\n";

        try {
            FileWriter fw = new FileWriter(destFile);
            PrintWriter out = new PrintWriter(fw);
            out.print(header);

            out.printf("%15s%15s%15s%15s%15s%15s%15s\n", "Transaction ID", "Buyer ID", "Seller ID", "Item ID", "Name", "Price", "Quantity");
            out.println();
            for (String[] transaction : transactions) {
                out.printf("%15s%15s%15s%15s%15s%15s%15s\n", transaction[0], transaction[1], transaction[2], transaction[3], transaction[4], transaction[5], transaction[7]);
            }
            out.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void writeTextFile(JTextArea display, String fileName) {
        try {
            FileWriter outStream = new FileWriter(fileName);
            outStream.write(display.getText());
            outStream.close();
        } catch (IOException e) {
            display.setText("error or something: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private static void readTextFile(JTextArea display, String fileName) {
        try {
            BufferedReader inStream
                    = new BufferedReader(new FileReader(fileName));
            String line = inStream.readLine();
            while (line != null) {
                display.append(line + "\n");
                line = inStream.readLine();
            }
            inStream.close();
        } catch (FileNotFoundException e) {
            display.setText("error: " + fileName + " NOT found\n");
            e.printStackTrace();
        } catch (IOException e) {
            display.setText("error: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    /**
     *  Updates the transactions 2d array with content from transactions.txt
     */
    private void updateTransactionHistory() {
        inputData = null;
        setArray();
        fillArray();
    }

    /**
     *  Fills the transactions 2d array with the contents from the transactions.txt file
     */
    private void fillArray() {
        for(int j = 1; j < inputData.length; j++) {
            String currentLine = inputData[j];
            String[] tmpLine = currentLine.split(COLTAG);
            for(int i = 0; i < tmpLine.length; i++) {
                String tmp = tmpLine[i];
                transactions[j - 1][i] = tmp;
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
            in = new Scanner(new File(transactionHistoryFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (in.hasNextLine()) {
            content = content + in.nextLine();
        }
        inputData = content.split(LINETAG);
        rows = inputData.length - 1;
        fields = inputData[0].split(COLTAG).length;
        transactions = new String[rows][fields];
        in.close();
    }


    public void actionPerformed(ActionEvent evt) {

        if (evt.getSource() == updateUsersBtn) {

            writeTextFile(usersTextArea, userFile);
        }
        if (evt.getSource() == updateInventoryBtn) {

            writeTextFile(inventoryTextArea, inventoryFile);
        }
        if (evt.getSource() == updateTransactionHistoryBtn) {

            writeTextFile(transactionHistoryTextArea, transactionHistoryFile);
        }
    }


    public void start(){
        setFocusable(true);
        setVisible(true);
        updateTransactionHistory();
        System.out.println("");
    }

}






