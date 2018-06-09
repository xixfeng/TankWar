package substance;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

import map.Block;
import music.BombMusicThread;

public class Bullet extends Spirit {
	BombMusicThread bombMusicThread = null;
	private int clock = 0;
	ImageIcon bulletup = new ImageIcon("img//bulletup.png");
	ImageIcon bulletdown = new ImageIcon("img//bulletdown.png");
	ImageIcon bulletleft = new ImageIcon("img//bulletleft.png");
	ImageIcon bulletright = new ImageIcon("img//bulletright.png");

	ImageIcon bomb1 = new ImageIcon("img//1.png");
	ImageIcon bomb2 = new ImageIcon("img//2.png");
	ImageIcon bomb3 = new ImageIcon("img//3.png");
	ImageIcon[] bomb = { bomb1, bomb2, bomb3 };

	public Bullet() {
		v = 10;
	}

	public Bullet(int ip, int x, int y, Direction direction) {
		this.id = ip;
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public void canmove(int map[][]) {
		if (getDirection() == Direction.north) {
			if ((y / Block.height) >= 0) {// 判断是否在界面里面
				if (map[y / Block.height][x / Block.width + 2] == 2) {// 下面是砖 不能移动
					status = Status.bomb;
				}
			} else
				status = Status.bomb;
		}

		else if (getDirection() == Direction.south) {
			if ((y / Block.height) <= 23) {
				if (map[y / Block.height][x / Block.width] == 2) {
					status = Status.bomb;
				}

			} else
				status = Status.bomb;
		} else if (getDirection() == Direction.west) {
			if ((x / Block.width) >= 0) {
				if (map[y / Block.height][x / Block.width] == 2) {
					status = Status.bomb;
				}
			} else
				status = Status.bomb;
		}

		else if (getDirection() == Direction.east) {
			if ((x / Block.width) <= 39) {
				if (map[y / Block.height][x / Block.width] == 2) {
					status = Status.bomb;
				}

			} else
				status = Status.bomb;

		}
	}

	public void draw(Graphics g, JDialog jd) {
		if (clock >= 10) {
			status = Status.unexistence;
		}
		else if (status == Status.bomb) {
			if (clock == 0) {
				bombMusicThread = new BombMusicThread();
				bombMusicThread.start();
			}
			if (clock % 3 == 0) {
				g.drawImage(bomb[clock / 4].getImage(), this.x, this.y, 15,15,jd);
			}
			clock++;
		} else if (status == Status.existence) {
			switch (direction) {
			case north:
				g.drawImage(bulletup.getImage(), this.x, this.y, Block.width / 5, Block.height / 5, jd);
				break;
			case west:
				g.drawImage(bulletleft.getImage(), this.x, this.y, Block.width / 5, Block.height / 5, jd);
				break;
			case south:
				g.drawImage(bulletdown.getImage(), this.x, this.y, Block.width / 5, Block.height / 5, jd);
				break;
			case east:
				g.drawImage(bulletright.getImage(), this.x, this.y, Block.width / 5, Block.height / 5, jd);
				break;
			default:
				break;
			}
		}
	}
}