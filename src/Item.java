

/**
 * Created by AdamKummer on 4/27/17.
 */
public class Item {
    private double price;

    private String description;
    private String Name;
    private int ID;
    private int sellerID;

    public Item(double price, String description, String name, int ID, int sellerID) {
        this.price = price;
        this.description = description;
        Name = name;
        this.ID = ID;
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

    public int getID() {
        return ID;
    }

    public int getSellerID() {
        return sellerID;
    }
}
