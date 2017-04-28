
/**
 * Created by AdamKummer on 4/27/17.
 * Charles Frank
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.io.*;

public class AdminPanel extends JPanel implements ActionListener {



    private static JTextArea display1 = new JTextArea();
    private static JTextArea display2 = new JTextArea();
    private static JTextArea display3 = new JTextArea();




     JButton updateUsersBtn = new JButton("Update UserInfo");
     JButton updateInventoryBtn = new JButton("Update Inventory");
     JButton updateTransactionHistoryBtn = new JButton("Update TransactionHistory");






    private JPanel UsersPanel = new JPanel();
    private JPanel InventoryPanel = new JPanel();
    private JPanel TransactionHistoryPanel = new JPanel();




    public AdminPanel() {

        updateUsersBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeTextFile(display1, "Users.txt");
            }
        });

        updateInventoryBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeTextFile(display2, "Inventory.txt");
            }
        });

        updateTransactionHistoryBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeTextFile(display3, "TransactionHistory.txt");
            }
        });


        UsersPanel.setLayout(new BorderLayout());
        UsersPanel.add(display1, BorderLayout.CENTER);
        UsersPanel.add(updateUsersBtn, BorderLayout.SOUTH);
        readTextFile(display1, "Users.txt");



        InventoryPanel.setLayout(new BorderLayout());
        InventoryPanel.add(display2, BorderLayout.CENTER);
        InventoryPanel.add(updateInventoryBtn, BorderLayout.SOUTH);
        readTextFile(display2, "Inventory.txt");


        TransactionHistoryPanel.setLayout(new BorderLayout());
        TransactionHistoryPanel.add(display3, BorderLayout.CENTER);
        TransactionHistoryPanel.add(updateTransactionHistoryBtn, BorderLayout.SOUTH);
        readTextFile(display3, "TransactionHistory.txt");



        JPanel container = new JPanel();

        container.add(UsersPanel);
        container.add(InventoryPanel);
        container.add(TransactionHistoryPanel);

        container.setLayout(new GridLayout(3, 1, 1, 1));


        add(container);


        this.setBounds(50, 50, 1000, 500);
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



    public void actionPerformed(ActionEvent evt) {

        if (evt.getSource() == updateUsersBtn) {

            writeTextFile(display1, "Users.txt");
        }
        if (evt.getSource() == updateInventoryBtn) {

            writeTextFile(display2, "Inventory.txt");
        }
        if (evt.getSource() == updateTransactionHistoryBtn) {

            writeTextFile(display3, "TransactionHistory.txt");
        }
    }


    public void start(){
        setFocusable(true);
        setVisible(true);
    }


//    public static void main(String args[]) throws IOException {
//        new AdminPanel().setVisible(true);
//
//    }
}






