package pojo;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @ClassName Product
 * @Description TODO
 * @Author Xiangyu Liu @Email A00279565@student.ait.ie
 * @Date 2021/4/12 14:46
 * @Version 1.0
 */
@XmlRootElement(name = "product")
@XmlType(propOrder = {"productId", "productNo", "name", "category", "price", "purPrice", "storage", "alarmStorage"})
public class Product {

    private int productId;
    private String productNo;
    private String name;
    private String category;
    private double price;
    private double purPrice;
    private int storage;
    private int alarmStorage;

    public Product() {

    }

    public Product(String productNo, String name, String category, double price, double purPrice, int storage, int alarmStorage) {
        this.productNo = productNo;
        this.name = name;
        this.category = category;
        this.price = price;
        this.purPrice = purPrice;
        this.storage = storage;
        this.alarmStorage = alarmStorage;
    }

    public Product(int productId, String productNo, String name, String category, double price, double purPrice, int storage, int alarmStorage) {
        this.productId = productId;
        this.productNo = productNo;
        this.name = name;
        this.category = category;
        this.price = price;
        this.purPrice = purPrice;
        this.storage = storage;
        this.alarmStorage = alarmStorage;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPurPrice() {
        return purPrice;
    }

    public void setPurPrice(double purPrice) {
        this.purPrice = purPrice;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public int getAlarmStorage() {
        return alarmStorage;
    }

    public void setAlarmStorage(int alarmStorage) {
        this.alarmStorage = alarmStorage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productNo='" + productNo + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", purPrice=" + purPrice +
                ", storage=" + storage +
                ", alarmStorage=" + alarmStorage +
                '}';
    }
}
