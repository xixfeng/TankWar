package ly_game;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

public class BlockStone extends Block{
	public void draw(Graphics g,JDialog jd) {
		ImageIcon img = new ImageIcon("img//steelwall2.png");
		g.drawImage(img.getImage(), x, y, width, height, jd);
	}
}
