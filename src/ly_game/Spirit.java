package ly_game;

import java.awt.Graphics;

import ly_game.Bullet.Status;

public class Spirit {
	enum Direction {
		north, south, west, east
	}

	public int x;
	public int y;
	public int v;
	public int id;
	public int camp;
	public Direction direction;
	public Status status = Status.existence;

	public Spirit() {
		v = 1;
		direction = Direction.south;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Direction getDirection() {
		return this.direction;
	}

	public void setXY(int ax, int ay) {
		x = ax;
		y = ay;
	}

	public void move() {
		if (status == Status.existence) {
			switch (direction) {
			case north:
				y = y - v;
				setXY(x, y);
				break;
			case south:
				y = y + v;
				setXY(x, y);
				break;
			case west:
				x = x - v;
				setXY(x, y);
				break;
			case east:
				x = x + v;
				setXY(x, y);
				break;
			default:
				break;
			}
		}
	}
	public void judgecamp(){
		if(id%2==0) {camp = 0;
		}
		else if(id%2==1) {camp = 1;
		}
	}
	
	public void draw(Graphics g) {
	}
}
