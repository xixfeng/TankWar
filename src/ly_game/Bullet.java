package ly_game;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Spirit {
	enum Status {
		bomb, existence, unexistence
	}

	private int redius = 5;
	public Status status = Status.existence;

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
			if ((y / Block.height ) >= 0) {//判断是否在界面里面
				if (map[y / Block.height ][x / Block.width+2] == 2) {//下面是砖 不能移动
					status = Status.unexistence;
				} 
			}
			else status = Status.unexistence;
		}

		else if (getDirection() == Direction.south) {
			if ((y / Block.height) <= 23) {
				if (map[y / Block.height ][x / Block.width] == 2) {
					status = Status.unexistence;
				}
				
			}
			else status = Status.unexistence;
		} else if (getDirection() == Direction.west) {
			if ((x / Block.width ) >= 0) {
				if (map[y / Block.height][x / Block.width] == 2) {
					status = Status.unexistence;
				} 
				}
			else status = Status.unexistence;
			}
		

		else if (getDirection() == Direction.east) {
			if ((x / Block.width ) <= 39) {
				if (map[y / Block.height][x / Block.width] == 2) {
					status = Status.unexistence;
				} 

			}
			else status = Status.unexistence;

		}
	}

	public void draw(Graphics g) {
		if (redius == 10) {
			status = Status.unexistence;
		}
		if (status == Status.bomb) {
			redius++;
			g.setColor(Color.white);
			g.drawOval(x, y, redius, redius);
		} else if (status == Status.existence) {
			g.setColor(Color.white);
			g.drawOval(x, y, redius, redius);
		}
	}
}