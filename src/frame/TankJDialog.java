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
	MainTank maintank = new MainTank();// ������Tank�Ķ���
	private ArrayList<AutoTank> autotanks = new ArrayList<>();
	private Map map = new Map();
	FileWR mapWR = new FileWR();
	private AutoTank autotank;
	private int numofAutoTank = 10;
	public int kill = 0;
	MusicThread musicThread = null;
		public TankJDialog(Tankframe frame) {
		super(frame);
		this.setTitle("������Ϸ");
		ImageIcon backgroundi = new ImageIcon("img//back.png");// ��ӱ���
		JLabel background = new JLabel(backgroundi);// ��ͼƬ�����ǩ
		this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
		background.setBounds(0, 0, backgroundi.getIconWidth(), backgroundi.getIconHeight());
		((JPanel) getContentPane()).setOpaque(false);
		map.initMap();
		
		
		setBar();
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

	public void paint(Graphics g) {// ��Ҫ�õ�һ������ ������������ڸ��࣬�����൱�������ˣ����Լ��ؽ���ʱ�������
		super.paint(g);// �����������
		int maptemp[][];
		maptemp = map.returnmap();
	    g.setColor(Color.WHITE);
	    g.setFont(new Font("����", 0, 15));
	    g.drawString("��ǰ�÷�����"+kill, 850, 80);
	    g.drawString("���̹������"+numofAutoTank, 850, 100);
	    g.drawString("��ǰ̹������"+autotanks.size(), 850, 120);
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
					maintank.move();// �ı�XY����

		}

		maintank.draw(this,g);// ����tank
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

		} // ��̹���ӵ�

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

		} // autotank���ӵ�

		//g.drawImage(imageBuffer,0, 0, this); //ֻ�����gPlacementһ�ż��ɣ���������˸����*/

		
	}
	private void setBar() {
		JMenuBar mBar = new JMenuBar();
        setJMenuBar(mBar);
        JMenu fileMenu = new JMenu("�����Զ�̹������");
        JMenuItem miOpen = new JMenuItem("������������");
        miOpen.addActionListener(new NumofAutoActionLisener());
        mBar.add(fileMenu); 
        fileMenu.add(miOpen);
        fileMenu.addSeparator();//�ָ���  
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
							autotanks.get(i).status = Status.bomb;//֮ǰ����İ�numofautotank����autotank������
					}
					numofAutoTank = num;//��parseʱ������쳣
					}
				}
			});
		}
		
	}
	
	
}
