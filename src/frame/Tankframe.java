package frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Tankframe extends JFrame {
	public Tankframe() {
		Container container = this.getContentPane();
		container.setBackground(Color.black);
		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);
		JButton jb1 = new JButton("开始游戏");
		JButton jb2 = new JButton("游戏记录");
		JButton jb3 = new JButton("联机游戏");
		jb1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new TankJDialog(Tankframe.this).setVisible(true);
			}
		});

		jb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new RecordJDialog(Tankframe.this).setVisible(true);
			}
		});
		jb3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new NetTankJDialog(Tankframe.this).setVisible(true);
			}
		});
		/*jb1.setBorderPainted(false);
		jb1.setContentAreaFilled(false);
		jb1.setContentAreaFilled(false);*/
		setLayout(new FlowLayout(1, 1, 1));// 流式布局
		container.add(jb1);
		container.add(jb3);
		container.add(jb2);
		ImageIcon backgroundi = new ImageIcon("img//TankBackground.jpg");// 添加背景
		JLabel background = new JLabel(backgroundi);// 将图片放入标签
		this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
		background.setBounds(0, 0, backgroundi.getIconWidth(), backgroundi.getIconHeight());
		((JPanel) getContentPane()).setOpaque(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
