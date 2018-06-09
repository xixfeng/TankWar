package music;

public class ShootMusicThread extends Thread{
	MusicPlayer bulletplayer = new MusicPlayer("music/bullet.wav");
	Boolean play = false;
	public void run() {
	
		bulletplayer.play();
		try {
			Thread.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bulletplayer.pause();
		
	}
}
