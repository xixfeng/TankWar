package server;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import frame.NetTankJDialog;
import substance.Spirit.Status;

public class TankUnexistMsg {
	public int id;
	NetTankJDialog tj = null;

	public TankUnexistMsg(int id) {
		this.id = id;
	}

	public TankUnexistMsg(NetTankJDialog tj) {
		this.tj = tj;
	}

	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);// 输出到baos当中
		try {
			dos.writeInt(3);
			dos.writeInt(id);

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
			int id = dis.readInt();
			for(int i=0;i<tj.enemytanks.size();i++) {
				if(tj.enemytanks.get(i).id == id) {
					tj.enemytanks.get(i).status = Status.bomb;
				}
			}
			}
			
			catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
