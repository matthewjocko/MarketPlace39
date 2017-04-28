import javax.swing.*;
import java.awt.*;

/**
 * Charles Frank
 * charfran
 * Apr 24, 2017
 */
public class MarketPlaceDriver extends JFrame {

    public final int FRAME_WIDTH = 1000;
    public final int FRAME_HEIGHT = 1000;

    LoginPanel loginPanel = new LoginPanel(this);
        BuyerPanel buyerPanel = new BuyerPanel();
        SellerPanel sellerPanel = new SellerPanel();
    //    AdminPanel adminPanel = new AdminPanel();     TODO uncomment once sellerPanel is created
    String accountType = "";

    //TODO add JFrame fields

    public MarketPlaceDriver() {
        super();
        setTitle("Market Place");
        setSize(loginPanel.getSize());
        setLayout(new GridLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Start();
    }

    /**
     *  Start is called to start the frame and start the maketplace
     */
    public void Start() {
//        this.add(loginPanel);
//        loginPanel.start();  //Uncomment for deployment
        this.add(sellerPanel);
        sellerPanel.start("-2");
        this.setVisible(true);

    }

    /**
     * Sets the account type
     * @param accountType Type to set
     */
    public void setAccountType(String accountType, String userID) {
        this.accountType = accountType;
        loginPanel.stop();
        remove(loginPanel);
        if (accountType == "Seller") {
            add(sellerPanel);
            sellerPanel.start(userID);
        } else if (accountType == "Buyer") {
            add(buyerPanel);
            buyerPanel.start(userID);
        } else if (accountType == "Admin") {
//            add(adminPanel);
//            adminPanel.start();
        }
    }
}
