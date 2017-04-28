

/**
 * Created by AdamKummer on 4/27/17.
 */
//TODO - fix weird spacings
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class Admin extends JFrame implements ActionListener {



    private static JTextArea display1 = new JTextArea();
    private static JTextArea display2 = new JTextArea();
    private static JTextArea display3 = new JTextArea();




     JButton write1 = new JButton("Update UserInfo");
     JButton write2 = new JButton("Update Inventory");
     JButton write3 = new JButton("Update TransactionHistory");






    private JPanel p1 = new JPanel();
    private JPanel p2 = new JPanel();
    private JPanel p3 = new JPanel();




    public Admin() throws IOException {

        write1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeTextFile(display1, "/Users/AdamKummer/IdeaProjects/something/src/MarketPlaceSeller/Users.txt");
            }
        });

        write2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeTextFile(display2, "/Users/AdamKummer/IdeaProjects/something/src/MarketPlaceSeller/Inventory.txt");
            }
        });

        write3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writeTextFile(display3, "/Users/AdamKummer/IdeaProjects/something/src/MarketPlaceSeller/TransactionHistory.txt");
            }
        });


        p1.setLayout(new BorderLayout());
        p1.add(display1, BorderLayout.CENTER);
        p1.add(write1, BorderLayout.SOUTH);
        readTextFile(display1, "/Users/AdamKummer/IdeaProjects/something/src/MarketPlaceSeller/Users.txt");



        p2.setLayout(new BorderLayout());
        p2.add(display2, BorderLayout.CENTER);
        p2.add(write2, BorderLayout.SOUTH);
        readTextFile(display2, "/Users/AdamKummer/IdeaProjects/something/src/MarketPlaceSeller/Inventory.txt");


        p3.setLayout(new BorderLayout());
        p3.add(display3, BorderLayout.CENTER);
        p3.add(write3, BorderLayout.SOUTH);
        readTextFile(display3, "/Users/AdamKummer/IdeaProjects/something/src/MarketPlaceSeller/TransactionHistory.txt");



        JPanel container = new JPanel();

        container.add(p1);
        container.add(p2);
        container.add(p3);
        container.setLayout(new GridLayout(1, 3, 1, 1));


        this.getContentPane().add(container);


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

        if (evt.getSource() == write1) {

            writeTextFile(display1, "/Users/AdamKummer/IdeaProjects/something/src/MarketPlaceSeller/Users.txt");
        }
        if (evt.getSource() == write2) {

            writeTextFile(display2, "/Users/AdamKummer/IdeaProjects/something/src/MarketPlaceSeller/Inventory.txt");
        }
        if (evt.getSource() == write3) {

            writeTextFile(display3, "/Users/AdamKummer/IdeaProjects/something/src/MarketPlaceSeller/TransactionHistory.txt");
        }
    }


    public void start(){
        setFocusable(true);
        setVisible(true);
    }


//    public static void main(String args[]) throws IOException {
//        new Admin().setVisible(true);
//
//    }
}






