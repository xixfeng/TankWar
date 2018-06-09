package server;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import frame.NetTankJDialog;
import substance.MainTank;
import substance.Spirit.Direction;
import substance.Spirit.Status;

public class TankNewmsg {
	MainTank maintank = null;
	NetTankJDialog tj = null;

	public TankNewmsg(MainTank maintank) {
		this.maintank = maintank;
	}// ���ֹ��췽��

	public TankNewmsg(NetTankJDialog tj) {
		this.tj = tj;
	}

	public void send(DatagramSocket ds, String ip, int udpport) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);// �����baos����
		try {
			dos.writeInt(0);
			dos.writeInt(maintank.id);
			dos.writeInt(maintank.x);
			dos.writeInt(maintank.y);
			dos.writeInt(maintank.status.ordinal());
			dos.writeInt(maintank.direction.ordinal());// ��Ϣһ��һ��д���ֽ���������

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] buf = baos.toByteArray();
		DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(ip, udpport));
		try {
			ds.send(dp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void parse(DataInputStream dis) {
		try {
			int id = dis.readInt();
			/*System.out.println("id:" + id);*/
			if (tj.maintank.id != id) {
				int x = dis.readInt();
				int y = dis.readInt();
				Status status = Status.values()[dis.readInt()];
				Direction direction = Direction.values()[dis.readInt()];// ���±�ֵ��Ϊö������
				boolean exist = false;
				for (int i = 0; i < tj.enemytanks.size();i++) {
					if (tj.enemytanks.get(i).id == id) {
						exist = true;
						break;
						}//��Ү һ��break ���õ����˰���
				}
				if (!exist) {
					tj.enemytank = new MainTank(x, y, direction, id ,status,3);//�����ھͷ���tank��Ϣ
					tj.enemytanks.add(tj.enemytank);

					TankNewmsg tnMsg = new TankNewmsg(tj.maintank);
					tj.nc.send(tnMsg);//ʹ��һ���õ�ǰһ������Ϣ
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
