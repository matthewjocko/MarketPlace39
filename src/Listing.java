

/**
 * Created by AdamKummer on 4/27/17.
 */



public class Listing {

    private Item item;
    private int quantity;
    private String sellerID;


    public Listing(Item item, int quantity, String sellerID) {
        this.item = item;
        this.quantity = quantity;
        this.sellerID = sellerID;
    }

    public Listing(int quantity, String sellerID) {
        this.quantity = quantity;
        this.sellerID = sellerID;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void sold(int amount) {
        quantity = quantity - amount;
    }

}
