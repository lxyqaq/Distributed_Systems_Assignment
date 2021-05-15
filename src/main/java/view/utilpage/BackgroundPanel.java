package view.utilpage;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * @ClassName BackgroundPanel
 * @Description BackgroundPanel
 * @Author Xiangyu Liu @Email A00279565@student.ait.ie
 * @Date 2021/2/10 22:19
 * @Version 1.0
 */
public class BackgroundPanel extends JPanel {

    private static final long serialVersionUID = -6352788025440244338L;

    private Image image = null;

    public BackgroundPanel(Image image) {
        this.image = image;
    }

    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }

}
