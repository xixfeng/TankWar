package ly_game;

import java.io.File;
import java.io.FileInputStream;
import ly_game.Bullet.Status;

public class PaintThread1 extends Thread {
	private TankJDialog tankjdialog;
	private MainTank maintank;

	public void setdefault(TankJDialog tankJDialog, MainTank maintank) {
		this.tankjdialog = tankJDialog;
		this.maintank = maintank;
	}
	public void run() {
		// TODO Auto-generated method stub
		while (maintank.status == Status.existence) {// 无限循环
			try {
				Thread.sleep(28);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tankjdialog.caculateAutoTank();
			tankjdialog.repaint();// repaint()将调用paint()与update()方法 即实现擦除功能
		}
		int newrecord;
		File file = new File("record.txt");
		newrecord = tankjdialog.getautotanknum();
		try {
			FileInputStream in = new FileInputStream(file);
			byte recordin[] = new byte[1024];
			int len = in.read(recordin);
			try {
				String s = new String(recordin, 20, len - 20);// 第三个参数是需要字节位数 这个很关键
				int oldrecord;
				oldrecord = Integer.parseInt(s.toString());
				if (newrecord > oldrecord) {
					Record.record(newrecord);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
