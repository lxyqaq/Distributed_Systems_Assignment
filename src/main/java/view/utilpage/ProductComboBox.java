package view.utilpage;

import pojo.ProductOption;

import java.util.Vector;

import javax.swing.JComboBox;


/**
 * @ClassName ProductComboBox
 * @Description ProductComboBox
 * @Author Xiangyu Liu @Email A00279565@student.ait.ie
 * @Date 2021/2/10 22:19
 * @Version 1.0
 */
public class ProductComboBox extends JComboBox {

    private static final long serialVersionUID = -2461723055752181348L;

    public ProductComboBox() {

    }

    public ProductComboBox(Vector<ProductOption> vec) {
        super(vec);
    }

    public String getText() {
        ProductOption item = (ProductOption) this.getSelectedItem();
        return item.getText();
    }

    public Integer getValue() {
        ProductOption item = (ProductOption) this.getSelectedItem();
        return item.getValue();
    }

}
