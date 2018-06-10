package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class TankServer {
	public static int ID = 1;
	public static final int TCP_PORT = 8888;
	public static final int UDP_PORT = 6666;
	
	ArrayList<Client> clients = new ArrayList<Client>();// 储存客户端信息
	
	@SuppressWarnings("resource")
	public void start() {
		UDPThread thread = new UDPThread(); 
		Thread udpthread = new Thread(thread);
		udpthread.start();
		ServerSocket ss = null;
		Client c = null;
		try {
			ss = new ServerSocket(TCP_PORT);
			System.out.println(ss.getInetAddress());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (true) {
			Socket s = null;			
			try {
			s = ss.accept();
			DataInputStream dis = new DataInputStream(s.getInputStream());
			String IP = s.getInetAddress().getHostAddress();
			int udpPort = dis.readInt();
			c = new Client(IP,udpPort);
			clients.add(c);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeInt(ID++);
			System.out.println("A Client connect! Addr- " + s.getInetAddress() + ":" + s.getPort() +"----UPD Port" + udpPort);
		}catch (IOException e){
			e.printStackTrace();
		}finally {
			if(s!=null) {
				try {
					s.close();
					s = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		}
	}
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		new TankServer().start();
//	}

	private class Client {
		String IP;
		int udpPort;
		
		public Client(String IP,int udpPort) {
			this.IP = IP;
			this.udpPort = udpPort;
		}
	}
	
	class UDPThread implements Runnable{
		byte[] buf = new byte[1024];
		@Override
		public void run() {
			DatagramSocket ds = null;
			try {
				ds = new DatagramSocket(UDP_PORT);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("udp thread started at port:" + UDP_PORT);
			while(ds != null) {
				DatagramPacket dp = new DatagramPacket(buf, 1024);//将数据读到buf里面
				try {
					ds.receive(dp);
					for(int i=0;i<clients.size();i++) {
						String IP = clients.get(i).IP;
						int udpport = clients.get(i).udpPort;
						dp.setSocketAddress(new InetSocketAddress(IP, udpport));
						ds.send(dp);
					}
					System.out.println("a package is received!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
}
}
