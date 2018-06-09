package frame;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

import server.BulletMsg;
import server.TankMoveMsg;
import server.TankNewmsg;
import server.TankServer;
import server.TankUnexistMsg;

public class NetClient {
	NetTankJDialog tj = null;
	private int udpPort;
	DatagramSocket ds = null;
	String ip;//server ip

	public NetClient(NetTankJDialog netTankJDialog) {
		Random r = new Random(); 
		udpPort = r.nextInt(3000)+3000;
		this.tj = netTankJDialog;
		try {
			ds = new DatagramSocket(udpPort);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void connect(String IP, int port) {
		Socket s = null;
		ip = IP;
		try {
			s = new Socket(IP, port);// ��socket��serversocket���������ľ������TCP_PORT
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeInt(udpPort);
			DataInputStream dis = new DataInputStream(s.getInputStream());// �õ�maintank id
			int id = dis.readInt();
			tj.maintank.id = id;
			System.out.println("Connected to server!and server give me a ID:" + id);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (s != null) {
				try {
					s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		TankNewmsg msg = new TankNewmsg(tj.maintank);
		send(msg);// ��������������ݰ�
		new Thread(new UDPRecvThread()).start();
	}

	public void send(TankNewmsg msg) {
		msg.send(ds, ip, TankServer.UDP_PORT);
	}

	public void send(TankMoveMsg mmsg) {
		mmsg.send(ds, ip, TankServer.UDP_PORT);
	}

	public void send(BulletMsg buMsg) {
		buMsg.send(ds, ip, TankServer.UDP_PORT);
	}
	public void send(TankUnexistMsg tuMsg) {
		tuMsg.send(ds, ip, TankServer.UDP_PORT);
		System.out.println(tuMsg.id);
	}
	class UDPRecvThread implements Runnable {//���պͷ���
		byte[] buf = new byte[1024];

		@Override
		public void run() {
			while (ds != null) {
				DatagramPacket dp = new DatagramPacket(buf, buf.length);// �����ݶ���buf����
				try {
					ds.receive(dp);
					parse(dp);
					System.out.println("a package is received from server!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private void parse(DatagramPacket dp) {
			ByteArrayInputStream bais = new ByteArrayInputStream(buf, 0, dp.getLength());
			DataInputStream dis = new DataInputStream(bais);
			try {
				int type = dis.readInt();//��һ��ȡ������
				switch (type) {
				case 0:
					TankNewmsg msg = new TankNewmsg(NetClient.this.tj);// �ڲ�����ʷ�װ���Ա����
					msg.parse(dis);
					break;

				case 1:
					TankMoveMsg mmsg = new TankMoveMsg(NetClient.this.tj);
					mmsg.parse(dis);
					break;
				
				case 2:
					BulletMsg buMsg = new BulletMsg(NetClient.this.tj);
					buMsg.parse(dis);
					break;
					
				case 3:
					TankUnexistMsg tuMsg = new TankUnexistMsg(NetClient.this.tj);
					tuMsg.parse(dis);
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}