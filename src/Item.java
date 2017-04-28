

/**
 * Created by AdamKummer on 4/27/17.
 */
public class Item {
    private double price;

    private String description;
    private String Name;
    private String itemID;
    private String sellerID;

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
