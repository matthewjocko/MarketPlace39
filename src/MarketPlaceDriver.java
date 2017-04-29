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
    BuyerPanel buyerPanel = new BuyerPanel(this);
    SellerPanel sellerPanel = new SellerPanel();
    AdminPanel adminPanel = new AdminPanel();
    String accountType = "";

    /**
    * Defines the default behaviors for the MarketPlaceDriver
    */
    public MarketPlaceDriver() {
        super();
        setTitle("Market Place");
        setSize(loginPanel.getSize());
        setLayout(new GridLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start();
    }

    /**
     *  start is called to start the frame and start the maketplace
     */
    public void start() {
        this.add(loginPanel);
        loginPanel.start();  //Uncomment for deployment
//        this.add(adminPanel);
//        adminPanel.start();
        this.setVisible(true);

    }

    /**
     * Sets the account type
     * @param accountType Type to set
     */
    public void setAccountType(String accountType, String userID) {
        System.out.println("Check 1");
        this.accountType = accountType;
        loginPanel.stop();
        remove(loginPanel);
        if (accountType.equals("Seller")) {
            add(sellerPanel);
            sellerPanel.start(userID);
        } else if (accountType.equals("Buyer")) {
            add(buyerPanel);
            buyerPanel.start(userID);
        } else if (accountType.equals("AdminPanel")) {
            add(adminPanel);
            adminPanel.start();
        }
    }

    public void resetToLogin() {
        buyerPanel.stop();
        remove(buyerPanel);
        start();
    }
}
