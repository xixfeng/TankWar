package ly_game;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

public class GrassBlock extends Block {
	
	public void setPosition(int ax, int ay) {
		x = ax;
		y = ay;
	}
	public void draw(Graphics g,JDialog jd) {
		
		
		
		ImageIcon img = new ImageIcon("img//forest2.png");
		g.drawImage(img.getImage(), x, y, width, height, jd);

		
	}


}
