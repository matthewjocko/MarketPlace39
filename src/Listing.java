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


public class Listing {

    private Item item;
    private int quantity;
    private String sellerID;

    /**
     * Method that initializes the required fields
     * @param item
     * @param quantity
     * @param sellerID
     */
    public Listing(Item item, int quantity, String sellerID) {
        this.item = item;
        this.quantity = quantity;
        this.sellerID = sellerID;
    }

    /**
     * Method that returns the item
     * @return item
     */
    public Item getItem() {
        return item;
    }

    /**
     * returns the quantity of the item
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * returns the sellerID for the listing
     * @return sellerID
     */
    public String getSellerID() {
        return sellerID;
    }

    /**
     * method that handles the situation when an item is sold
     * @param amount
     */
    public void sold(int amount) {
        quantity = quantity - amount;
    }

}
