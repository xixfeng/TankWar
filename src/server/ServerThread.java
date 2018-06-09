package server;

public class ServerThread extends Thread{
	public void run() {
		new TankServer().start();
	}
}
