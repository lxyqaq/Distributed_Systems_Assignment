package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @ClassName ModifyProductFrame
 * @Description ModifyProductFrame
 * @Author Xiangyu Liu @Email A00279565@student.ait.ie
 * @Date 2021/4/13 22:19
 * @Version 1.0
 */
public class ModifyProductFrame extends JFrame {

    private static final long serialVersionUID = 303139940819412480L;
    private ClientContext clientContext;
    private JTextField productNameText = new JTextField();
    private JTextField productNoText = new JTextField();
    private JTextField storageText = new JTextField();
    private JTextField purchasePriceText = new JTextField();
    private JTextField priceText = new JTextField();
    private JTextField alarmStorageText = new JTextField();
    private JComboBox catCom = new JComboBox();

    public ModifyProductFrame() {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        init();
    }

    public void init() {
        this.setTitle("Edit product information");
        this.setSize(600, 400);
        this.setContentPane(createContentPane());
        int windowWidth = this.getWidth();
        int windowHeight = this.getHeight();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        this.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);
        this.setResizable(false);
    }

    private Container createContentPane() {
        JPanel p0 = new JPanel(new BorderLayout());
        p0.add(new JLabel("\n"), BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(8, 3, 3, 5));
        catCom = new JComboBox();
        catCom.addItem("Drinks");
        catCom.addItem("Food");
        catCom.addItem("Wine");
        catCom.addItem("Cigarette");
        catCom.addItem("Snacks");
        catCom.addItem("Household");
        panel.add(new JLabel(leftPadding("Categories")));
        panel.add(catCom);
        panel.add(new JLabel(leftPadding("Name")));
        panel.add(productNameText);
        panel.add(new JLabel(leftPadding("Product Number")));
        panel.add(productNoText);
        panel.add(new JLabel(leftPadding("Purchase price")));
        panel.add(purchasePriceText);
        panel.add(new JLabel(leftPadding("Price")));
        panel.add(priceText);
        panel.add(new JLabel(leftPadding("Stock")));
        panel.add(storageText);
        panel.add(new JLabel(leftPadding("Reminder")));
        panel.add(alarmStorageText);
        p0.add(panel, BorderLayout.CENTER);
        p0.add(new JLabel("                             "), BorderLayout.EAST);
        JPanel btnPanel = new JPanel();
        JButton okBtn = new JButton("Update");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientContext.updateProduct();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                JOptionPane.showMessageDialog(ModifyProductFrame.this, "The product has been modified successfully!", "Product storage", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        btnPanel.add(okBtn);
        JButton canelBtn = new JButton("Cancel");
        canelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientContext.showOrHideModifyProductFrame(false);
            }
        });
        btnPanel.add(canelBtn);
        p0.add(btnPanel, BorderLayout.SOUTH);

        return p0;
    }

    public ClientContext getClientContext() {
        return clientContext;
    }

    public void setClientContext(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    public JTextField getProductNameText() {
        return productNameText;
    }

    public void setProductNameText(JTextField productNameText) {
        this.productNameText = productNameText;
    }

    public JTextField getStorageText() {
        return storageText;
    }

    public void setStorageText(JTextField storageText) {
        this.storageText = storageText;
    }

    public JTextField getPurchasePriceText() {
        return purchasePriceText;
    }

    public void setPurchasePriceText(JTextField purchasePriceText) {
        this.purchasePriceText = purchasePriceText;
    }

    public JTextField getPriceText() {
        return priceText;
    }

    public void setPriceText(JTextField priceText) {
        this.priceText = priceText;
    }

    public JTextField getProductNoText() {
        return productNoText;
    }

    public void setProductNoText(JTextField productNoText) {
        this.productNoText = productNoText;
    }

    public JTextField getAlarmStorageText() {
        return alarmStorageText;
    }

    public void setAlarmStorageText(JTextField alarmStorageText) {
        this.alarmStorageText = alarmStorageText;
    }

    public JComboBox getCatCom() {
        return catCom;
    }

    public void setCatCom(JComboBox catCom) {
        this.catCom = catCom;
    }

    private String leftPadding(String str) {
        return "                                  " + str;
    }

    public static void main(String[] args) {
        ModifyProductFrame npf = new ModifyProductFrame();
        npf.init();
        npf.setVisible(true);
    }

}
