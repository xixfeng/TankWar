package ly_game;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import ly_game.Bullet.Status;
import ly_game.Spirit.Direction;

public class NetTankJDialog extends MyJDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map map = new Map();
	MainTank maintank = new MainTank(this);// 创建了Tank的对象
	ArrayList<MainTank> enemytanks  = new ArrayList<MainTank>();
	MainTank enemytank = null;
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	Bullet bullet = null;
	NetClient nc = new NetClient(this);	
	public NetTankJDialog(Tankframe frame) {
		super(frame);
		this.setTitle("ly_tank");
		map.initMap();
		maintank.v = 5;

		addKeyListener(new KeyMonitor());// 监听器会一直执行 直到程序结束
		PaintThread2 myThread = new PaintThread2();
		myThread.setdefault(this, maintank);
		myThread.start();// 这个线程实现paint方法
		
		nc.connect("127.0.0.1", TankServer.TCP_PORT);//连接服务器  这个ip地址是服务器的ip地址
	}
	public class KeyMonitor extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();// 从键盘读入后转为参数
			Direction dir  = maintank.direction;
			maintank.tanklistener(key);
			if(dir != maintank.direction) {
				TankMoveMsg mmsg = new TankMoveMsg(maintank.id,maintank.x,maintank.y,maintank.direction);
				nc.send(mmsg);
			}
			maintank.cratebulletNet(key);
			}
		}
	public void paint(Graphics g) {// 需要得到一个变量 这个方法存在于父类，我们相当于重载了，所以加载界面时会出问题
		super.paint(g);// 解决重载问题
		int maptemp[][];
		maptemp = map.returnmap();
		map.draw(g,this);
		if(maintank.canmove(maptemp))
		maintank.move();// 改变XY坐标
		
		if(bullet != null) {
			for(int i=0;i<bullets.size();i++) {
			maintank.bulletcash(bullets.get(i).getX(), bullets.get(i).getY(),bullets.get(i).camp);
			if(bullets.get(i).status == Status.unexistence) bullets.remove(i);
			}
		}
		maintank.draw(g);// 画出tank
		
		if(enemytank != null) {
			for(int i=0;i<enemytanks.size();i++) {
				if(enemytanks.get(i).canmove(maptemp))
					enemytanks.get(i).move();
				
				if ( maintank.maintankbullets != null) {
				for (int j = 0; j <  maintank.maintankbullets.size(); j++) {
						enemytanks.get(i).bulletcash( maintank.maintankbullets.get(j).getX(), maintank.maintankbullets.get(j).getY(),maintank.maintankbullets.get(j).camp);
				}
			}
				if(enemytanks.get(i).status == Status.unexistence)enemytanks.remove(i);
				enemytanks.get(i).draw(g);
		}
		}
		for (int i = 0; i < maintank.maintankbullets.size(); i++) {
			
			maintank.maintankbullets.get(i).canmove(maptemp);
			maintank.maintankbullets.get(i).move();
			maintank.maintankbullets.get(i).draw(g);
		} // 主坦克子弹
		
		for(int i=0;i<bullets.size();i++) {
			bullets.get(i).move();
			bullets.get(i).canmove(maptemp);
			bullets.get(i).draw(g);
			if(bullets.get(i).status == Status.unexistence)bullets.remove(i);
		}
	}

}

