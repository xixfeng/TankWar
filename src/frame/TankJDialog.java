package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import map.Map;
import music.MusicThread;
import substance.AutoTank;
import substance.MainTank;
import substance.Spirit.Direction;
import substance.Spirit.Status;

@SuppressWarnings("serial")
public class TankJDialog extends MyJDialog {
	MainTank maintank = new MainTank();// 创建了Tank的对象
	private ArrayList<AutoTank> autotanks = new ArrayList<>();
	private Map map = new Map();
	FileWR mapWR = new FileWR();
	private AutoTank autotank;
	private int numofAutoTank = 10;
	public int kill = 0;
	MusicThread musicThread = null;
		public TankJDialog(Tankframe frame) {
		super(frame);
		this.setTitle("单人游戏");
		ImageIcon backgroundi = new ImageIcon("img//back.png");// 添加背景
		JLabel background = new JLabel(backgroundi);// 将图片放入标签
		this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
		background.setBounds(0, 0, backgroundi.getIconWidth(), backgroundi.getIconHeight());
		((JPanel) getContentPane()).setOpaque(false);
		map.initMap();
		
		
		setBar();
		addKeyListener(new KeyMonitor());// 监听器会一直执行 直到程序结束
		PaintThread1 myThread = new PaintThread1();
		myThread.setdefault(this, this.maintank);
		musicThread = new MusicThread();
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
		if ((AutoTankjudge == 1)&&(autotanks.size()<numofAutoTank)) {
			    	autotank = new AutoTank();
					autotank.setXY(r.nextInt(500 + 200), r.nextInt(100 + 300));
					autotanks.add(autotank);
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
				case(4):
					autotanks.get(i).createbullet();
					break;
				case(5):
					autotanks.get(i).createbullet();
					break;
				case(6):
					autotanks.get(i).createbullet();
					break;
				case(7):
					autotanks.get(i).createbullet();
					break;
				}
			}
			
		}
	}

	public void paint(Graphics g) {// 需要得到一个变量 这个方法存在于父类，我们相当于重载了，所以加载界面时会出问题
		super.paint(g);// 解决重载问题
		int maptemp[][];
		maptemp = map.returnmap();
	    g.setColor(Color.WHITE);
	    g.setFont(new Font("楷书", 0, 15));
	    g.drawString("当前得分数："+kill, 850, 80);
	    g.drawString("最大坦克数："+numofAutoTank, 850, 100);
	    g.drawString("当前坦克数："+autotanks.size(), 850, 120);
		if (autotank != null) {

				if(maintank.canmove(maptemp))
					maintank.move();


			for (int j = 0; j < autotanks.size(); j++) {

				for (int i = 0; i < autotanks.get(j).AutoTankbullets.size(); i++) {

					if (autotanks.get(j).autotankbullet != null) {

						maintank.bulletcash(autotanks.get(j).AutoTankbullets.get(i));

					}

				}

			}

		} else {

				if(maintank.canmove(maptemp))
					maintank.move();// 改变XY坐标

		}

		maintank.draw(this,g);// 画出tank
		if(maintank.status != Status. existence) {
			ImageIcon img = new ImageIcon("img//gameOver.jpg");
			g.drawImage(img.getImage(), 100, 100, 800, 600, this);
		}

		for (int i = 0; i < maintank.maintankbullets.size(); i++) {

			 maintank.maintankbullets.get(i).canmove(maptemp);
			 maintank.maintankbullets.get(i).move();

			 maintank.maintankbullets.get(i).draw(g,this);
			 if(maintank.maintankbullets.get(i).status==Status.unexistence)
				 maintank.maintankbullets.remove(i);

		} // 主坦克子弹

		if (autotank != null) {

			for (int j = 0; j < autotanks.size(); j++) {
				if(autotanks.get(j).status == Status.unexistence) 
					autotanks.remove(j);
                  if(autotanks.get(j).canmove(maptemp))	
                	autotanks.get(j).move();		
				for (int i = 0; i <  maintank.maintankbullets.size(); i++) {

					if ( maintank.maintankbullets != null) {

						autotanks.get(j).bulletcash(maintank.maintankbullets.get(i),this);

					}

				}

				autotanks.get(j).draw(this,g);

			}

		}

		if (autotank != null) {

			for (int j = 0; j < autotanks.size(); j++) {

				for (int i = 0; i < autotanks.get(j).AutoTankbullets.size(); i++) {

					autotanks.get(j).AutoTankbullets.get(i).move();

					autotanks.get(j).AutoTankbullets.get(i).canmove(maptemp);

					autotanks.get(j).AutoTankbullets.get(i).draw(g,this);
					 if(autotanks.get(j).AutoTankbullets.get(i).status==Status.unexistence)
						 autotanks.get(j).AutoTankbullets.remove(i);

				}

			}

		} // autotank的子弹

		//g.drawImage(imageBuffer,0, 0, this); //只需绘制gPlacement一张即可，不会有闪烁现象*/

		
	}
	private void setBar() {
		JMenuBar mBar = new JMenuBar();
        setJMenuBar(mBar);
        JMenu fileMenu = new JMenu("控制自动坦克数量");
        JMenuItem miOpen = new JMenuItem("请输入正整数");
        miOpen.addActionListener(new NumofAutoActionLisener());
        mBar.add(fileMenu); 
        fileMenu.add(miOpen);
        fileMenu.addSeparator();//分隔条  
	}
	class NumofAutoActionLisener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JDialog jd = new JDialog(TankJDialog.this);
			JTextField jt = new JTextField();
			jd.setSize(200,100);
			jd.setLocationRelativeTo(null);
			jd.add(jt);
			jd.setVisible(true);
			jt.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					int num = Integer.parseInt(jt.getText());
					if(num > 0) {
					if(autotanks.size()>num) {
						for(int i=0;i<autotanks.size()-num;i++)
							autotanks.get(i).status = Status.bomb;//之前错误的把numofautotank当成autotank的数量
					}
					numofAutoTank = num;//在parse时会产生异常
					}
				}
			});
		}
		
	}
	
	
}
