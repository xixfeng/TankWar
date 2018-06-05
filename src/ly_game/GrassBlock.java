package ly_game;

import java.awt.Color;
import java.awt.Graphics;

public class GrassBlock extends Block {
	
	public void setPosition(int ax, int ay) {
		x = ax;
		y = ay;
	}
	public void draw(Graphics g) {
		
	
		g.setColor(Color.green);
		g.drawLine(x,y + height/2 ,x + width/4 ,y + height);
		g.drawLine(x + width/4 ,y + height ,x + width/2 ,y);
		g.drawLine(x + width/2 ,y , x + 3*width/4 ,y + height);
		g.drawLine(x + 3*width/4 ,y + height,x + width ,y + height);
		   
		
	}

}
