package music;

public class BombMusicThread extends Thread{
	MusicPlayer bulletplayer = new MusicPlayer("music/bulletwall.wav");
	public void run() {
	
		bulletplayer.play();
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bulletplayer.pause();
	}
}
