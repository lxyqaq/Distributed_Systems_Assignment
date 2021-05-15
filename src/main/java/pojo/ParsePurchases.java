package pojo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ParsePurchases
 * @Description TODO
 * @Author lxyqaq @Email A00279565@student.ait.ie
 * @Date 2021/4/16 18:13
 * @Version 1.0
 */
public class ParsePurchases {

    boolean inPurchases = false;
    boolean inPurchase = false;
    boolean inPurchaseId = false;
    boolean inPurchaseCategory = false;
    boolean inPurchaseName = false;
    boolean inPurchaseQuantity = false;

    Purchase currentPurchase;
    List<Purchase> currentPurchaseList;

    public List<Purchase> doParsePurchases(String s) throws Exception {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser pullParser = factory.newPullParser();
        pullParser.setInput(new StringReader(s));
        processDocument(pullParser);
        return currentPurchaseList;
    }

    public void processDocument(XmlPullParser pullParser) throws XmlPullParserException, IOException {
        int eventType = pullParser.getEventType();
        do {
            if (eventType == XmlPullParser.START_DOCUMENT) {
                System.out.println("Start Document");
            } else if (eventType == XmlPullParser.END_DOCUMENT) {
                System.out.println("End Document");
            } else if (eventType == XmlPullParser.START_TAG) {
                processStartElement(pullParser);
            } else if (eventType == XmlPullParser.END_TAG) {
                processEndElement(pullParser);
            } else if (eventType == XmlPullParser.TEXT) {
                processText(pullParser);
            }
            eventType = pullParser.next();
        } while (eventType != XmlPullParser.END_DOCUMENT);
    }

    public void processStartElement(XmlPullParser event) {

        String name = event.getName();

        if (name.equals("purchases")) {
            inPurchases = true;
            currentPurchaseList = new ArrayList<>();
        } else if (name.equals("purchase")) {
            inPurchase = true;
            currentPurchase = new Purchase();
        } else if (name.equals("purchaseId")) {
            inPurchaseId = true;
        } else if (name.equals("purchaseCategory")) {
            inPurchaseCategory = true;
        } else if (name.equals("purchaseName")) {
            inPurchaseName = true;
        } else if (name.equals("purchaseQuantity")) {
            inPurchaseQuantity = true;
        }

    }

    public void processEndElement(XmlPullParser event) {

        String name = event.getName();

        if (name.equals("purchases")) {
            inPurchases = false;
        } else if (name.equals("purchase")) {
            inPurchase = false;
            currentPurchaseList.add(currentPurchase);
        } else if (name.equals("purchaseId")) {
            inPurchaseId = false;
        } else if (name.equals("purchaseCategory")) {
            inPurchaseCategory = false;
        } else if (name.equals("purchaseName")) {
            inPurchaseName = false;
        } else if (name.equals("purchaseQuantity")) {
            inPurchaseQuantity = false;
        }

    }

    public void processText(XmlPullParser event) throws XmlPullParserException {

        if (inPurchaseId) {
            String s = event.getText();
            currentPurchase.setPurchaseId(Integer.parseInt(s));
        }

        if (inPurchaseCategory) {
            String s = event.getText();
            currentPurchase.setPurchaseCategory(s);
        }

        if (inPurchaseName) {
            String s = event.getText();
            currentPurchase.setPurchaseName(s);
        }

        if (inPurchaseQuantity) {
            String s = event.getText();
            currentPurchase.setPurchaseQuantity(Integer.parseInt(s));
        }

    }

}
