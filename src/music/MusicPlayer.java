package music;

public class MusicPlayer {
	  private AePlayWave player;
	  private Boolean isPlaying = Boolean.valueOf(true);
	  
	  public MusicPlayer(String file) { this.player = new AePlayWave(file, Boolean.valueOf(true));
	    this.player.start();
	  }
	  
	  public void pause() { if (this.isPlaying.booleanValue()) {
	      this.player.isContinue = Boolean.valueOf(false);
	      this.isPlaying = Boolean.valueOf(false);
	    }
	  }
	  
	  public void play() { if (!this.isPlaying.booleanValue()) {
	      this.player.isContinue = Boolean.valueOf(true);
	      this.isPlaying = Boolean.valueOf(true);
	    }
	  }
	  
	  public void change(String file) { this.player.toEnd();
	    this.player = new AePlayWave(file, Boolean.valueOf(true));
	    this.player.start();
	  }
	}
