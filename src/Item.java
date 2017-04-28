/**
 * Final Project C212
 * Due: 4-28-17
 *
 * @Author: Matthew Lieberman
 * @Author: Adam Kummer
 * @Author: Charles Frank
 *
 * Last Updated: 4-28-17
 *
 */


public class Item {

    private double price;
    private String description;
    private String Name;
    private String itemID;
    private String sellerID;

    /**
     * This method is the default constructor to make a new Item object
     * @param name
     * @param price
     * @param description
     * @param itemID
     * @param sellerID
     */
    public Item(String name, double price, String description, String itemID, String sellerID) {
        this.price = price;
        this.description = description;
        Name = name;
        this.itemID = itemID;
        this.sellerID = sellerID;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return Name;
    }

    public String getItemID() {
        return itemID;
    }

    public String getSellerID() {
        return sellerID;
    }
}
