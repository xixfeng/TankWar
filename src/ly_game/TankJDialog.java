package ly_game;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.sun.corba.se.impl.io.OptionalDataException;

import ly_game.Bullet.Status;
import ly_game.Spirit.Direction;

@SuppressWarnings("serial")
public class TankJDialog extends MyJDialog {
	MainTank maintank = new MainTank();// 创建了Tank的对象
	private ArrayList<AutoTank> autotanks = new ArrayList<>();
	private Map map = new Map();
	FileWR mapWR = new FileWR();
	private AutoTank autotank;
	private AutoTank autotankClone;
		public TankJDialog(Tankframe frame) {
		super(frame);
		this.setTitle("ly_tank");
		
		map.initMap();
		
		
		addKeyListener(new KeyMonitor());// 监听器会一直执行 直到程序结束
		PaintThread1 myThread = new PaintThread1();
		myThread.setdefault(this, this.maintank);
		MusicThread musicThread = new MusicThread();
		musicThread.start();
		myThread.start();// 这个线程实现paint方法

	}

	public class KeyMonitor extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();// 从键盘读入后转为参数
			maintank.tanklistener(key);
			maintank.cratebullet(key);
		}
	}

	public void caculateAutoTank() {
		Random r = new Random();
		int AutoTankjudge = r.nextInt(50);// 在0到50产生数字
		if (AutoTankjudge == 1) {
			if(autotank == null) {
				autotank = new AutoTank();
				autotank.setXY(r.nextInt(500 + 20), r.nextInt(100 + 50));
				autotanks.add(autotank);
			}
			else {
			  autotankClone = (AutoTank)autotank.clone();
			  autotankClone.setXY(r.nextInt(500 + 20), r.nextInt(100 + 50));
			  autotanks.add(autotankClone);
			}
		}
		if (autotank != null) {
			for (int i = 0; i < autotanks.size(); i++) {
				Random r1 = new Random();
				int direction = r1.nextInt(50);
				switch (direction) {
				case (0):
					autotanks.get(i).direction = Direction.east;
					break;
				case (1):
					autotanks.get(i).direction = Direction.north;
					break;
				case (2):
					autotanks.get(i).direction = Direction.south;
					break;
				case (3):
					autotanks.get(i).direction = Direction.west;
					break;
				}
				autotanks.get(i).createbullet();
				
			}
		}
	}

	public void paint(Graphics g) {// 需要得到一个变量 这个方法存在于父类，我们相当于重载了，所以加载界面时会出问题
		super.paint(g);// 解决重载问题
		int maptemp[][];
		maptemp = map.returnmap();
		map.draw(g);
		if(maintank.canmove(maptemp))
			maintank.move();

		if (autotank != null) {

				if(maintank.canmove(maptemp))
					maintank.move();


			for (int j = 0; j < autotanks.size(); j++) {

				for (int i = 0; i < autotanks.get(j).AutoTankbullets.size(); i++) {

					if (autotanks.get(j).autotankbullet != null) {

						maintank.bulletcash(autotanks.get(j).AutoTankbullets.get(i).getX(),

								autotanks.get(j).AutoTankbullets.get(i).getY());

					}

				}

			}

		} else {

				if(maintank.canmove(maptemp))
					maintank.move();// 改变XY坐标

		}

		maintank.draw(g);// 画出tank

		for (int i = 0; i < maintank.maintankbullets.size(); i++) {

			 maintank.maintankbullets.get(i).move();

			 maintank.maintankbullets.get(i).canmove(maptemp);

			 maintank.maintankbullets.get(i).draw(g);
			 if(maintank.maintankbullets.get(i).status==Status.unexistence)
				 maintank.maintankbullets.remove(i);

		} // 主坦克子弹

		if (autotank != null) {

			for (int j = 0; j < autotanks.size(); j++) {

                  if(autotanks.get(j).canmove(maptemp))
                	
                	autotanks.get(j).move();		
				for (int i = 0; i <  maintank.maintankbullets.size(); i++) {

					if ( maintank.maintankbullets != null) {

						autotanks.get(j).bulletcash( maintank.maintankbullets.get(i).getX(),  maintank.maintankbullets.get(i).getY());

					}

				}

				autotanks.get(j).draw(g);

			}

		}

		if (autotank != null) {

			for (int j = 0; j < autotanks.size(); j++) {

				for (int i = 0; i < autotanks.get(j).AutoTankbullets.size(); i++) {

					autotanks.get(j).AutoTankbullets.get(i).move();

					autotanks.get(j).AutoTankbullets.get(i).canmove(maptemp);

					autotanks.get(j).AutoTankbullets.get(i).draw(g);
					 if(autotanks.get(j).AutoTankbullets.get(i).status==Status.unexistence)
						 autotanks.get(j).AutoTankbullets.remove(i);

				}

			}

		} // autotank的子弹

		/* g.drawImage(bufferImage,0, 0, this); //只需绘制gPlacement一张即可，不会有闪烁现象*/

	}

	public int getautotanknum() {
		int num = 0;
		if (autotank != null) {
			for (int i = 0; i < autotanks.size(); i++) {
				if (autotanks.get(i).status == Status.unexistence)
					num++;
			}
		}
		return num;
	}
}
