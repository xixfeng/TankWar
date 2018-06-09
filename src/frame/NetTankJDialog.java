package frame;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import map.Map;
import server.ServerThread;
import server.TankMoveMsg;
import server.TankServer;
import substance.Bullet;
import substance.MainTank;
import substance.Spirit.Direction;
import substance.Spirit.Status;

public class NetTankJDialog extends MyJDialog{
	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	private Map map = new Map();
	public MainTank maintank = new MainTank(this);// ������Tank�Ķ���
	public ArrayList<MainTank> enemytanks  = new ArrayList<MainTank>();
	public MainTank enemytank = null;
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public Bullet bullet = null;
	public NetClient nc = new NetClient(this);
	int maptemp[][];

	public NetTankJDialog(Tankframe frame) {
		super(frame);
		this.setTitle("����");
		ImageIcon backgroundi = new ImageIcon("img//back.png");// ��ӱ���
		JLabel background = new JLabel(backgroundi);// ��ͼƬ�����ǩ
		this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
		background.setBounds(0, 0, backgroundi.getIconWidth(), backgroundi.getIconHeight());
		((JPanel) getContentPane()).setOpaque(false);
		map.initMap();
		maptemp = map.returnmap();

		addKeyListener(new KeyMonitor());// ��������һֱִ�� ֱ���������
		PaintThread2 myThread = new PaintThread2();
		myThread.setdefault(this, maintank);
		myThread.start();// ����߳�ʵ��paint����
		setBar();
		
	}
	private void setBar() {
		JMenuBar mBar = new JMenuBar();
        setJMenuBar(mBar);
          
        //�˵����Ҳ˵�  
        JMenu fileMenu = new JMenu("���ӷ�����");
        JMenuItem miOpen = new JMenuItem("����ip");
        JMenu fileMenu1 = new JMenu("������Ϊ������");
        JMenuItem miOpen1 = new JMenuItem("���ɷ�����");
        JMenu fileMenu2 = new JMenu("����˵��");
        JMenuItem miOpen2 = new JMenuItem("˵��");
        miOpen.addActionListener(new ConActionListener());
        miOpen1.addActionListener(new SetActionListener());
        miOpen2.addActionListener(new InfActionListener());
        mBar.add(fileMenu); 
        fileMenu.add(miOpen);
        fileMenu.addSeparator();//�ָ���  
        mBar.add(fileMenu1);
        fileMenu1.add(miOpen1);
        fileMenu1.addSeparator();
        mBar.add(fileMenu2);
        fileMenu2.add(miOpen2);
        fileMenu2.addSeparator();
	}
	public void paint(Graphics g) {// ��Ҫ�õ�һ������ ������������ڸ��࣬�����൱�������ˣ����Լ��ؽ���ʱ�������
		super.paint(g);// �����������
		if(maintank.canmove(maptemp))
		maintank.move();// �ı�XY����
		
		if(bullet != null) {
			for(int i=0;i<bullets.size();i++) {
			maintank.bulletcash(bullets.get(i).getX(), bullets.get(i).getY(),bullets.get(i).camp);
			if(bullets.get(i).status == Status.unexistence) bullets.remove(i);
			}
		}
		maintank.draw(this,g);// ����tank
		
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
				enemytanks.get(i).draw(this,g);
		}
		}
		for (int i = 0; i < maintank.maintankbullets.size(); i++) {
			
			maintank.maintankbullets.get(i).canmove(maptemp);
			maintank.maintankbullets.get(i).move();
			maintank.maintankbullets.get(i).draw(g,this);
		} // ��̹���ӵ�
		
		for(int i=0;i<bullets.size();i++) {
			bullets.get(i).move();
			bullets.get(i).canmove(maptemp);
			bullets.get(i).draw(g,this);
			if(bullets.get(i).status == Status.unexistence)bullets.remove(i);
		}
	}
	class ConActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				JDialog jd = new JDialog(NetTankJDialog.this);
				JTextField jt = new JTextField();
				jd.setSize(200,100);
				jd.setLocationRelativeTo(null);
				jd.add(jt);
				jd.setVisible(true);
				jt.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String ip = jt.getText();
						nc.connect(ip, TankServer.TCP_PORT);//���ӷ�����  ���ip��ַ�Ƿ�������ip��ַ
					}
				});
				// TODO Auto-generated method stub
				
			
		}
		
	}
	class SetActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new ServerThread().start();
			nc.connect("127.0.0.1",TankServer.TCP_PORT);
		}
	}
	class InfActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JDialog jd = new JDialog(NetTankJDialog.this);
			JTextArea jt = new JTextArea("ѡ�񱾻���Ϊ���������뽫�����ľ�����ip��ַ�����������Ϸ���������ԣ�"
					+ "������IP��ַ��ͨ��CMD����ipconfig�õ�");
			jd.setSize(800,200);
			jd.setLocationRelativeTo(null);
			jd.add(jt);
			jd.setVisible(true);
		}
		
	}
	public class KeyMonitor extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();// �Ӽ��̶����תΪ����
			Direction dir  = maintank.direction;
			maintank.tanklistener(key);
			if(dir != maintank.direction) {
				TankMoveMsg mmsg = new TankMoveMsg(maintank.id,maintank.x,maintank.y,maintank.direction);
				nc.send(mmsg);
			}
			maintank.cratebulletNet(key);
			}
		}
}

