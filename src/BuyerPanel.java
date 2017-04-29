
/**
 * Adam Kummer
 * Charles Frank
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuyerPanel extends JPanel {

    private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
    private static final String ADD_BUTTON_LABEL = "Add >>";
    private static final String PROCEED_TO_CHECKOUT_CHECKOUT = "ProceedToCheckout";
    private static final String REMOVE_BUTTON_LABEL = "<< Remove";
    private static final String DEFAULT_SOURCE_CHOICE_LABEL = "Market";
    private static final String DEFAULT_DEST_CHOICE_LABEL = "Cart";

    private static String[][] inventory;
    private static String[] inputDataInv;
    private static final String LINETAG = "<Line/>";
    private static final String COLTAG = "<Col/>";

    private int price = 0;

    private JLabel sourceLabel;
    private JLabel totalPriceLabel;
    private JList sourceList;
    private SortedListModel sourceListModel;
    private JList destList;
    private SortedListModel destListModel;
    private JLabel destLabel;
    private JButton addButton;
    private JButton removeButton;
    private JButton checkoutButton;
    private String buyerID;
    private ArrayList<Listing> cartList;
    private String[][] transactions;
    private static String[] inputData;

    public BuyerPanel(MarketPlaceDriver marketPlaceDriver) {
        cartList = new ArrayList<Listing>();
        setBorder(BorderFactory.createEtchedBorder());
        setLayout(new GridBagLayout());
        sourceLabel = new JLabel(DEFAULT_SOURCE_CHOICE_LABEL);
        sourceListModel = new SortedListModel();
        sourceList = new JList(sourceListModel);
        add(sourceLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                EMPTY_INSETS, 0, 0));
        add(new JScrollPane(sourceList), new GridBagConstraints(0, 1, 1, 5, .5,
                1, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                EMPTY_INSETS, 0, 0));

        addButton = new JButton(ADD_BUTTON_LABEL);
        add(addButton, new GridBagConstraints(1, 2, 1, 2, 0, .25,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                EMPTY_INSETS, 0, 0));
        addButton.addActionListener(new AddListener(this));

        totalPriceLabel = new JLabel("Total Price: $" + price);
        add(totalPriceLabel, new GridBagConstraints(1, 6, 1, 2, 0, .25,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
                0, 5, 0, 5), 0, 0));

        checkoutButton = new JButton("Checkout");
        add(checkoutButton, new GridBagConstraints(1, 8, 1, 2, 0, .25,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
                0, 5, 0, 5), 0, 0));
        checkoutButton.addActionListener(new CheckoutListener(this, marketPlaceDriver));

        removeButton = new JButton(REMOVE_BUTTON_LABEL);
        add(removeButton, new GridBagConstraints(1, 4, 1, 2, 0, .25,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
                0, 5, 0, 5), 0, 0));
        removeButton.addActionListener(new RemoveListener(this));

        destLabel = new JLabel(DEFAULT_DEST_CHOICE_LABEL);
        destListModel = new SortedListModel();
        destList = new JList(destListModel);
        add(destLabel, new GridBagConstraints(2, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                EMPTY_INSETS, 0, 0));
        add(new JScrollPane(destList), new GridBagConstraints(2, 1, 1, 5, .5,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                EMPTY_INSETS, 0, 0));

    }

    public String getSourceChoicesTitle() {
        return sourceLabel.getText();
    }

    public void setSourceChoicesTitle(String newValue) {
        sourceLabel.setText(newValue);
    }

    public String getDestinationChoicesTitle() {
        return destLabel.getText();
    }

    public void setDestinationChoicesTitle(String newValue) {
        destLabel.setText(newValue);
    }

    public void clearSourceListModel() {
        sourceListModel.clear();
    }

    public void clearDestinationListModel() {
        destListModel.clear();
    }

    public void addSourceElements(ListModel newValue) {
        fillListModel(sourceListModel, newValue);
    }

    public void setSourceElements(ListModel newValue) {
        clearSourceListModel();
        addSourceElements(newValue);
    }

    public void addDestinationElements(ListModel newValue) {
        fillListModel(destListModel, newValue);
    }

    private void fillListModel(SortedListModel model, ListModel newValues) {
        int size = newValues.getSize();
        for (int i = 0; i < size; i++) {
            model.add(newValues.getElementAt(i));
        }
    }

    public void addSourceElements(Object newValue[]) {
        fillListModel(sourceListModel, newValue);
    }

    public void setSourceElements(Object newValue[]) {
        clearSourceListModel();
        addSourceElements(newValue);
    }

    public void addDestinationElements(Object newValue[]) {
        fillListModel(destListModel, newValue);
    }

    private void fillListModel(SortedListModel model, Object newValues[]) {
        model.addAll(newValues);
    }

    public Iterator sourceIterator() {
        return sourceListModel.iterator();
    }

    public Iterator destinationIterator() {
        return destListModel.iterator();
    }

    public void setSourceCellRenderer(ListCellRenderer newValue) {
        sourceList.setCellRenderer(newValue);
    }

    public ListCellRenderer getSourceCellRenderer() {
        return sourceList.getCellRenderer();
    }

    public void setDestinationCellRenderer(ListCellRenderer newValue) {
        destList.setCellRenderer(newValue);
    }

    public ListCellRenderer getDestinationCellRenderer() {
        return destList.getCellRenderer();
    }

    public void setVisibleRowCount(int newValue) {
        sourceList.setVisibleRowCount(newValue);
        destList.setVisibleRowCount(newValue);
    }

    public int getVisibleRowCount() {
        return sourceList.getVisibleRowCount();
    }

    public void setSelectionBackground(Color newValue) {
        sourceList.setSelectionBackground(newValue);
        destList.setSelectionBackground(newValue);
    }

    public Color getSelectionBackground() {
        return sourceList.getSelectionBackground();
    }

    public void setSelectionForeground(Color newValue) {
        sourceList.setSelectionForeground(newValue);
        destList.setSelectionForeground(newValue);
    }

    public Color getSelectionForeground() {
        return sourceList.getSelectionForeground();
    }

    private void clearSourceSelected() {
        Object selected[] = sourceList.getSelectedValues();
        for (int i = selected.length - 1; i >= 0; --i) {
            sourceListModel.removeElement(selected[i]);
        }
        sourceList.getSelectionModel().clearSelection();
    }

    private void clearDestinationSelected() {
        Object selected[] = destList.getSelectedValues();
        for (int i = selected.length - 1; i >= 0; --i) {
            destListModel.removeElement(selected[i]);
        }
        destList.getSelectionModel().clearSelection();
    }

    public void start(String userID) {
        this.buyerID = userID;
        updateInventory();
        try {
            this.addSourceElements(getElementsFromInv());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String[] getElementsFromInv() {
        String[] output = new String[inventory.length];

        for (int i = 0; i < output.length; i++) {
            output[i] = "SellerID: " + inventory[i][0] + "; ItemID: " + inventory[i][1] + "; Name: " + inventory[i][2] + "; Description: " + inventory[i][4] + "; Price: $" + inventory[i][3];
        }
        return output;
    }

    /**
     * Stops the loginPanel
     */
    public void stop() {
        setFocusable(false);
        setVisible(false);
    }

    private class AddListener implements ActionListener {

        private JPanel jPanel;

        public AddListener(JPanel jpanel) {
            this.jPanel = jpanel;
        }
        public void actionPerformed(ActionEvent e) {
            Object selected[] = sourceList.getSelectedValues();

            String sellerID = "";
            String itemID = "";
            String price = "";

            for (Object item : selected) {
                String txt = item.toString();
//TODO fix names
                String re1=".*?";	// Non-greedy match on filler
                String re2="(\\d+)";	// Integer Number 1
                String re3=".*?";	// Non-greedy match on filler
                String re4="(\\d+)";	// Integer Number 2
                String re5=".*?";	// Non-greedy match on filler
                String re6="(\\d+)";	// Integer Number 3

                Pattern p = Pattern.compile(re1+re2+re3+re4+re5+re6,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                Matcher m = p.matcher(txt);
                if (m.find())
                {
                    sellerID = m.group(1);
                    itemID = m.group(2);
                    price = m.group(3);
                }
            }
            int amountAvail = getAmount(sellerID, itemID);
            int quantity = Integer.valueOf(JOptionPane.showInputDialog(jPanel, String.valueOf(amountAvail) + " units available. How many would you like?", ""));
            //TODO add purchase to a list of something
            if (quantity != -1) {
                if (quantity <= amountAvail && quantity > 0) {
                    addDestinationElements(selected);
                    clearSourceSelected();
                    increasePrice(Integer.valueOf(price) * quantity);
                    for (String[] item : inventory) {
                        if (item[0].equals(sellerID) && item[1].equals(itemID)) {
                            cartList.add(new Listing(new Item(item[2], Double.valueOf(item[3]), item[4],
                                    item[1], item[0]), quantity, item[0]));
                        }
                    }
                }
            }
        }
    }

    private int getAmount(String sellerID, String itemID) {
        for (String[] item : inventory) {
            if (item[0].equals(sellerID) && item[1].equals(itemID)) {
                return Integer.valueOf(item[5]);
            }
        }
        return -1;
    }

    private void increasePrice(int change) {
//TODO fix names
        price = price + change;
        totalPriceLabel.setText("Total Price: $" + price);
    }

    private void decreasePrice(int change) {

        price = price - change;
        totalPriceLabel.setText("Total Price: $" + price);
    }

    private class RemoveListener implements ActionListener {
        private JPanel jPanel;

        public RemoveListener(JPanel jPanel) {
            this.jPanel = jPanel;
        }

        public void actionPerformed(ActionEvent e) {
            Object selected[] = destList.getSelectedValues();

            String sellerID = "";
            String itemID = "";
            String price = "";

            for (Object item : selected) {
                String txt = item.toString();
//TODO fix names
                String re1=".*?";	// Non-greedy match on filler
                String re2="(\\d+)";	// Integer Number 1
                String re3=".*?";	// Non-greedy match on filler
                String re4="(\\d+)";	// Integer Number 2
                String re5=".*?";	// Non-greedy match on filler
                String re6="(\\d+)";	// Integer Number 3

                Pattern p = Pattern.compile(re1+re2+re3+re4+re5+re6,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                Matcher m = p.matcher(txt);
                if (m.find())
                {
                    sellerID = m.group(1);
                    itemID = m.group(2);
                    price = m.group(3);
                }
            }
            int amountInCart = cartList.get(destList.getSelectedIndex()).getQuantity();

            addSourceElements(selected);
            clearDestinationSelected();
            decreasePrice(Integer.valueOf(price) * amountInCart);
            for (Listing listing : cartList) {
                if (listing.getSellerID().equals(sellerID)) {
                    if (listing.getItem().getItemID().equals(itemID)) {
                        cartList.remove(listing);
                        break;
                    }
                }
            }


        }
    }

    private class CheckoutListener implements ActionListener {
        private JPanel jPanel;
        private MarketPlaceDriver marketPlaceDriver;

        public CheckoutListener(JPanel jPanel, MarketPlaceDriver marketPlaceDriver) {
            this.jPanel = jPanel;
            this.marketPlaceDriver = marketPlaceDriver;
        }
        public void actionPerformed(ActionEvent e) {
            //TODO Checkout here
            if (cartList.size() != 0) {
                int option = JOptionPane.showConfirmDialog(jPanel, "Are you sure you want to logout and checkout?");
                if (option == 0) {
                    logCheckout(cartList);
                    updateInvFile();
                    JOptionPane.showMessageDialog(jPanel, "Your total is $" + String.valueOf(price) + ". Thank you for shopping with us!");
                    marketPlaceDriver.resetToLogin();
                }
            } else {
                JOptionPane.showMessageDialog(jPanel, "Add items to cart first.");
            }
        }
    }

    private void updateInvFile() {
        updateInventoryWithPurchaseList();
        FileWriter fw = null;
        PrintWriter writer = null;
        String newAccount = "";
        try {
            fw = new FileWriter("Inventory.txt");
            writer = new PrintWriter(fw);
            writer.print("Seller ID<Col/>Item ID<Col/>Name<Col/>Price<Col/>Description<Col/>Quantity");
            for (int i = 0; i < inventory.length; i++) {
                if (Integer.valueOf(inventory[i][5]) > 0) {
                    writer.print("\n" + LINETAG + inventory[i][0] + COLTAG + inventory[i][1] + COLTAG
                            + inventory[i][2] + COLTAG + inventory[i][3] + COLTAG + inventory[i][4] + COLTAG + inventory[i][5]);
                }
            }
            writer.flush();
            writer.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateInventoryWithPurchaseList() {
        for (Listing listing : cartList) {
            for (int i = 0; i < inventory.length; i++) {
                if (inventory[i][0].equals(listing.getSellerID()) && inventory[i][1].equals(listing.getItem().getItemID())) {
                    inventory[i][5] = String.valueOf(Integer.valueOf(inventory[i][5]) - listing.getQuantity());
                }
            }
        }
    }

    private void logCheckout(ArrayList<Listing> cartList) {
        updateTransactionHistory();
        FileWriter fw = null;
        PrintWriter writer;
        String nextTransactionId = nextTransactionID();
        try {
            fw = new FileWriter("TransactionHistory.txt", true);
            writer = new PrintWriter(fw);
            for (Listing listing : cartList) {
                writer.print("\n" + LINETAG + nextTransactionId + COLTAG + buyerID + COLTAG + listing.getSellerID()
                        + COLTAG + listing.getItem().getItemID() + COLTAG + listing.getItem().getName() + COLTAG + listing.getItem().getPrice()
                        + COLTAG + listing.getItem().getDescription() + COLTAG + listing.getQuantity());
            }
            writer.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String nextTransactionID() {
        int max = 0;
        for (String[] transaction : transactions) {
            if (Integer.valueOf(transaction[0]) > max) {
                max = Integer.valueOf(transaction[0]);
            }
        }
        return String.valueOf(max + 1);
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
            in = new Scanner(new File("TransactionHistory.txt"));
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

    /**
     *  Updates the inventory 2d array with content from inventory.txt
     */
    private static void updateInventory() {
        inputDataInv = null;
        setArrayInv();
        fillArrayInv();
    }

    /**
     *  Fills the inventory 2d array with the contents from the inventory.txt file
     */
    private static void fillArrayInv() {
        for(int j = 1; j < inputDataInv.length; j++) {
            String currentLine = inputDataInv[j];
            String[] tmpLine = currentLine.split(COLTAG);
            for(int i = 0; i < tmpLine.length; i++) {
                String tmp = tmpLine[i];
                inventory[j - 1][i] = tmp;
            }
        }
    }

    /**
     *  Sets the inventory 2d array to the appropriate size
     */
    private static void setArrayInv() {
        int rows = 0;
        int fields = 0;
        Scanner in = null;
        String content = "";
        try {
            String inventoryFilePath = "Inventory.txt";
            in = new Scanner(new File(inventoryFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (in.hasNextLine()) {
            content = content + in.nextLine();
        }
        inputDataInv = content.split(LINETAG);
        rows = inputDataInv.length - 1;
        fields = inputDataInv[0].split(COLTAG).length;
        inventory = new String[rows][fields];
        in.close();
    }
}

class SortedListModel extends AbstractListModel {

    SortedSet model;

    public SortedListModel() {
        model = new TreeSet();
    }

    public int getSize() {
        return model.size();
    }

    public Object getElementAt(int index) {
        return model.toArray()[index];
    }

    public void add(Object element) {
        if (model.add(element)) {
            fireContentsChanged(this, 0, getSize());
        }
    }

    public void addAll(Object elements[]) {
        Collection c = Arrays.asList(elements);
        model.addAll(c);
        fireContentsChanged(this, 0, getSize());
    }

    public void clear() {
        model.clear();
        fireContentsChanged(this, 0, getSize());
    }

    public boolean contains(Object element) {
        return model.contains(element);
    }

    public Object firstElement() {
        return model.first();
    }

    public Iterator iterator() {
        return model.iterator();
    }

    public Object lastElement() {
        return model.last();
    }

    public boolean removeElement(Object element) {
        boolean removed = model.remove(element);
        if (removed) {
            fireContentsChanged(this, 0, getSize());
        }
        return removed;
    }


}

