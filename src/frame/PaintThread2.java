package frame;

import substance.MainTank;
import substance.Spirit.Status;

public class PaintThread2 extends Thread{
	private NetTankJDialog nettankjdialog;
	private MainTank maintank;

	public void setdefault(NetTankJDialog nettankJDialog, MainTank maintank) {
		this.nettankjdialog = nettankJDialog;
		this.maintank = maintank;
	}
	
	public void run() {
		while (maintank.status == Status.existence) {// ����ѭ��
			try {
				Thread.sleep(36);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			nettankjdialog.repaint();// repaint()������paint()��update()���� ��ʵ�ֲ�������
		}
		if(maintank.status == Status.unexistence) {
			nettankjdialog.setVisible(false);
		}
	}
}
