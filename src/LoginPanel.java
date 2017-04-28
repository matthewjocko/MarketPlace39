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


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class LoginPanel extends JPanel{
    private final String[] TYPES = {"Seller", "Buyer"};

    private MarketPlaceDriver marketPlaceDriver;
    private File userfile;
    private String[][] users;
    private String[] inputData;

    private JLabel eEmailLbl;
    private JLabel ePasswordLbl;
    private JTextField eEmail;
    private JPasswordField ePassword;
    private JButton loginBtn;

    private JLabel nFirstNameLbl;
    private JLabel nLastNameLbl;
    private JLabel nEmailLbl;
    private JLabel nPasswordLbl;
    private JLabel nConfirmPasswordLbl;
    private JLabel nTypesLbl;
    private JTextField nFirstName;
    private JTextField nLastName;
    private JTextField nEmail;
    private JPasswordField nPassword;
    private JPasswordField nConfirmPassword;
    private JComboBox nTypes;
    private JButton createAccountBtn;

    private ActionListener loginBtnLis;
    private ActionListener createAccountBtnLis;

    private final String LINETAG = "<Line/>";
    private final String COLTAG = "<Col/>";
    private final int FRAME_WIDTH = 1000;
    private final int FRAME_HEIGHT = 1000;
    private final int DEFAULT_COLUNM_SIZE = 25;
    private  final int COLS = 6;
    private final int y = 40;
    private final int x = 100;
    private int lastID = 0;


    public LoginPanel(MarketPlaceDriver marketPlaceDriver) {
        super();
        setBackground(Color.white);
        setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.marketPlaceDriver = marketPlaceDriver;
        this.userfile = new File("Users.txt");
        updateUsers();
        setDisplay();
    }

    /**
     * Starts the loginPanel
     */
    public void start() {
        setFocusable(true);
        setVisible(true);
    }

    /**
     * Stops the loginPanel
     */
    public void stop() {
        setFocusable(false);
        setVisible(false);
    }

    /**
     * Sets the initial values for all jpanel components
     */
    private void setDisplay() {
        //TODO set layouts
        loginBtnLis = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login(eEmail.getText(), ePassword.getPassword());
            }
        };

        createAccountBtnLis = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createAccount(nEmail.getText(), nPassword.getPassword(), nConfirmPassword.getPassword(), nTypes.getSelectedItem().toString(),
                        nFirstName.getText(), nLastName.getText());
            }
        };

        eEmailLbl = new JLabel("Enter Email:");
        eEmailLbl.setLocation(x, y);
        eEmail = new JTextField();
        eEmail.setColumns(DEFAULT_COLUNM_SIZE);
        eEmail.setLocation(eEmailLbl.getX() + 10 + eEmailLbl.getWidth(), eEmailLbl.getY());
        ePasswordLbl = new JLabel("Enter Password");
        ePasswordLbl.setLocation(eEmailLbl.getX(), eEmailLbl.getY() + 10 + eEmailLbl.getHeight());
        ePassword = new JPasswordField();
        ePassword.setColumns(DEFAULT_COLUNM_SIZE);
        ePassword.setLocation(ePasswordLbl.getX() + 10 + ePasswordLbl.getWidth(), ePasswordLbl.getY());
        loginBtn = new JButton();
        loginBtn.setText("Login");
        loginBtn.setLocation(ePasswordLbl.getX(), ePasswordLbl.getY() + 10 + ePasswordLbl.getHeight());
        loginBtn.addActionListener(loginBtnLis);

        nFirstNameLbl = new JLabel("Enter First Name:");
        nLastNameLbl = new JLabel("Enter Last Name:");
        nEmailLbl = new JLabel("Enter Email:");
        nPasswordLbl = new JLabel("Enter Password:");
        nConfirmPasswordLbl = new JLabel("Enter Password Again:");
        nTypesLbl = new JLabel("Select Type:");
        nFirstName = new JTextField();
        nFirstName.setColumns(DEFAULT_COLUNM_SIZE);
        nLastName = new JTextField();
        nLastName.setColumns(DEFAULT_COLUNM_SIZE);
        nEmail = new JTextField();
        nEmail.setColumns(DEFAULT_COLUNM_SIZE);
        nPassword = new JPasswordField();
        nPassword.setColumns(DEFAULT_COLUNM_SIZE);
        nConfirmPassword = new JPasswordField();
        nConfirmPassword.setColumns(DEFAULT_COLUNM_SIZE);
        nTypes = new JComboBox(TYPES);
        createAccountBtn = new JButton();
        createAccountBtn.setText("Create Account");
        createAccountBtn.addActionListener(createAccountBtnLis);

        add(eEmailLbl);
        add(eEmail);
        add(ePasswordLbl);
        add(ePassword);
        add(loginBtn);

        add(nFirstNameLbl);
        add(nFirstName);
        add(nLastNameLbl);
        add(nLastName);
        add(nEmailLbl);
        add(nEmail);
        add(nPasswordLbl);
        add(nPassword);
        add(nConfirmPasswordLbl);
        add(nConfirmPassword);
        add(nTypesLbl);
        add(nTypes);
        add(createAccountBtn);
    }

    /**
     *  Updates the transactions 2d array with content from transactions.txt
     */
    private void updateUsers() {
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
                users[j - 1][i] = tmp;
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
            in = new Scanner(new File("Users.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (in.hasNextLine()) {
            content = content + in.nextLine();
        }
        inputData = content.split(LINETAG);
        rows = inputData.length - 1;
        fields = inputData[0].split(COLTAG).length;
        users = new String[rows][fields];
        in.close();
    }

    /**
     * This method logs a user into the system with the help of the auth method
     * @param email
     * @param password
     */
    private void login(String email, char[] password) {
        if (auth(email, password)) {
            for (String[] user : users) {
                if (user[1].equals(email)) {
                    clearInputs();
                    marketPlaceDriver.setAccountType(user[3], user[0]);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Email and/or password do not match records. "
                    + "Try again...", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Creates an account for a new user
     * @param email
     * @param password
     * @param confirmPassword
     * @param type
     * @param firstName
     * @param lastName
     */
    private void createAccount(String email, char[] password, char[] confirmPassword, String type, String firstName, String lastName) {
        FileWriter fw = null;
        PrintWriter writer;
        String newAccount = "";

        if (Arrays.hashCode(password) == Arrays.hashCode(confirmPassword) && !email.equals("") && !firstName.equals("")
                && !lastName.equals("")) {
            try {
                fw = new FileWriter(userfile, true);
                writer = new PrintWriter(fw);
                newAccount = "\n" + LINETAG + (lastID + 1) + COLTAG + email + COLTAG + hash(password) + COLTAG + type + COLTAG + firstName + COLTAG + lastName;
                writer.print(newAccount);
                writer.close();
                fw.close();
                lastID = lastID + 1;
                JOptionPane.showMessageDialog(this, "Account created. You can now log in.", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
                clearInputs();
                updateUsers();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect information entered. Try again...", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }

    /**
     * Clears inputs
     */
    private void clearInputs() {
        eEmail.setText("");
        ePassword.setText("");
        nFirstName.setText("");
        nLastName.setText("");
        nEmail.setText("");
        nPassword.setText("");
        nConfirmPassword.setText("");
    }

    /**
     * Authenticates a users username and password against a data file
     * @param email The users username
     * @param password The users password, passed directly as a char array to prevent String storing of passwords
     * @return True or false on whether the user credentials match records
     */
    private boolean auth(String email, char[] password) {
        for (int i = 0; i < users.length; i++) {
            if (email.equals(users[i][1])) {
                if (hash(password).equals(users[i][2])) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * method that hashes the password for the text file
     * @param password
     * @return
     */
    private String hash(char[] password) {
        String hash = "";
        for (char ch : password) {
            hash = hash + String.valueOf((int)ch * 16);
        }
        return hash;
    }

    /**
     * Method to test with
     * @param args
     */
    public static void main(String[] args) {
    }
}
