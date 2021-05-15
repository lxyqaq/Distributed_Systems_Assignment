package dao;

import pojo.Product;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;


/**
 * @ClassName ProductDao
 * @Description ProductDao method
 * @Author Xiangyu Liu @Email A00279565@student.ait.ie
 * @Date 2021/4/9 17:19
 * @Version 1.0
 */
public enum ProductDao {

    instance;
    Connection conn;
    PreparedStatement pstmt;

    ProductDao() {

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
     * @return java.util.Vector<entity.Product>
     * @throws
     * @description findAllProduct method
     * @author Xiangyu Liu @email A00279565@student.ait.ie
     * @date 2021/2/18 13:51
     */
    public List<Product> findAllProduct() {
        List<Product> ret = new Vector<>();
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("select * from product");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setCategory(rs.getString("category"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setProductId(rs.getInt("product_id"));
                p.setProductNo(rs.getString("product_no"));
                p.setPurPrice(rs.getDouble("pur_price"));
                p.setStorage(rs.getInt("storage"));
                p.setAlarmStorage(rs.getInt("alarm_storage"));
                ret.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public List<Product> findProductByCategory(String name) {
        List<Product> ret = new Vector<>();
        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement("select * from product where category=?");
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setCategory(rs.getString("category"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setProductId(rs.getInt("product_id"));
                p.setProductNo(rs.getString("product_no"));
                p.setPurPrice(rs.getDouble("pur_price"));
                p.setStorage(rs.getInt("storage"));
                p.setAlarmStorage(rs.getInt("alarm_storage"));
                ret.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * @param pid
     * @return entity.Product
     * @throws
     * @description saveProduct method by id
     * @author Xiangyu Liu @email A00279565@student.ait.ie
     * @date 2021/2/18 14:02
     */
    public Product findProduct(int pid) {
        ResultSet rs = null;
        Product p = null;
        try {
            pstmt = conn.prepareStatement("SELECT * FROM product WHERE product_id = ?");
            pstmt.setInt(1, pid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                p = new Product();
                p.setCategory(rs.getString("category"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setProductId(rs.getInt("product_id"));
                p.setProductNo(rs.getString("product_no"));
                p.setPurPrice(rs.getDouble("pur_price"));
                p.setStorage(rs.getInt("storage"));
                p.setAlarmStorage(rs.getInt("alarm_storage"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public Product findProduct(String name) {
        ResultSet rs = null;
        Product p = null;
        try {
            pstmt = conn.prepareStatement("SELECT * FROM product WHERE name = ?");
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                p = new Product();
                p.setCategory(rs.getString("category"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setProductId(rs.getInt("product_id"));
                p.setProductNo(rs.getString("product_no"));
                p.setPurPrice(rs.getDouble("pur_price"));
                p.setStorage(rs.getInt("storage"));
                p.setAlarmStorage(rs.getInt("alarm_storage"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    /**
     * @param product
     * @return void
     * @throws
     * @description saveProduct method
     * @author Xiangyu Liu @email A00279565@student.ait.ie
     * @date 2021/2/18 14:02
     */
    public void saveProduct(Product product) {
        String sql = "insert into product(product_no, name ,category, price, pur_price, storage, alarm_storage) values(?,?,?,?,?,?,?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, product.getProductNo());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getCategory());
            pstmt.setDouble(4, product.getPrice());
            pstmt.setDouble(5, product.getPurPrice());
            pstmt.setInt(6, product.getStorage());
            pstmt.setInt(7, product.getAlarmStorage());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param pid
     * @return void
     * @throws
     * @description deleteProduct method by id
     * @author Xiangyu Liu @email A00279565@student.ait.ie
     * @date 2021/2/18 14:04
     */
    public void deleteProduct(int pid) {
        try {
            pstmt = conn.prepareStatement("delete from product where product_id=?");
            pstmt.setInt(1, pid);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param product
     * @return void
     * @throws
     * @description updateProduct method
     * @author Xiangyu Liu @email A00279565@student.ait.ie
     * @date 2021/2/18 14:09
     */
    public void updateProduct(Product product) {
        String sql = "update product set product_no=?, name=? ,category=?, price=?, pur_price=?, storage=?, alarm_storage=? where product_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, product.getProductNo());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getCategory());
            pstmt.setDouble(4, product.getPrice());
            pstmt.setDouble(5, product.getPurPrice());
            pstmt.setInt(6, product.getStorage());
            pstmt.setInt(7, product.getAlarmStorage());
            pstmt.setInt(8, product.getProductId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getProducts() {
        ResultSet rs = null;
        String sql = "select * from product";
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rs;
    }

}
