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
		while (maintank.status == Status.existence) {// ����ѭ��
			try {
				Thread.sleep(28);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tankjdialog.caculateAutoTank();
			tankjdialog.repaint();// repaint()������paint()��update()���� ��ʵ�ֲ�������
		}
		int newrecord;
		File file = new File("record.txt");
		newrecord = tankjdialog.getautotanknum();
		try {
			FileInputStream in = new FileInputStream(file);
			byte recordin[] = new byte[1024];
			int len = in.read(recordin);
			try {
				String s = new String(recordin, 20, len - 20);// ��������������Ҫ�ֽ�λ�� ����ܹؼ�
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
