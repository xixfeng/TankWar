package map;

import java.awt.Graphics;

import javax.swing.JDialog;

public abstract class Block {
	public int x,y;
	public static final int width = 25;
	public static final int height = 25;
	public void draw(Graphics g,JDialog jd) {
		
	}
	public void setXY(int x,int y) {
		this.x = x;
		this.y = y;
	}
}
