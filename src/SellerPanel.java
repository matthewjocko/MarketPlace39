import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Charles Frank
 * charfran
 * Apr 27, 2017
 */
public class SellerPanel extends JPanel {
    private String sellerID;
    private File inventoryFile;
    private String[][] inventory;
    private String[] inputDataInv;
    private File userfile;
    private String[][] users;
    private String[] inputData;
    private ArrayList<Listing> listings;
    private String selectedID;
    private int selectedIndex = -1;

    private DefaultListModel model;
    private JList list;
    private JScrollPane scrollPane;

    private JButton updateInfoBtn;
    private JButton updateInvBtn;
    private JButton addInvBtn;
    private JButton removeInvBtn;
    private JLabel passwordLbl;
    private JLabel confirmPasswordLbl;
    private JLabel firstNameLbl;
    private JLabel lastNameLbl;
    private JLabel priceLbl;
    private JLabel descriptionLbl;
    private JLabel quantityLbl;
    private JPasswordField passwordTf;
    private JPasswordField confirmPasswordTf;
    private JTextField firstNameTf;
    private JTextField lastNameTf;
    private JTextField priceTf;
    private JTextField descriptionTf;
    private JTextField quantityTf;

    private JPanel updateInfoPan;
    private JPanel updateInvPan;
    private JPanel addRemovePan;

    private ActionListener updateInfoLis;
    private ActionListener updateInvLis;
    private ActionListener addInvLis;
    private ActionListener removeInvLis;
    private ListSelectionListener listLisener;

    private final String LINETAG = "<Line/>";
    private final String COLTAG = "<Col/>";
    private final int FRAME_WIDTH = 1000;
    private final int FRAME_HEIGHT = 1000;
    private final int DEFAULT_COLUNM_SIZE = 25;
    private final int COLS = 6;

