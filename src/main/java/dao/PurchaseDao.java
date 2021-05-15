package dao;

import pojo.Purchase;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Vector;


/**
 * @ClassName PurchaseDao
 * @Description PurchaseDao method
 * @Author Xiangyu Liu @Email A00279565@student.ait.ie
 * @Date 2021/4/10 17:19
 * @Version 1.0
 */
public enum PurchaseDao {

    instance;
    Connection conn;
    PreparedStatement pstmt;

    private PurchaseDao() {

        try {
            Class.forName("org.hsqldb.jdbcDriver");
            conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/oneDB", "SA", "Passw0rd");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * @return java.util.Vector<entity.StockHistory>
     * @throws
     * @description findAllHistory methood
     * @author Xiangyu Liu @email A00279565@student.ait.ie
     * @date 2021/2/18 14:19
     */
    public List<Purchase> getAllPurchase() {
        List<Purchase> ret = new Vector<>();
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("select * from purchase");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Purchase p = new Purchase();
                p.setPurchaseId(rs.getInt("purchase_id"));
                p.setPurchaseCategory(rs.getString("purchase_category"));
                p.setPurchaseName(rs.getString("purchase_name"));
                p.setPurchaseQuantity(rs.getInt("purchase_quantity"));
                ret.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * @param sh
     * @return void
     * @throws
     * @description saveStockHistory method
     * @author Xiangyu Liu @email A00279565@student.ait.ie
     * @date 2021/2/18 14:20
     */
    public void addPurchase(Purchase purchase) {
        String sql = "insert into purchase(purchase_category, purchase_name, purchase_quantity) values(?,?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, purchase.getPurchaseCategory());
            pstmt.setString(2, purchase.getPurchaseName());
            pstmt.setInt(3, purchase.getPurchaseQuantity());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
