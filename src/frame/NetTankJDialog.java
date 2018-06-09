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
	public MainTank maintank = new MainTank(this);// 创建了Tank的对象
	public ArrayList<MainTank> enemytanks  = new ArrayList<MainTank>();
	public MainTank enemytank = null;
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public Bullet bullet = null;
	public NetClient nc = new NetClient(this);
	int maptemp[][];

	public NetTankJDialog(Tankframe frame) {
		super(frame);
		this.setTitle("联机");
		ImageIcon backgroundi = new ImageIcon("img//back.png");// 添加背景
		JLabel background = new JLabel(backgroundi);// 将图片放入标签
		this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
		background.setBounds(0, 0, backgroundi.getIconWidth(), backgroundi.getIconHeight());
		((JPanel) getContentPane()).setOpaque(false);
		map.initMap();
		maptemp = map.returnmap();

		addKeyListener(new KeyMonitor());// 监听器会一直执行 直到程序结束
		PaintThread2 myThread = new PaintThread2();
		myThread.setdefault(this, maintank);
		myThread.start();// 这个线程实现paint方法
		setBar();
		
	}
	private void setBar() {
		JMenuBar mBar = new JMenuBar();
        setJMenuBar(mBar);
          
        //菜单栏挂菜单  
        JMenu fileMenu = new JMenu("连接服务器");
        JMenuItem miOpen = new JMenuItem("输入ip");
        JMenu fileMenu1 = new JMenu("本机作为服务器");
        JMenuItem miOpen1 = new JMenuItem("生成服务器");
        JMenu fileMenu2 = new JMenu("联机说明");
        JMenuItem miOpen2 = new JMenuItem("说明");
        miOpen.addActionListener(new ConActionListener());
        miOpen1.addActionListener(new SetActionListener());
        miOpen2.addActionListener(new InfActionListener());
        mBar.add(fileMenu); 
        fileMenu.add(miOpen);
        fileMenu.addSeparator();//分隔条  
        mBar.add(fileMenu1);
        fileMenu1.add(miOpen1);
        fileMenu1.addSeparator();
        mBar.add(fileMenu2);
        fileMenu2.add(miOpen2);
        fileMenu2.addSeparator();
	}
	public void paint(Graphics g) {// 需要得到一个变量 这个方法存在于父类，我们相当于重载了，所以加载界面时会出问题
		super.paint(g);// 解决重载问题
		if(maintank.canmove(maptemp))
		maintank.move();// 改变XY坐标
		
		if(bullet != null) {
			for(int i=0;i<bullets.size();i++) {
			maintank.bulletcash(bullets.get(i).getX(), bullets.get(i).getY(),bullets.get(i).camp);
			if(bullets.get(i).status == Status.unexistence) bullets.remove(i);
			}
		}
		maintank.draw(this,g);// 画出tank
		
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
		} // 主坦克子弹
		
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
						nc.connect(ip, TankServer.TCP_PORT);//连接服务器  这个ip地址是服务器的ip地址
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
			JTextArea jt = new JTextArea("选择本机作为服务器后，请将本机的局域网ip地址发给想加入游戏的其他电脑，"
					+ "局域网IP地址可通过CMD命令ipconfig得到");
			jd.setSize(800,200);
			jd.setLocationRelativeTo(null);
			jd.add(jt);
			jd.setVisible(true);
		}
		
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
}

