package pojo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ParseProduct
 * @Description TODO
 * @Author lxyqaq @Email A00279565@student.ait.ie
 * @Date 2021/4/14 22:26
 * @Version 1.0
 */
public class ParseProduct {

    boolean inProduct = false;
    boolean inProductId = false;
    boolean inProductNo = false;
    boolean inName = false;
    boolean inCategory = false;
    boolean inPrice = false;
    boolean inPurPrice = false;
    boolean inStorage = false;
    boolean inAlarmStorage = false;

    Product currentProduct;

    public Product doParseProducts(String s) throws Exception {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser pullParser = factory.newPullParser();
        pullParser.setInput(new StringReader(s));
        processDocument(pullParser);
        return currentProduct;
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

        if (name.equals("product")) {
            inProduct = true;
            currentProduct = new Product();
        } else if (name.equals("productId")) {
            inProductId = true;
        } else if (name.equals("productNo")) {
            inProductNo = true;
        } else if (name.equals("name")) {
            inName = true;
        } else if (name.equals("category")) {
            inCategory = true;
        } else if (name.equals("price")) {
            inPrice = true;
        } else if (name.equals("purPrice")) {
            inPurPrice = true;
        } else if (name.equals("storage")) {
            inStorage = true;
        } else if (name.equals("alarmStorage")) {
            inAlarmStorage = true;
        }

    }

    public void processEndElement(XmlPullParser event) {

        String name = event.getName();

        if (name.equals("product")) {
            inProduct = false;
        } else if (name.equals("productId")) {
            inProductId = false;
        } else if (name.equals("productNo")) {
            inProductNo = false;
        } else if (name.equals("name")) {
            inName = false;
        } else if (name.equals("category")) {
            inCategory = false;
        } else if (name.equals("price")) {
            inPrice = false;
        } else if (name.equals("purPrice")) {
            inPurPrice = false;
        } else if (name.equals("storage")) {
            inStorage = false;
        } else if (name.equals("alarmStorage")) {
            inAlarmStorage = false;
        }

    }

    public void processText(XmlPullParser event) throws XmlPullParserException {

        if (inProductId) {
            String s = event.getText();
            currentProduct.setProductId(Integer.parseInt(s));
        }

        if (inProductNo) {
            String s = event.getText();
            currentProduct.setProductNo(s);
        }

        if (inName) {
            String s = event.getText();
            currentProduct.setName(s);
        }

        if (inCategory) {
            String s = event.getText();
            currentProduct.setCategory(s);
        }

        if (inPrice) {
            String s = event.getText();
            currentProduct.setPrice(Double.parseDouble(s));
        }

        if (inPurPrice) {
            String s = event.getText();
            currentProduct.setPurPrice(Double.parseDouble(s));
        }

        if (inStorage) {
            String s = event.getText();
            currentProduct.setStorage(Integer.parseInt(s));
        }

        if (inAlarmStorage) {
            String s = event.getText();
            currentProduct.setAlarmStorage(Integer.parseInt(s));
        }

    }

}


