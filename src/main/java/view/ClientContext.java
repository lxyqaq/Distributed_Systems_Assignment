package view;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import dao.ProductDao;
import dao.PurchaseDao;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import pojo.*;


/**
 * @ClassName ClientContext
 * @Description ClientContext
 * @Author Xiangyu Liu @Email A00279565@student.ait.ie
 * @Date 2021/4/10 22:19
 * @Version 1.0
 */
public class ClientContext {

    private MainFrame mainFrame;
    private PurchaseFrame purchaseFrame;
    private NewProductFrame newProductFrame;
    private StorageFrame storageFrame;
    private ModifyProductFrame modifyProductFrame;
    private StockHistoryFrame stockHistoryFrame;
    private GetProductFrame getProductFrame;
    private Product currProduct = new Product();

    public void saveNewProduct() throws Exception {

        Product p = new Product();
        p.setName(newProductFrame.getProductNameText().getText());
        p.setAlarmStorage(Integer.valueOf(newProductFrame.getAlarmStorageText().getText()));
        p.setCategory(newProductFrame.getCatCom().getSelectedItem().toString());
        p.setPrice(Double.parseDouble(newProductFrame.getPriceText().getText()));
        p.setProductNo(newProductFrame.getProductNoText().getText());
        p.setPurPrice(Double.parseDouble(newProductFrame.getPurchasePriceText().getText()));
        p.setStorage(Integer.parseInt(newProductFrame.getStorageText().getText()));

        String xml = "<product>\n" +
                "    <productNo>" + p.getProductNo() + "</productNo>\n" +
                "    <name>" + p.getName() + "</name>\n" +
                "    <category>" + p.getCategory() + "</category>\n" +
                "    <price>" + p.getPrice() + "</price>\n" +
                "    <purPrice>" + p.getPurPrice() + "</purPrice>\n" +
                "    <storage>" + p.getStorage() + "</storage>\n" +
                "    <alarmStorage>" + p.getAlarmStorage() + "</alarmStorage>\n" +
                "</product>";

        System.out.println(xml);
        //Response models
        CloseableHttpResponse response = null;
        try {
            //Create URI and set parameters
            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost")
                    .setPort(8080)
                    .setPath("/DSAssignment/api/products").build();
            System.out.println(uri.toString());
            //Create HttpPost
            HttpPost httpPost = new HttpPost(uri);
            //Set the header
            httpPost.setHeader("Accept", "application/xml");
            //Get the Http Client
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //Encapsulating strings into entities
            StringEntity se = new StringEntity(xml);
            se.setContentEncoding("UTF-8");
            se.setContentType("application/xml");
            httpPost.setEntity(se);
            //Execution of requests by the client
            response = httpClient.execute(httpPost);
        } finally {
            response.close();
        }
        newProductFrame.setVisible(false);
    }

    public void updateProduct() throws Exception {

        Product p = currProduct;
        p.setName(modifyProductFrame.getProductNameText().getText());
        p.setAlarmStorage(Integer.valueOf(modifyProductFrame.getAlarmStorageText().getText()));
        p.setCategory(modifyProductFrame.getCatCom().getSelectedItem().toString());
        p.setPrice(Double.parseDouble(modifyProductFrame.getPriceText().getText()));
        p.setProductNo(modifyProductFrame.getProductNoText().getText());
        p.setPurPrice(Double.parseDouble(modifyProductFrame.getPurchasePriceText().getText()));
        p.setStorage(Integer.parseInt(modifyProductFrame.getStorageText().getText()));

        String xml = "<product>\n" +
                "    <productId>" + p.getProductId() + "</productId>\n" +
                "    <productNo>" + p.getProductNo() + "</productNo>\n" +
                "    <name>" + p.getName() + "</name>\n" +
                "    <category>" + p.getCategory() + "</category>\n" +
                "    <price>" + p.getPrice() + "</price>\n" +
                "    <purPrice>" + p.getPurPrice() + "</purPrice>\n" +
                "    <storage>" + p.getStorage() + "</storage>\n" +
                "    <alarmStorage>" + p.getAlarmStorage() + "</alarmStorage>\n" +
                "</product>";

        System.out.println(xml);

        //Response models
        CloseableHttpResponse response = null;
        try {

            //Create URI and set parameters
            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost")
                    .setPort(8080)
                    .setPath("/DSAssignment/api/products").build();

            System.out.println(uri.toString());

            //Create HttpPut
            HttpPut httpPut = new HttpPut(uri);
            //Set the header
            httpPut.setHeader("Accept", "application/xml");
            //Get the Http Client
            CloseableHttpClient httpClient = HttpClients.createDefault();

            //Encapsulating strings into entities
            StringEntity se = new StringEntity(xml);
            se.setContentEncoding("UTF-8");
            se.setContentType("application/xml");
            httpPut.setEntity(se);

            //Execution of requests by the client
            response = httpClient.execute(httpPut);

        } finally {
            response.close();
        }
        storageFrame.refreshTableData();
        modifyProductFrame.setVisible(false);
        currProduct = null;
    }

