package ly_game;

import ly_game.Bullet.Status;

public class PaintThread2 extends Thread{
	private NetTankJDialog nettankjdialog;
	private MainTank maintank;

	public void setdefault(NetTankJDialog nettankJDialog, MainTank maintank) {
		this.nettankjdialog = nettankJDialog;
		this.maintank = maintank;
	}
	
	public void run() {
		while (maintank.status == Status.existence) {// 无限循环
			try {
				Thread.sleep(28);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			nettankjdialog.repaint();// repaint()将调用paint()与update()方法 即实现擦除功能
		}
		
	}
}
