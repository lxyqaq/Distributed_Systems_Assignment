package pojo;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @ClassName Purchase
 * @Description TODO
 * @Author lxyqaq @Email A00279565@student.ait.ie
 * @Date 2021/4/16 17:55
 * @Version 1.0
 */
@XmlRootElement(name = "purchase")
@XmlType(propOrder = {"purchaseId", "purchaseCategory", "purchaseName", "purchaseQuantity"})
public class Purchase {

    private int purchaseId;
    private String purchaseCategory;
    private String purchaseName;
    private int purchaseQuantity;

    public Purchase() {

    }

    public Purchase(int purchaseId, String purchaseCategory, String purchaseName, int purchaseQuantity) {
        this.purchaseId = purchaseId;
        this.purchaseCategory = purchaseCategory;
        this.purchaseName = purchaseName;
        this.purchaseQuantity = purchaseQuantity;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getPurchaseCategory() {
        return purchaseCategory;
    }

    public void setPurchaseCategory(String purchaseCategory) {
        this.purchaseCategory = purchaseCategory;
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "purchaseId=" + purchaseId +
                ", purchaseCategory='" + purchaseCategory + '\'' +
                ", purchaseName='" + purchaseName + '\'' +
                ", purchaseQuantity=" + purchaseQuantity +
                '}';
    }

}
