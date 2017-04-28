

/**
 * Created by AdamKummer on 4/27/17.
 */
public class Listing {

    private Item item;
    private int quantity;
    private int sellerID;


    public Listing(Item item, int quantity, int sellerID) {
        this.item = item;
        this.quantity = quantity;
        this.sellerID = sellerID;

    }
    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSellerID() {
        return sellerID;
    }



}
