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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BuyerPanel extends JPanel {
    private String buyerID;
    private File inventoryFile;
    private String[][] inventory;
    private String[] inputDataInv;
    private ArrayList<Listing> listings;

    private final String LINETAG = "<Line/>";
    private final String ROWTAG = "<Col/>";
    private final int FRAME_WIDTH = 1000;
    private final int FRAME_HEIGHT = 1000;
    private final int DEFAULT_COLUNM_SIZE = 25;
    private final int COLS = 6;

    private JLabel inventoryLabel;
    private JLabel cartLabel;
    private JButton addToCartBtn;
    private JButton removeFromCartBtn;
    private DefaultListModel inventoryListModel;
    private DefaultListModel cartListModel;
    private JList inventoryList;
    private JList cartList;

    private JPanel buttonPanel;
    private JPanel inventoryPanel;
    private JPanel cartPanel;


    public BuyerPanel() {
        setLayout(new BorderLayout());
        this.inventoryFile = new File("Inventory.txt");
        listings = new ArrayList<Listing>();

        inventoryListModel = new DefaultListModel();
        inventoryList = new JList(inventoryListModel);

        cartListModel = new DefaultListModel();
        cartList = new JList(cartListModel);

        inventoryLabel = new JLabel("Inventory");
        cartLabel = new JLabel("Cart");
        addToCartBtn = new JButton("Add >>");
        removeFromCartBtn = new JButton("<< Remove");

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        buttonPanel.add(addToCartBtn);
        buttonPanel.add(removeFromCartBtn);

        inventoryPanel = new JPanel();
        inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.PAGE_AXIS));
        inventoryPanel.add(inventoryLabel);
        inventoryPanel.add(new JScrollPane(inventoryList));

        cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.PAGE_AXIS));
        cartPanel.add(cartLabel);
        cartPanel.add(new JScrollPane(cartList));

        add(buttonPanel, BorderLayout.CENTER);
        add(inventoryPanel, BorderLayout.WEST);
        add(cartPanel, BorderLayout.EAST);

    }

    /**
     * Starts the BuyerPanel
     * @param buyerID This is the tag that identifies the id of the buyer
     */
    public void start(String buyerID) {
        setFocusable(true);
        setVisible(true);

        this.buyerID = buyerID;
        updateInventory();
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
        for (int i = 0; i < inventory.length; i++) {
            listings.add(new Listing(new Item(inventory[i][2], Double.valueOf(inventory[i][3]), inventory[i][4],
                    inventory[i][1], inventory[i][0]), Integer.valueOf(inventory[i][5]), inventory[i][0]));
        }
    }

    /**
     *  Fills the inventory 2d array with the contents from the inventory.txt file
     */
    private void fillArray() {
        for(int j = 0; j < inputDataInv.length; j++) {
            String currentLine = inputDataInv[j];
            String[] tmpLine = currentLine.split(ROWTAG);
            for(int i = 0; i < tmpLine.length; i++) {
                String tmp = tmpLine[i];
                inventory[j][i] = tmp;
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
}


