package ly_game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import ly_game.Bullet.Status;

public class AutoTank extends Tank implements Cloneable{
	ArrayList<Bullet> AutoTankbullets = new ArrayList<>();
	Bullet autotankbullet = null;
	int camp = 1;
	private int imgD = 50;
	ImageIcon maintankup = new ImageIcon("img//autotankup.png");// 添加背景

	ImageIcon maintankwest = new ImageIcon("img//autotankwest.png");// 添加背景

	ImageIcon maintanksouth = new ImageIcon("img//autotanksouth.png");// 添加背景

	ImageIcon maintankeast = new ImageIcon("img//autotankeast.png");// 添加背景

	public AutoTank() {
		super();
	}
	public void draw(JDialog jd,Graphics g) {
		if(status == Status.existence) {
		switch(direction) {
		case north:
			g.drawImage(maintankup.getImage(), this.x,this.y,jd);
			break;
		case west:
			g.drawImage(maintankwest.getImage(), this.x,this.y,jd);
			break;
		case south:
			g.drawImage(maintanksouth.getImage(), this.x,this.y,jd);
			break;
		case east:
			g.drawImage(maintankeast.getImage(), this.x,this.y,jd);
			break;
		default:
			break;	
		}
		}
	}
	public void createbullet() {
		Random r = new Random();
		int r1 = r.nextInt(30);
		if ((r1 == 1) && (status == Status.existence)) {
			autotankbullet = new Bullet();
			autotankbullet.setXY(x, y);
			autotankbullet.direction = direction;
			AutoTankbullets.add(autotankbullet);
		}
	}
	public void bulletcash(int BulletX, int BulletY) {
		if (Math.sqrt((BulletX-x)*(BulletX-x)+(BulletY-y)*(BulletY-y))<=20)
			status = Status.bomb;
	}
	@SuppressWarnings("unchecked")
	protected AutoTank clone() {
		AutoTank autotank = null;
		try {
			autotank = (AutoTank) super.clone();//强制类型转换
			autotank.AutoTankbullets = (ArrayList<Bullet>) AutoTankbullets.clone();
			return autotank;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
