package view;

import view.utilpage.BackgroundPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * @ClassName MainFrame
 * @Description MainFrame
 * @Author Xiangyu Liu @Email A00279565@student.ait.ie
 * @Date 2021/4/12 22:19
 * @Version 1.0
 */
public class MainFrame extends JFrame {

    private static final long serialVersionUID = -8808883923263763897L;

    private ClientContext clientContext;

    public MainFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
    }

    public void init() {
        this.setTitle("Store Management System");
        this.setSize(650, 450);
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
        panel.add(createFunctionPanel(), BorderLayout.CENTER);
        return panel;
    }

    private Component createFunctionPanel() {
        Image image = new ImageIcon(MainFrame.class.getResource("/icon/bg.jpg")).getImage();
        JPanel panel = new BackgroundPanel(image);
        Font font16 = new Font("Times New Roman", 1, 16);

        JButton sale = new JButton("Search", new ImageIcon(MainFrame.class.getResource("/icon/sell.png")));
        sale.setHorizontalTextPosition(SwingConstants.CENTER);
        sale.setVerticalTextPosition(SwingConstants.BOTTOM);
        sale.setBorderPainted(false);
        sale.setFocusPainted(false);
        sale.setContentAreaFilled(false);
        sale.setFocusable(true);
        sale.setMargin(new Insets(180, 10, 0, 80));
        sale.setFont(font16);
        sale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientContext.showSellFrame();
            }
        });
        panel.add(sale);

        JButton purchases = new JButton("Purchase", new ImageIcon(MainFrame.class.getResource("/icon/inventory.png")));
        purchases.setHorizontalTextPosition(SwingConstants.CENTER);
        purchases.setVerticalTextPosition(SwingConstants.BOTTOM);
        purchases.setBorderPainted(false);
        purchases.setFocusPainted(false);
        purchases.setContentAreaFilled(false);
        purchases.setFocusable(true);
        purchases.setMargin(new Insets(180, 10, 0, 80));
        purchases.setFont(font16);
        purchases.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientContext.showOrHidePurchaseFrame(true);
            }
        });
        panel.add(purchases);

        JButton stock = new JButton("Stock", new ImageIcon(MainFrame.class.getResource("/icon/storage.png")));
        stock.setHorizontalTextPosition(SwingConstants.CENTER);
        stock.setVerticalTextPosition(SwingConstants.BOTTOM);
        stock.setBorderPainted(false);
        stock.setFocusPainted(false);
        stock.setContentAreaFilled(false);
        stock.setFocusable(true);
        stock.setMargin(new Insets(180, 10, 0, 80));
        stock.setFont(font16);
        stock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientContext.showOrHideStorageFrame(true);
            }
        });
        panel.add(stock);

        return panel;
    }

    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.init();
        mf.setVisible(true);
    }

    public ClientContext getClientContext() {
        return clientContext;
    }

    public void setClientContext(ClientContext clientContext) {
        this.clientContext = clientContext;
    }

}