    public void deleteProduct(int pid) throws Exception {
        CloseableHttpResponse response = null;
        try {
            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost")
                    .setPort(8080)
                    .setPath("/DSAssignment/api/products/" + pid).build();

            System.out.println(uri.toString());

            //DELETE
            HttpDelete httpDelete = new HttpDelete(uri);
            httpDelete.setHeader("Accept", "application/xml");
            CloseableHttpClient httpClient = HttpClients.createDefault();

            response = httpClient.execute(httpDelete);

        } finally {
            response.close();
        }
        storageFrame.refreshTableData();
    }

    public void purchase() throws Exception {

        String txt = purchaseFrame.getPurNumbers().getText();
        if (txt == null || txt.trim().length() == 0) {
            JOptionPane.showMessageDialog(purchaseFrame, "Please enter the quantity of goods!", "Product storage", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int num = Integer.parseInt(txt);
        currProduct = ProductDao.instance.findProduct(purchaseFrame.getProductCombox().getSelectedItem().toString());
        int storage = currProduct.getStorage() + num;

        String xml = "<product>\n" +
                "    <productId>" + currProduct.getProductId() + "</productId>\n" +
                "    <productNo>" + currProduct.getProductNo() + "</productNo>\n" +
                "    <name>" + currProduct.getName() + "</name>\n" +
                "    <category>" + currProduct.getCategory() + "</category>\n" +
                "    <price>" + currProduct.getPrice() + "</price>\n" +
                "    <purPrice>" + currProduct.getPurPrice() + "</purPrice>\n" +
                "    <storage>" + storage + "</storage>\n" +
                "    <alarmStorage>" + currProduct.getAlarmStorage() + "</alarmStorage>\n" +
                "</product>";

        System.out.println(xml);

        CloseableHttpResponse response = null;
        try {
            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost")
                    .setPort(8080)
                    .setPath("/DSAssignment/api/products").build();

            System.out.println(uri.toString());

            //PUT
            HttpPut httpPut = new HttpPut(uri);
            httpPut.setHeader("Accept", "application/xml");
            CloseableHttpClient httpClient = HttpClients.createDefault();

            StringEntity se = new StringEntity(xml);
            se.setContentEncoding("UTF-8");
            se.setContentType("application/xml");
            httpPut.setEntity(se);
            response = httpClient.execute(httpPut);

        } finally {
            response.close();
        }

        JOptionPane.showMessageDialog(purchaseFrame, "The storage is successful!", "Product storage", JOptionPane.INFORMATION_MESSAGE);
        purchaseFrame.getPurNumbers().setText("");

        String xmlPurchase = "<purchase>\n" +
                "        <purchaseCategory>" + currProduct.getCategory() + "</purchaseCategory>\n" +
                "        <purchaseName>" + currProduct.getName() + "</purchaseName>\n" +
                "        <purchaseQuantity>" + num + "</purchaseQuantity>\n" +
                "    </purchase>";

        System.out.println(xmlPurchase);

        CloseableHttpResponse response2 = null;
        try {
            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost")
                    .setPort(8080)
                    .setPath("/DSAssignment/api/purchases").build();

            System.out.println(uri.toString());

            //POST
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setHeader("Accept", "application/xml");
            CloseableHttpClient httpClient = HttpClients.createDefault();

            StringEntity se = new StringEntity(xmlPurchase);
            se.setContentEncoding("UTF-8");
            se.setContentType("application/xml");
            httpPost.setEntity(se);
            response2 = httpClient.execute(httpPost);

        } finally {
            response2.close();
        }
        currProduct = null;
    }

    public void showOrHideModifyProductFrame(boolean showOrHide) {
        if (showOrHide) {
            modifyProductFrame.getCatCom().setSelectedIndex(0);
            modifyProductFrame.getAlarmStorageText().setText(currProduct.getAlarmStorage() + "");
            modifyProductFrame.getProductNameText().setText(currProduct.getName());
            modifyProductFrame.getProductNoText().setText(currProduct.getProductNo());
            modifyProductFrame.getStorageText().setText(currProduct.getStorage() + "");
            modifyProductFrame.getPurchasePriceText().setText(currProduct.getPurPrice() + "");
            modifyProductFrame.getPriceText().setText(currProduct.getPrice() + "");
        }
        modifyProductFrame.setVisible(showOrHide);
    }

    public void showOrHideStockHistory(boolean showOrHide) {
        if (showOrHide) {
            try {
                stockHistoryFrame.refreshTableData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        stockHistoryFrame.setVisible(showOrHide);
    }

    public void showOrHideNewProductFrame(boolean showOrHide) {
        if (showOrHide) {
            newProductFrame.getCatCom().setSelectedIndex(0);
            newProductFrame.getAlarmStorageText().setText("");
            newProductFrame.getProductNameText().setText("");
            newProductFrame.getProductNoText().setText("");
            newProductFrame.getStorageText().setText("");
            newProductFrame.getPurchasePriceText().setText("");
            newProductFrame.getPriceText().setText("");
        }
        newProductFrame.setVisible(showOrHide);
    }

    public void showOrHideStorageFrame(boolean showOrHide) {
        if (showOrHide) {
            storageFrame.refreshTableData();
        }
        storageFrame.setVisible(showOrHide);
    }

    public void showOrHidePurchaseFrame(boolean showOrHide) {
        purchaseFrame.setVisible(showOrHide);
    }

    public void writeToFile(ResultSet rs) {
        try {
            FileWriter outputFile = new FileWriter("product.csv");
            PrintWriter printWriter = new PrintWriter(outputFile);
            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();
            for (int i = 0; i < numColumns; i++) {
                printWriter.print(rsmd.getColumnLabel(i + 1) + ",");
            }
            printWriter.print("\n");
            while (rs.next()) {
                for (int i = 0; i < numColumns; i++) {
                    printWriter.print(rs.getString(i + 1) + ",");
                }
                printWriter.print("\n");
                printWriter.flush();
            }
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSellFrame() {
        getProductFrame.setVisible(true);
    }

    public void export() {
        writeToFile(ProductDao.instance.getProducts());
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public PurchaseFrame getPurchaseFrame() {
        return purchaseFrame;
    }

    public void setPurchaseFrame(PurchaseFrame purchaseFrame) {
        this.purchaseFrame = purchaseFrame;
    }

    public NewProductFrame getNewProductFrame() {
        return newProductFrame;
    }

    public void setNewProductFrame(NewProductFrame newProductFrame) {
        this.newProductFrame = newProductFrame;
    }

    public StorageFrame getStorageFrame() {
        return storageFrame;
    }

    public void setStorageFrame(StorageFrame storageFrame) {
        this.storageFrame = storageFrame;
    }

    public Product getCurrProduct() {
        return currProduct;
    }

    public void setCurrProduct(Product currProduct) {
        this.currProduct = currProduct;
    }

    public ModifyProductFrame getModifyProductFrame() {
        return modifyProductFrame;
    }

    public void setModifyProductFrame(ModifyProductFrame modifyProductFrame) {
        this.modifyProductFrame = modifyProductFrame;
    }

    public StockHistoryFrame getStockHistoryFrame() {
        return stockHistoryFrame;
    }

    public void setStockHistoryFrame(StockHistoryFrame stockHistoryFrame) {
        this.stockHistoryFrame = stockHistoryFrame;
    }

    public GetProductFrame getCartFrame() {
        return getProductFrame;
    }

    public void setCartFrame(GetProductFrame getProductFrame) {
        this.getProductFrame = getProductFrame;
    }

}
