package server;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import frame.NetTankJDialog;
import substance.Bullet;
import substance.Spirit.Direction;

public class BulletMsg{
	int id;
	int x;
	int y;
	Direction direction;
	NetTankJDialog tj = null;

	public BulletMsg(int id, int x,int y,Direction direction) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public BulletMsg(NetTankJDialog tj) {
		this.tj = tj;
	}

	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);// 输出到baos当中
		try {
			dos.writeInt(2);
			dos.writeInt(id);
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(direction.ordinal());// 信息一个一个写到字节数组里面

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] buf = baos.toByteArray();
		DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(IP, udpPort));
		try {
			ds.send(dp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void parse(DataInputStream dis) {
		try {
			int camp = 0;
			int id = dis.readInt();
			if(tj.maintank.id!=id) {
			int x = dis.readInt();
			int y = dis.readInt();
			Direction direction = Direction.values()[dis.readInt()];//把下标值变为枚举类型
			
			if(id%2==0) {
				camp = 0;
			}
			if(id%2==1){
				camp = 1;
			}
			tj.bullet = new Bullet(id,x-12,y-12,direction);
			tj.bullet.v = 10;
			tj.bullet.camp = camp;
			tj.bullets.add(tj.bullet);}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
