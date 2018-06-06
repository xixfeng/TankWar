package ly_game;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

public class RiverBlock extends Block{
	public void draw(Graphics g,JDialog jd) {
		ImageIcon img = new ImageIcon("img//river.png");
		g.drawImage(img.getImage(), x, y, width, height, jd);
	}
}
