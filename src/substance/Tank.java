package substance;//封装

/*******************1.形状2.移动*****************/

import java.awt.Color;
import java.awt.Graphics;

import map.Block;

public class Tank extends Spirit {
	public final int WIDTH = 20;
	public final int HEIGHT = 20;
	private int bombredius = 10;
	private int animationState;

	public Tank() {
		super();
		x = 500;
		y = 300;
		v = 3;
	}

	public void draw(Graphics g) {
		if (status == Status.existence) {
			judgecamp();
			if (camp == 0) {
				g.setColor(Color.RED);
			} else if (camp == 1) {
				g.setColor(Color.WHITE);
			}
			switch (direction) {
			case north:
				this.drawMain(g);
				g.drawRect(x + 9, y - 18, 2, 28);// 炮
				this.drawTrackUD(g);
				animationState--;
				if (animationState <= 0)
					animationState = 4;
				break;
			case south:
				this.drawMain(g);
				g.drawRect(x + 9, y + 10, 2, 28);// 炮
				this.drawTrackUD(g);
				animationState++;
				if (animationState >= 4)
					animationState = 0;
				break;
			case west:
				this.drawMain(g);
				g.drawRect(x - 18, y + 9, 28, 2);// 炮
				this.drawTrackWE(g);
				animationState--;
				if (animationState <= 0)
					animationState = 4;
				break;
			case east:
				this.drawMain(g);
				g.drawRect(x + 10, y + 9, 28, 2);// 炮
				this.drawTrackWE(g);
				animationState++;
				if (animationState >= 4)
					animationState = 0;
				break;
			default:
				break;
			}
		} else if (status == Status.bomb) {
			bombredius++;
			g.setColor(Color.white);
			g.drawOval(x, y, bombredius, bombredius);
			if (bombredius >= 20)
				status = Status.unexistence;
		}
	}

	public boolean canmove(int map[][]) {
		Boolean moveState = false;

		if (getDirection() == Direction.north) {
			if ((y / Block.height - 1) >= 0) {//判断是否在界面里面
				if (map[y / Block.height - 1][x / Block.width] == 2) {//下面是砖 不能移动
					moveState = false;
				} else {
					moveState = true;
				}
			}

		}

		else if (getDirection() == Direction.south) {
			if ((y / Block.height + 1) <= 23) {
				if (map[y / Block.height + 1][x / Block.width] == 2) {
					moveState= false;
				} else {// 上方没物体，可以继续向上移动
					moveState = true;
				}

			}

		} else if (getDirection() == Direction.west) {
			if ((x / Block.width - 1) >= 0) {
				if (map[y / Block.height][x / Block.width - 1] == 2) {
					moveState = false;
				} else {
					moveState = true;
				}
			}
		}

		else if (getDirection() == Direction.east) {
			if ((x / Block.width + 1) <= 39) {
				if (map[y / Block.height][x / Block.width + 1] == 2) {
					moveState = false;
				} else {
					moveState= true;
				}

			}

		}
		return moveState;
	}


	// UP and DOWN
	private void drawTrackUD(Graphics g) {
		g.drawRect(x - 4, y - 2, 4, HEIGHT + 4);// 左履带
		g.drawRect(x + WIDTH, y - 2, 4, HEIGHT + 4);// 右履带
		for (int i = y; i <= y + 16; i = i + 4) {
			g.drawLine(x - 4, i + animationState, x, i + animationState);// 履带痕
			g.drawLine(x + WIDTH, i + animationState, x + WIDTH + 4, i + animationState);// 履带痕
		}
		/*
		 * g.fillRect(x-WIDTH/2+4, y-HEIGHT/2+25, 2, 10);//L g.fillRect(x-WIDTH/2+4,
		 * y-HEIGHT/2+35, 10, 2);//L
		 */
	}

	// WEST and EAST
	private void drawTrackWE(Graphics g) {
		g.drawRect(x - 2, y - 4, HEIGHT + 4, 4);// 左履带
		g.drawRect(x - 2, y + WIDTH, HEIGHT + 4, 4);// 右履带
		for (int i = x; i < x + 16; i = i + 4) {
			g.drawLine(i + animationState, y - 4, i + animationState, y);// 履带痕
			g.drawLine(i + animationState, y + WIDTH + 4, i + animationState, y + WIDTH);// 履带痕
		}
		/*
		 * g.fillRect(x-WIDTH/2+4, y-HEIGHT/2+25, 2, 10);//L g.fillRect(x-WIDTH/2+4,
		 * y-HEIGHT/2+35, 10, 2);//L
		 */
	}

	private void drawMain(Graphics g) {
		g.drawOval(x + 2, y + 2, WIDTH - 4, HEIGHT);// 炮塔
		g.drawRect(x, y, WIDTH, HEIGHT);// 主体

	}
}
