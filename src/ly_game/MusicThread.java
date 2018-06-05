package ly_game;
public class MusicThread extends Thread {
	MusicPlayer musicPlayer = new MusicPlayer();
	
	public void run() {
		// TODO Auto-generated method stub
		musicPlayer.prepareMusic("music/Hyperfun.wav");
		musicPlayer.start(true);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		musicPlayer.stop();
		
	}

}
