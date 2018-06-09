package music;

public class MusicThread extends Thread {
	public MusicPlayer musicPlayer = new MusicPlayer("music/Hyperfun.wav");
	
	public void run() {
		// TODO Auto-generated method stub
		musicPlayer.play();
	}
}
