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
	}// 多种构造方法

	public TankNewmsg(NetTankJDialog tj) {
		this.tj = tj;
	}

	public void send(DatagramSocket ds, String ip, int udpport) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);// 输出到baos当中
		try {
			dos.writeInt(0);
			dos.writeInt(maintank.id);
			dos.writeInt(maintank.x);
			dos.writeInt(maintank.y);
			dos.writeInt(maintank.status.ordinal());
			dos.writeInt(maintank.direction.ordinal());// 信息一个一个写到字节数组里面

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
				Direction direction = Direction.values()[dis.readInt()];// 把下标值变为枚举类型
				boolean exist = false;
				for (int i = 0; i < tj.enemytanks.size();i++) {
					if (tj.enemytanks.get(i).id == id) {
						exist = true;
						break;
						}//妈耶 一个break 整得调试了半天
				}
				if (!exist) {
					tj.enemytank = new MainTank(x, y, direction, id ,status,3);//不存在就发送tank消息
					tj.enemytanks.add(tj.enemytank);

					TankNewmsg tnMsg = new TankNewmsg(tj.maintank);
					tj.nc.send(tnMsg);//使后一个得到前一个的消息
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
