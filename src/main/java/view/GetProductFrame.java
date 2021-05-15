package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import pojo.ParseProduct;
import pojo.Product;

/**
 * @ClassName CartFrame
 * @Description Shopping cart page
 * @Author Xiangyu Liu @Email A00279565@student.ait.ie
 * @Date 2021/4/10 22:19
 * @Version 1.0
 */
public class GetProductFrame extends JFrame {

    public static void main(String[] args) {
        GetProductFrame mf = new GetProductFrame();
        mf.init();
        mf.setVisible(true);
    }

    private static final long serialVersionUID = -8808883923263763897L;

    private ClientContext clientContext;
    private JScrollPane storagePanel;
    private JTable storageTable;
    private JTextField productQty;
    private JTextField productNumField;
    private JButton addProductBtn;
    private String[] rowname;
    private String[][] data = new String[1][8];

    public GetProductFrame() {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        init();
    }

    public void init() {
        this.setTitle("Basket");
        this.setSize(760, 320);
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
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createTopPanel(), BorderLayout.NORTH);
        panel.add(createStoragePanel(), BorderLayout.CENTER);
        panel.add(createButtonPanel(), BorderLayout.SOUTH);
        return panel;
    }

    private Component createTopPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Product ID"));
        productNumField = new JTextField(10);
        panel.add(productNumField);
        addProductBtn = new JButton("GET");
        panel.add(addProductBtn);
        addProductBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    getProduct();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        return panel;
    }

    private Component createButtonPanel() {
        JPanel panel = new JPanel();
        JButton okBtn = new JButton("Close");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GetProductFrame.this.setVisible(false);
            }
        });
        panel.add(okBtn);
        return panel;
    }

    public void getProduct() throws Exception {

        String number = productNumField.getText();

        //Response models
        CloseableHttpResponse response = null;
        try {

            //Create URI and set parameters
            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost")
                    .setPort(8080)
                    .setPath("/DSAssignment/api/products/product/" + number).build();

            System.out.println(uri.toString());

            //Create HttpGet
            HttpGet httpGet = new HttpGet(uri);
            //Set the header
            httpGet.setHeader("Accept", "application/xml");
            //Get the Http Client
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //Execution of requests by the client
            response = httpClient.execute(httpGet);

            //Getting the response entity from the response model
            HttpEntity entity = response.getEntity();
            String text = EntityUtils.toString(entity);
            System.out.println(text);

            Product product = new ParseProduct().doParseProducts(text);
            System.out.println(product.toString());

            //Populate object data into JTable
            String[][] ret = new String[1][8];
            ret[0][0] = product.getProductId() + "";
            ret[0][1] = product.getProductNo();
            ret[0][2] = product.getCategory();
            ret[0][3] = product.getName();
            ret[0][4] = product.getPurPrice() + "";
            ret[0][5] = product.getPrice() + "";
            ret[0][6] = product.getStorage() + "";
            ret[0][7] = product.getAlarmStorage() + "";

            DefaultTableModel model = new DefaultTableModel(ret, getRowNames());
            storageTable.setModel(model);
            storageTable.repaint();
            storageTable.updateUI();

        } finally {
            response.close();
        }
    }

    private String[] getRowNames() {
        if (rowname == null) {
            rowname = new String[]{"ID", "Product Number", "Categories", "Name", "Purchase Price", "Price", "Stock", "Alarm Store"};
        }
        return rowname;
    }

    private Component createStoragePanel() {
        storagePanel = new JScrollPane();
        storageTable = new JTable(data, getRowNames());
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(JLabel.CENTER);
        storageTable.setDefaultRenderer(Object.class, tcr);
        storageTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        storagePanel.setViewportView(storageTable);
        return storagePanel;
    }

    public ClientContext getClientContext() {
        return clientContext;
    }

    public void setClientContext(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

}
