package substance;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

import frame.TankJDialog;
import music.ShootMusicThread;

public class AutoTank extends Tank implements Cloneable{
	public ArrayList<Bullet> AutoTankbullets = new ArrayList<>();
	public Bullet autotankbullet = null;
	int camp = 1;
	private int imgD = 25;
	private int clock = 0;
	ShootMusicThread bombMusicThread = null;
	ImageIcon maintankup = new ImageIcon("img//autotankup.png");// 添加背景

	ImageIcon maintankwest = new ImageIcon("img//autotankwest.png");// 添加背景

	ImageIcon maintanksouth = new ImageIcon("img//autotanksouth.png");// 添加背景

	ImageIcon maintankeast = new ImageIcon("img//autotankeast.png");// 添加背景
	
	ImageIcon bomb1 = new ImageIcon("img//bomb1.png");
	ImageIcon bomb2 = new ImageIcon("img//bomb2.png");
	ImageIcon bomb3 = new ImageIcon("img//bomb3.png");
	ImageIcon bomb4 = new ImageIcon("img//bomb4.png");
	ImageIcon[] bomb = {bomb1,bomb2,bomb3,bomb4};
	public AutoTank() {
		super();
	}
	public void draw(JDialog jd,Graphics g) {
		if(clock>=13) {
			status = Status.unexistence;
		}
		if(status == Status.existence) {
		switch(direction) {
		case north:
			g.drawImage(maintankup.getImage(), this.x-imgD,this.y-imgD,imgD,imgD,jd);
			break;
		case west:
			g.drawImage(maintankwest.getImage(), this.x-imgD,this.y-imgD,imgD,imgD,jd);
			break;
		case south:
			g.drawImage(maintanksouth.getImage(), this.x-imgD,this.y-imgD,imgD,imgD,jd);
			break;
		case east:
			g.drawImage(maintankeast.getImage(), this.x-imgD,this.y-imgD,imgD,imgD,jd);
			break;
		default:
			break;	
		}
		}
		if(status == Status.bomb) {
			if(clock%4==0){
			g.drawImage(bomb[clock/4].getImage(), this.x-imgD, this.y-imgD*2,25,25,jd);}
			clock++;
		}
	}
	public void createbullet() {
		Random r = new Random();
		int r1 = r.nextInt(10);
		if ((r1 == 1) && (status == Status.existence)) {
			autotankbullet = new Bullet();
			autotankbullet.setXY(x-imgD/2, y-imgD/2);
			autotankbullet.direction = direction;
			AutoTankbullets.add(autotankbullet);
			bombMusicThread = new ShootMusicThread();
			bombMusicThread.start();
		}
	}
	public void bulletcash(Bullet bullet,TankJDialog tj) {
		if ((Math.sqrt((bullet.getX()-imgD/10-x+imgD/2)*(bullet.getX()-imgD/10-x+imgD/2)+
				(bullet.getY()-imgD/10-y+imgD/2)*(bullet.getY()-imgD/10-y+imgD/2))<=(6*imgD/10))&&(status == Status.existence))
		{
			status = Status.bomb;
			bullet.status = Status.bomb;
			tj.kill++;
		}
	}
	@SuppressWarnings("unchecked")
	public AutoTank clone() {
		AutoTank autotank = null;
		try {
			autotank = (AutoTank) super.clone();//强制类型转换
			autotank.AutoTankbullets = (ArrayList<Bullet>) AutoTankbullets.clone();
			autotank.status = Status.existence;
			return autotank;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
