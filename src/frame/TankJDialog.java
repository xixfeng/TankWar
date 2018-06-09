package frame;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import map.Map;
import music.MusicThread;
import substance.AutoTank;
import substance.MainTank;
import substance.Spirit.Direction;
import substance.Spirit.Status;

@SuppressWarnings("serial")
public class TankJDialog extends MyJDialog {
	MainTank maintank = new MainTank();// ������Tank�Ķ���
	private ArrayList<AutoTank> autotanks = new ArrayList<>();
	private Map map = new Map();
	FileWR mapWR = new FileWR();
	private AutoTank autotank;
	private AutoTank autotankClone;
	MusicThread musicThread = null;
		public TankJDialog(Tankframe frame) {
		super(frame);
		this.setTitle("ly_tank");
		ImageIcon backgroundi = new ImageIcon("img//back.png");// ��ӱ���
		JLabel background = new JLabel(backgroundi);// ��ͼƬ�����ǩ
		this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
		background.setBounds(0, 0, backgroundi.getIconWidth(), backgroundi.getIconHeight());
		((JPanel) getContentPane()).setOpaque(false);
		map.initMap();
		
		
		addKeyListener(new KeyMonitor());// ��������һֱִ�� ֱ���������
		PaintThread1 myThread = new PaintThread1();
		myThread.setdefault(this, this.maintank);
		musicThread = new MusicThread();
		musicThread.start();
		myThread.start();// ����߳�ʵ��paint����
		
		}
	public class KeyMonitor extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();// �Ӽ��̶����תΪ����
			maintank.tanklistener(key);
			maintank.cratebullet(key);
		}
	}

	public void caculateAutoTank() {
		Random r = new Random();
		int AutoTankjudge = r.nextInt(50);// ��0��50��������
		if (AutoTankjudge == 1) {
			
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
				}
			}
			
		}
	}

	public void paint(Graphics g) {// ��Ҫ�õ�һ������ ������������ڸ��࣬�����൱�������ˣ����Լ��ؽ���ʱ�������
		super.paint(g);// �����������
		int maptemp[][];
		maptemp = map.returnmap();

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
					maintank.move();// �ı�XY����

		}

		maintank.draw(this,g);// ����tank

		for (int i = 0; i < maintank.maintankbullets.size(); i++) {

			 maintank.maintankbullets.get(i).canmove(maptemp);
			 maintank.maintankbullets.get(i).move();

			 maintank.maintankbullets.get(i).draw(g,this);
			 if(maintank.maintankbullets.get(i).status==Status.unexistence)
				 maintank.maintankbullets.remove(i);

		} // ��̹���ӵ�

		if (autotank != null) {

			for (int j = 0; j < autotanks.size(); j++) {

                  if(autotanks.get(j).canmove(maptemp))
                	
                	autotanks.get(j).move();		
				for (int i = 0; i <  maintank.maintankbullets.size(); i++) {

					if ( maintank.maintankbullets != null) {

						autotanks.get(j).bulletcash( maintank.maintankbullets.get(i));

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

		} // autotank���ӵ�

		//g.drawImage(imageBuffer,0, 0, this); //ֻ�����gPlacementһ�ż��ɣ���������˸����*/

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
