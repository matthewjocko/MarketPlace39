import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Charles Frank
 * charfran
 * Apr 27, 2017
 */
public class BuyerPanel extends JPanel {
    private String sellerID;
    private File userfile;
    private String[][] inventory;
    private String[] inputData;
    private ArrayList<Listing> listings;

    private final String LINETAG = "<Line/>";
    private final String ROWTAG = "<Col/>";
    private final int FRAME_WIDTH = 1000;
    private final int FRAME_HEIGHT = 1000;
    private final int DEFAULT_COLUNM_SIZE = 25;

    public BuyerPanel() {
        this.userfile = new File("Inventory.txt");
        listings = new ArrayList<Listing>();
    }

    /**
     * Starts the BuyerPanel
     */
    public void start(String sellerID) {
        setFocusable(true);
        setVisible(true);
        this.sellerID = sellerID;
        updateInventory();
    }

    /**
     *  Updates the inventory 2d array with content from inventory.txt
     */
    private void updateInventory() {
        inputData = null;
        setArray();
        fillArray();
        updateListings();
    }

    /**
     * Updates the listings available
     */
    private void updateListings() {
        for (String[] currentRow : inventory) {
            listings.add(new Listing(new Item(currentRow[2], Double.valueOf(currentRow[3]), currentRow[4],
                    currentRow[1], currentRow[0]), Integer.valueOf(currentRow[5]), currentRow[0]));
        }
    }

    /**
     *  Fills the inventory 2d array with the contents from the inventory.txt file
     */
    private void fillArray() {
        for(int j = 0; j < inputData.length; j++) {
            String currentLine = inputData[j];
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
        fields = inputData[0].split(ROWTAG).length;
        inventory = new String[rows][fields];
        in.close();
    }
}
