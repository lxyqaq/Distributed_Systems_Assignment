import view.*;

/**
 * @ClassName Application
 * @Description Start the Application
 * @Author Xiangyu Liu @Email A00279565@student.ait.ie
 * @Date 2021/4/10 22:19
 * @Version 1.0
 */
public class Application {

    public static void main(String[] args) {

        /*
         * Instantiate each Frame and Controller ClientContext.
         * Here we draw on the idea of IOC, there is only one controller instance during the entire program running, and there is only one instance for each Frame.
         * Create all instances here and inject them into each other, so that different Frames can refer to the methods in the controller,
         * and the controller can also operate all the Frames and their controls.
         * Because it is a singleton, it will not cause object confusion.
         * */
        ClientContext clientContext = new ClientContext();
        MainFrame mainFrame = new MainFrame();
        PurchaseFrame purchaseFrame = new PurchaseFrame();
        StorageFrame storageFrame = new StorageFrame();
        NewProductFrame newProductFrame = new NewProductFrame();
        StockHistoryFrame stockHistoryFrame = new StockHistoryFrame();
        ModifyProductFrame modifyProductFrame = new ModifyProductFrame();
        GetProductFrame getProductFrame = new GetProductFrame();

        /*
         * Inject all Frame instances into ClientContext
         * */
        clientContext.setMainFrame(mainFrame);
        clientContext.setPurchaseFrame(purchaseFrame);
        clientContext.setStorageFrame(storageFrame);
        clientContext.setNewProductFrame(newProductFrame);
        clientContext.setModifyProductFrame(modifyProductFrame);
        clientContext.setStockHistoryFrame(stockHistoryFrame);
        clientContext.setCartFrame(getProductFrame);

        /*
         * Inject the ClientContext instance into each Frame instance
         * */
        mainFrame.setClientContext(clientContext);
        purchaseFrame.setClientContext(clientContext);
        newProductFrame.setClientContext(clientContext);
        storageFrame.setClientContext(clientContext);
        modifyProductFrame.setClientContext(clientContext);

        //The login window is displayed, and the login and successful operations are handled by the controller
        mainFrame.setVisible(true);

    }

}
