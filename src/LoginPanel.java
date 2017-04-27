import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Charles Frank
 * charfran
 * Apr 24, 2017
 */
public class LoginPanel extends JPanel{
    private final String[] TYPES = {"Seller", "Buyer"};

    private MarketPlaceDriver marketPlaceDriver;
    private File userfile;
    private String[][] users;
    private ArrayList<String> inputData;

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

    private final int FRAME_WIDTH = 1000;
    private final int FRAME_HEIGHT = 1000;
    private final int DEFAULT_COLUNM_SIZE = 25;
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
     *  Updates the users 2d array with content from users.txt
     */
    private void updateUsers() {
        inputData = new ArrayList<String>();
        setArray();
        fillArray();
        lastID = Integer.valueOf(users[users.length - 1][0]);
    }

    /**
     *  Fills the users 2d array with the contents from the users.txt file
     */
    private void fillArray() {
        for(String currentLine : inputData) {
            String[] tmpLine = currentLine.split(",");
            for(int i = 0; i < tmpLine.length; i++) {
                String tmp = tmpLine[i];
                if (tmp.charAt(0) == " ".charAt(0)) {
                    tmp = tmp.substring(1);
                }
                if (tmp.charAt(tmp.length() - 1) == " ".charAt(0)) {
                    tmp = tmp.substring(0, tmp.length() - 2);
                }
                users[inputData.indexOf(currentLine)][i] = tmp;
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
        try {
            in = new Scanner(userfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        in.nextLine();
        while(in.hasNextLine()) {
            String nextLine = in.nextLine();
            if(nextLine.charAt(0) != ",".charAt(0)) {
                inputData.add(nextLine);
            }
        }
        rows = inputData.size();
        fields = inputData.get(0).split(",").length;
        users = new String[rows][fields];
        in.close();
    }

    /**
     * This method logs a user into the system with the help of the auth method
     */
    private void login(String email, char[] password) {
        if (auth(email, password)) {
            for (String[] user : users) {
                if (user[1].equals(email)) {
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
                newAccount = "\n" + (lastID + 1) + ", " + email + ", " + password.hashCode() + ", " + type + ", " + firstName + ", " + lastName;
                writer.write(newAccount);
                writer.close();
                fw.close();
                lastID = lastID + 1;
                JOptionPane.showMessageDialog(this, "Account created. You can now log in.", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
                clearInputs();
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
            if (email == users[i][1]) {
                if (password.hashCode() == Integer.valueOf(users[i][2])) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Method to test with
     * @param args
     */
    public static void main(String[] args) {
//        LoginPanel loginPanel = new LoginPanel(null);
//        String pass = "12345";
//        char[] password = {pass.charAt(0), pass.charAt(1), pass.charAt(2), pass.charAt(3), pass.charAt(4)};
//        loginPanel.createAccount("test@email.com", password, "Seller", "Nick", "Verdi");
    }
}
