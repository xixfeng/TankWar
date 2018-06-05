package ly_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import ly_game.Bullet.Status;

public class MainTank extends Tank {
	int length = WIDTH + 2;
	public ArrayList<Bullet> maintankbullets = new ArrayList<>();// 自动扩容的对象数组
	NetTankJDialog tj = null;
	int camp = 0;

	public MainTank(int x, int y, Direction direction, int id, Status status, int v) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.id = id;
		this.status = status;
		this.v = v;
	}

	public MainTank() {
		super();
	}

	public MainTank(NetTankJDialog tj) {
		this.tj = tj;
	}

	Bullet maintankbullet = null;

	public void draw(Graphics g) {
		super.draw(g);
		if (status == Status.existence) {
			g.setColor(Color.RED);
			g.drawString("id:" + id, x, y - 20);
		}
		// 画血条
		switch (direction) {

		case north:
			g.fillRect(x - WIDTH / 2, y - HEIGHT / 2 - HEIGHT / 5, length, WIDTH / 5);
			break;

		case west:
			g.fillRect(x - WIDTH / 2 - WIDTH / 5, y - HEIGHT / 2, WIDTH / 5, length);
			break;

		case south:
			g.fillRect(x - WIDTH / 2, y + HEIGHT / 2, length, HEIGHT / 5);
			break;

		case east:
			g.fillRect(x + WIDTH / 2, y - HEIGHT / 2, HEIGHT / 5, length);
			break;

		}

	}

	public void bulletcash(int BulletX, int BulletY, int camp) {
		if ((Math.sqrt((BulletX - x) * (BulletX - x) + (BulletY - y) * (BulletY - y)) <= 20) && (this.camp != camp)) {
			length -= 1;
			if (length <= 0)
				status = Status.bomb;
		}
	}

	public void bulletcash(int BulletX, int BulletY) {
		if (Math.sqrt((BulletX - x) * (BulletX - x) + (BulletY - y) * (BulletY - y)) <= 20) {
			length -= 1;
			if (length <= 0)
				status = Status.bomb;
		}
	}

	public void tanklistener(int key) {
		switch (key)// 架构设计
		{
		case KeyEvent.VK_UP:
			this.direction = Direction.north;
			break;
		case KeyEvent.VK_DOWN:
			this.direction = Direction.south;
			break;
		case KeyEvent.VK_LEFT:
			this.direction = Direction.west;
			break;
		case KeyEvent.VK_RIGHT:
			this.direction = Direction.east;
			break;
		}
	}

	public void cratebulletNet(int key) {
		if ((key == KeyEvent.VK_SPACE) && (status == Status.existence)) {
			maintankbullet = new Bullet();
			maintankbullet.setXY(getX(), getY());
			maintankbullet.direction = getDirection();
			maintankbullet.camp = id % 2;
			maintankbullets.add(maintankbullet);
			BulletMsg buMsg = new BulletMsg(id, x, y, direction);
			tj.nc.send(buMsg);
		}
	}

	public void cratebullet(int key) {
		if ((key == KeyEvent.VK_SPACE) && (status == Status.existence)) {
			maintankbullet = new Bullet();
			maintankbullet.setXY(getX(), getY());
			maintankbullet.direction = getDirection();
			maintankbullets.add(maintankbullet);
		}
	}
}
