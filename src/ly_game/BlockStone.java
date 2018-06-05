package ly_game;

import java.awt.Color;
import java.awt.Graphics;

public class BlockStone extends Block{
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		g.drawRect(x, y, width, height);
		g.drawLine(x, y, x+width, y+height);
	}
}