    public SellerPanel() {
        super();
        setBackground(Color.white);
        setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setLayout(new BorderLayout());
        this.inventoryFile = new File("Inventory.txt");
        this.userfile = new File("Users.txt");
        listings = new ArrayList<Listing>();
        model = new DefaultListModel();
        list = new JList(model);
        scrollPane = new JScrollPane(list);
        selectedID = "";
//        selectedIndex = -1;
        updateInfoPan = new JPanel();
        updateInfoPan.setLayout(new BoxLayout(updateInfoPan, BoxLayout.PAGE_AXIS));
        updateInvPan = new JPanel();
        updateInvPan.setLayout(new BoxLayout(updateInvPan, BoxLayout.PAGE_AXIS));
        addRemovePan = new JPanel();
        addRemovePan.setLayout(new BoxLayout(addRemovePan, BoxLayout.PAGE_AXIS));

        listLisener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                selectedIndex = list.getSelectedIndex();
                try {
                    fillInv(list.getSelectedValue().toString());
                } catch (Exception ignored) {
                }
            }
        };
        updateInfoLis = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateInfo();
            }
        };
        updateInvLis = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateInvRow();
            }
        };
        addInvLis = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addInv();
            }
        };
        removeInvLis = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeInv("");
            }
        };
        addInvBtn = new JButton("Add New Listing");
        addInvBtn.addActionListener(addInvLis);
        removeInvBtn = new JButton("Remove Selected Listing");
        removeInvBtn.addActionListener(removeInvLis);
        updateInfoBtn = new JButton("Update Personal Information");
        updateInfoBtn.addActionListener(updateInfoLis);
        updateInvBtn = new JButton("Update Selected Listing");
        updateInvBtn.addActionListener(updateInvLis);
        passwordLbl = new JLabel("Password:");
        confirmPasswordLbl = new JLabel("Confirm Password:");
        firstNameLbl = new JLabel("First Name:");
        lastNameLbl = new JLabel("Last Name:");
        priceLbl = new JLabel("Price:");
        descriptionLbl = new JLabel("Description:");
        quantityLbl = new JLabel("Quantity:");
        firstNameTf = new JTextField(DEFAULT_COLUNM_SIZE);
        lastNameTf = new JTextField(DEFAULT_COLUNM_SIZE);
        passwordTf = new JPasswordField(DEFAULT_COLUNM_SIZE);
        confirmPasswordTf = new JPasswordField(DEFAULT_COLUNM_SIZE);
        priceTf = new JTextField(DEFAULT_COLUNM_SIZE);
        descriptionTf = new JTextField(DEFAULT_COLUNM_SIZE);
        quantityTf = new JTextField(DEFAULT_COLUNM_SIZE);
    }

    /**
     * Starts the SellerPanel
     */
    public void start(String sellerID) {
        JOptionPane.showMessageDialog(this,
                "Updating personal information will reset password to new password!",
                    "WARNING", JOptionPane.WARNING_MESSAGE);
        this.sellerID = sellerID;
//        updateInventory();
        updateInventoryDisplay();
        add(scrollPane, BorderLayout.NORTH);
        setFocusable(true);
        setVisible(true);
        list.addListSelectionListener(listLisener);

        updateInfoBtn.addActionListener(updateInfoLis);
        updateInvBtn.addActionListener(updateInvLis);
        updateInfoPan.add(firstNameLbl);
        updateInfoPan.add(firstNameTf);
        updateInfoPan.add(lastNameLbl);
        updateInfoPan.add(lastNameTf);
        updateInfoPan.add(passwordLbl);
        updateInfoPan.add(passwordTf);
        updateInfoPan.add(confirmPasswordLbl);
        updateInfoPan.add(confirmPasswordTf);
        updateInfoPan.add(updateInfoBtn);

        updateInvPan.add(priceLbl);
        updateInvPan.add(priceTf);
        updateInvPan.add(descriptionLbl);
        updateInvPan.add(descriptionTf);
        updateInvPan.add(quantityLbl);
        updateInvPan.add(quantityTf);
        updateInvPan.add(updateInvBtn);

        addRemovePan.add(addInvBtn);
        addRemovePan.add(removeInvBtn);

        add(updateInfoPan, BorderLayout.WEST);
        add(updateInvPan, BorderLayout.EAST);
        add(addRemovePan, BorderLayout.CENTER);
        fillUser();
    }

    /**
     * Filles in the textfields in the updateInfoPan
     */
    private void fillUser() {
        updateUsers();
        for (String[] user : users) {
            if (user[0].equals(sellerID)) {
                firstNameTf.setText(user[4]);
                lastNameTf.setText(user[5]);
                passwordTf.setText("");
                confirmPasswordTf.setText("");
                break;
            }
        }
    }

    /**
     *  Updates the users 2d array with content from users.txt
     */
    private void updateUsers() {
        inputData = null;
        setArrayUsers();
        fillArrayUsers();
    }

    /**
     *  Fills the users 2d array with the contents from the users.txt file
     */
    private void fillArrayUsers() {
        for(int j = 0; j < inputData.length; j++) {
            String currentLine = inputData[j];
            String[] tmpLine = currentLine.split(COLTAG);
            for(int i = 0; i < tmpLine.length; i++) {
                String tmp = tmpLine[i];
                users[j][i] = tmp;
            }
        }
    }

    /**
     *  Sets the users 2d array to the appropriate size
     */
    private void setArrayUsers() {
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
        fields = inputData[1].split(COLTAG).length;
        users = new String[rows][fields];
        in.close();
    }

    /**
     * Fills the textfields in the updateInvPan
     * @param selected The selectedID option from the List
     */
    private void fillInv(String selected) {
        for (int i = 1; i < inventory.length; i++) {
            if (inventory[i][2].equals(selected)) {
                selectedID = inventory[i][1];
                priceTf.setText(inventory[i][3]);
                descriptionTf.setText(inventory[i][4]);
                quantityTf.setText(inventory[i][5]);
                break;
            }
        }
    }

    /**
     * Updates display with items from listing
     */
    private void updateInventoryDisplay() {
        //TODO This method needs to work. Not updating Textfields after add/remove. Not updating scrollpane after add/remove
        updateInventory();
        model = null;
        list = null;
        scrollPane = null;
        model = new DefaultListModel();
        list = new JList(model);
        scrollPane = new JScrollPane(list);
        for (Listing listing : listings) {
            model.removeElement(listing.getItem().getName());
        }
        for (Listing listing : listings) {
            model.addElement(listing.getItem().getName());
        }
        scrollPane.update(this.getGraphics());
    }

    /**
     *  Updates the inventory 2d array with content from inventory.txt
     */
    private void updateInventory() {
        inputDataInv = null;
        setArray();
        fillArray();
        updateListings();
    }

    /**
     * Updates the listings available
     */
    private void updateListings() {
        listings = null;
        listings = new ArrayList<Listing>();
        for (int i = 1; i < inventory.length; i++) {
            if (Integer.valueOf(inventory[i][0]).equals(Integer.valueOf(sellerID))) {
                listings.add(new Listing(new Item(inventory[i][2], Double.valueOf(inventory[i][3]), inventory[i][4],
                        inventory[i][1], inventory[i][0]), Integer.valueOf(inventory[i][5]), inventory[i][0]));
            }
        }
    }

    /**
     *  Fills the inventory 2d array with the contents from the inventory.txt file
     */
    private void fillArray() {
        for(int j = 0; j < inputDataInv.length; j++) {
            String currentLine = inputDataInv[j];
            String[] tmpLine = currentLine.split(COLTAG);
            for(int i = 0; i < tmpLine.length; i++) {
                String tmp = tmpLine[i];
                if (!tmp.equals("")) {
                    inventory[j][i] = tmp;
                }
            }
        }
    }

    /**
     *  Sets the inventory 2d array to the appropriate size
     */
    private void setArray() {
        inventory = null;
        int rows = 0;
        int fields = 0;
        Scanner in = null;
        String content = "";
        try {
            in = new Scanner(inventoryFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        in.nextLine();
        while (in.hasNextLine()) {
            content = content + in.nextLine();
        }
        inputDataInv = content.split(LINETAG);
        rows = inputDataInv.length;
        fields = COLS;
        inventory = new String[rows][fields];
        in.close();
    }

    /**
     * Updates personal information
     */
    private void updateInfo() {
        updateUsers();
        for (int i = 0; i < users.length; i++) {
            if (users[i][0].equals(sellerID)) {
                if(hash(passwordTf.getPassword()).equals(hash(confirmPasswordTf.getPassword()))
                        && !hash(passwordTf.getPassword()).equals("")
                            && !hash(confirmPasswordTf.getPassword()).equals("")) {
                    users[i][2] = hash(passwordTf.getPassword());
                    users[i][4] = firstNameTf.getText();
                    users[i][5] = lastNameTf.getText();
                    updateUsersFile();
                    fillUser();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Incorrect information entered. Try again...", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            }
        }
    }

    private void updateUsersFile() {
        FileWriter fw = null;
        PrintWriter writer = null;
        String newAccount = "";
        try {
            fw = new FileWriter(userfile, false);
            writer = new PrintWriter(fw);
            writer.write("ID, Email, Password, Type, First Name, Last Name");
            for (int i = 1; i < users.length; i++) {
                writer.write("\n" + LINETAG + users[i][0] + COLTAG + users[i][1] + COLTAG + users[i][2] + COLTAG + users[i][3] + COLTAG
                        + users[i][4] + COLTAG + users[i][5]);
            }
            writer.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fillUser();
    }


    private String hash(char[] password) {
        String hash = "";
        for (char ch : password) {
            hash = hash + String.valueOf((int)ch * 16);
        }
        return hash;
    }

    /**
     * Updates inventory for selectedID item
     */
    private void updateInvRow() {
        for (int i = 1; i < inventory.length; i++) {
            if (inventory[i][1].equals(selectedID)) {
                inventory[i][3] = priceTf.getText();
                inventory[i][4] = descriptionTf.getText();
                inventory[i][5] = quantityTf.getText();
                updateInventoryFile();
                break;
            }
        }
    }

    private void updateInventoryFile() {
        FileWriter fw = null;
        PrintWriter writer = null;
        String newAccount = "";
        try {
            fw = new FileWriter(inventoryFile, false);
            writer = new PrintWriter(fw);
            writer.write("Seller ID, Item ID, Name, Price, Description, Quantity");
            for (int i = 1; i < inventory.length; i++) {
                writer.write("\n" + LINETAG + inventory[i][0] + COLTAG + inventory[i][1] + COLTAG
                        + inventory[i][2] + COLTAG + inventory[i][3] + COLTAG + inventory[i][4] + COLTAG + inventory[i][5]);
            }
            writer.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateInventoryDisplay();
    }

    /**
     * Adds new item to inventory
     */
    private void addInv() {
        String itemID = nextItemID();
        String name = JOptionPane.showInputDialog(this, "Enter name of product: ");
        String price = JOptionPane.showInputDialog(this, "Enter price of product: ");
        String description = JOptionPane.showInputDialog(this, "Enter description of product: ");
        String quantity = JOptionPane.showInputDialog(this, "Enter quantity of product: ");


        if (name != null && price != null && description != null && quantity != null) {
            FileWriter fw = null;
            PrintWriter writer = null;
            String newAccount = "";
            try {
                fw = new FileWriter(inventoryFile, true);
                writer = new PrintWriter(fw);
                writer.write("\n" + LINETAG + sellerID + COLTAG + itemID + COLTAG + name + COLTAG + price + COLTAG + description + COLTAG + quantity);
                writer.close();
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            updateInventoryDisplay();
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect information entered. Try again...", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Finds next itemID
     * @return next itemID
     */
    private String nextItemID() {
        String max = "-1";
        for (int i = 1; i < inventory.length; i++) {
            if (Integer.valueOf(inventory[i][1]) > Integer.valueOf(max)) {
                max = inventory[i][1];
            }
        }
        return String.valueOf(Integer.valueOf(max) + 1);
    }

    /**
     * Removes selectedID item from inventory
     */
    private void removeInv(String pos) {
//        model.removeElementAt(selectedIndex);
        if (!selectedID.equals("")) {
            for (int i = 1; i < inventory.length; i++) {
                if (inventory[i][1].equals(selectedID)) {
                    String[] tmp = inventory[i];
                    for (int j = i; j + 1 < inventory.length; j++) {
                        for (int k = 0; k < tmp.length; k++) {
                            inventory[j][k] = inventory[j + 1][k];
                        }
                    }
                    for (int j = 0; j < tmp.length; j++) {
                        inventory[i][j] = tmp[j];
                    }
                    break;
                }
            }
            String[][] tmp = new String[inventory.length - 1][COLS];
            for (int i = 0; i < inventory.length - 1; i++) {
                for (int j = 0; j < COLS; j++) {
                    tmp[i][j] = inventory[i][j];
                }
            }
            inventory = new String[tmp.length][tmp[1].length];
            for (int i = 0; i < inventory.length; i++) {
                for (int j = 0; j < COLS; j++) {
                    inventory[i][j] = tmp[i][j];
                }
            }

            updateInventoryFile();
        } else {
            JOptionPane.showMessageDialog(this, "Select item first.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
