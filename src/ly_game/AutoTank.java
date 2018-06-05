package ly_game;

import java.util.ArrayList;
import java.util.Random;

import ly_game.Bullet.Status;

public class AutoTank extends Tank implements Cloneable{
	ArrayList<Bullet> AutoTankbullets = new ArrayList<>();
	Bullet autotankbullet = null;
	int camp = 1;

	public AutoTank() {
		super();
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
