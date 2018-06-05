package ly_game;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JDialog;

public class MyJDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int Screenwidth = 1000;
	static final int Screenheight = 600;
	static final int ScreenX = 450;//450
	static final int ScreenY = 250;//250
	static final int LeftEdge = 10;
	static final int RightEdge = 987;
	static final int UpEdge = 40;
	static final int DownEdge = 585;

	public MyJDialog(Tankframe frame) {
		// 实例化一个对象，指定对话框的父体，标题，类型
		super(frame, "TANK", true);
		Container container = getContentPane();
		container.setBackground(Color.black);
		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}
}
