package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.ProductDao;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import pojo.ParseProducts;
import pojo.Product;

/**
 * @ClassName StorageFrame
 * @Description StorageFrame
 * @Author Xiangyu Liu @Email A00279565@student.ait.ie
 * @Date 2021/4/10 12:19
 * @Version 1.0
 */
public class StorageFrame extends JFrame {

    private static final long serialVersionUID = -8808883923263763897L;
    private ClientContext clientContext;
    private JScrollPane storagePanel;
    private JTable storageTable;
    private JComboBox catCombox;
    private String[] rowname;
    private String[][] data;
    private int currCat = 1;

    public StorageFrame() {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        init();
    }

    public void init() {
        this.setTitle("Inventory inquiry");
        this.setSize(760, 400);
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
        panel.add(createStoragePanel(), BorderLayout.CENTER);
        panel.add(createButtonPanel(), BorderLayout.SOUTH);
        return panel;
    }

    private Component createButtonPanel() {
        JPanel panel = new JPanel();
        catCombox = new JComboBox();
        panel.add(catCombox);
        catCombox.addItem("Drinks");
        catCombox.addItem("Food");
        catCombox.addItem("Wine");
        catCombox.addItem("Cigarette");
        catCombox.addItem("Snacks");
        catCombox.addItem("Household");
        catCombox.addItem("All");
        catCombox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    getCategory();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        JButton modifyBtn = new JButton("PUT");
        modifyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (storageTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(StorageFrame.this, "Please select the product to be operated!", "Commodity management", JOptionPane.WARNING_MESSAGE);
                } else {
                    int pid = Integer.parseInt(data[storageTable.getSelectedRow()][0]);
                    Product currProduct = ProductDao.instance.findProduct(pid);
                    clientContext.setCurrProduct(currProduct);
                    clientContext.showOrHideModifyProductFrame(true);
                }
            }
        });
        panel.add(modifyBtn);

        JButton delBtn = new JButton("DELETE");
        delBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (storageTable.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(StorageFrame.this, "Please select the product to be operated!", "Product management", JOptionPane.WARNING_MESSAGE);
                } else {
                    int val = JOptionPane.showConfirmDialog(StorageFrame.this, "Do you want to delete this product?");
                    if (val == JOptionPane.YES_OPTION) {
                        int pid = Integer.parseInt(data[storageTable.getSelectedRow()][0]);
                        JOptionPane.showMessageDialog(StorageFrame.this, "The product has been deleted!", "Product management", JOptionPane.INFORMATION_MESSAGE);
                        try {
                            clientContext.deleteProduct(pid);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        });
        panel.add(delBtn);

        JButton okBtn = new JButton("Export");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientContext.export();
            }
        });
        panel.add(okBtn);
        return panel;

    }

    public void getCategory() throws Exception {
        String cateName = catCombox.getSelectedItem().toString().toLowerCase();
        if (cateName.equals("all")) {
            cateName = "";
        }
        System.out.println(cateName);

        //Response models
        CloseableHttpResponse response = null;
        try {

            //Create URI and set parameters
            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost")
                    .setPort(8080)
                    .setPath("/DSAssignment/api/products/" + cateName).build();

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

            List<Product> productList = new ParseProducts().doParseProducts(text);

            //Populate object data into JTable
            String[][] ret = new String[productList.size()][8];
            for (int idx = 0; idx < productList.size(); idx++) {
                ret[idx][0] = productList.get(idx).getProductId() + "";
                ret[idx][1] = productList.get(idx).getProductNo();
                ret[idx][2] = productList.get(idx).getCategory();
                ret[idx][3] = productList.get(idx).getName();
                ret[idx][4] = productList.get(idx).getPurPrice() + "";
                ret[idx][5] = productList.get(idx).getPrice() + "";
                ret[idx][6] = productList.get(idx).getStorage() + "";
                ret[idx][7] = productList.get(idx).getAlarmStorage() + "";
            }
            data = ret;
            DefaultTableModel model = new DefaultTableModel(data, new String[]{"ID", "Product Number", "Categories", "Name", "Purchase price", "Price", "Stock", "Last purchase"});
            storageTable.setModel(model);
            storageTable.repaint();
            storageTable.updateUI();
        } finally {
            response.close();
        }
    }

    private Component createStoragePanel() {
        storagePanel = new JScrollPane();
        initTableData();
        storagePanel.setViewportView(storageTable);
        return storagePanel;
    }

    public void initTableData() {
        String[] rowNames = getRowNames();
        String[][] data = getData();
        storageTable = new JTable(data, rowNames);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(JLabel.CENTER);
        storageTable.setDefaultRenderer(Object.class, tcr);
        storageTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void refreshTableData() {
        DefaultTableModel model = new DefaultTableModel(getData(), getRowNames());
        storageTable.setModel(model);
        storageTable.repaint();
        storageTable.updateUI();
    }

    private String[] getRowNames() {
        if (rowname == null) {
            rowname = new String[]{"ID", "Product Number", "Categories", "Name", "Purchase price", "Price", "Stock", "Alarm Storage"};
        }
        return rowname;
    }

    private String[][] getData() {
        Vector<Product> list;
        list = (Vector<Product>) ProductDao.instance.findProductByCategory("Drinks");
        String[][] ret = new String[list.size()][8];
        for (int idx = 0; idx < list.size(); idx++) {
            ret[idx][0] = list.get(idx).getProductId() + "";
            ret[idx][1] = list.get(idx).getProductNo();
            ret[idx][2] = list.get(idx).getCategory() + "";
            ret[idx][3] = list.get(idx).getName();
            ret[idx][4] = list.get(idx).getPurPrice() + "";
            ret[idx][5] = list.get(idx).getPrice() + "";
            ret[idx][6] = list.get(idx).getStorage() + "";
            ret[idx][7] = list.get(idx).getAlarmStorage() + "";
        }
        data = ret;
        return data;
    }

    public static void main(String[] args) {
        StorageFrame mf = new StorageFrame();
        mf.init();
        mf.setVisible(true);
    }

    public ClientContext getClientContext() {
        return clientContext;
    }

    public void setClientContext(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

    public JScrollPane getStoragePanel() {
        return storagePanel;
    }

    public void setStoragePanel(JScrollPane storagePanel) {
        this.storagePanel = storagePanel;
    }

    public JTable getStorageTable() {
        return storageTable;
    }

    public void setStorageTable(JTable storageTable) {
        this.storageTable = storageTable;
    }

    public JTable getScoreTable() {
        return storageTable;
    }

    public void setScoreTable(JTable scoreTable) {
        this.storageTable = scoreTable;
    }

    public JComboBox getCatCombox() {
        return catCombox;
    }

    public void setCatCombox(JComboBox catCombox) {
        this.catCombox = catCombox;
    }

    public int getCurrCat() {
        return currCat;
    }

    public void setCurrCat(int currCat) {
        this.currCat = currCat;
    }

}
