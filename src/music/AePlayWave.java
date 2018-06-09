package music;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class AePlayWave
  extends Thread
{
  public Boolean isContinue = Boolean.valueOf(true);
  private Boolean isLoop = Boolean.valueOf(false);
  private AudioInputStream audioInputStream = null;
  private String filename;
  
  public AePlayWave(String wavfile, Boolean isLoop) { this.isLoop = isLoop;
    this.filename = wavfile;
    File soundFile = new File(this.filename);
    try {
      this.audioInputStream = AudioSystem.getAudioInputStream(soundFile);
    } catch (Exception e1) {
      e1.printStackTrace();
      return;
    }
  }
  
  public void toEnd() { this.isContinue = Boolean.valueOf(false);
    this.isLoop = Boolean.valueOf(false);
  }
  
  public void run() { AudioFormat format = this.audioInputStream.getFormat();
    SourceDataLine auline = null;
    DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
    try
    {
      auline = (SourceDataLine)AudioSystem.getLine(info);
      auline.open(format);
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
    
    auline.start();
    int nBytesRead = 0;
    
    byte[] abData = new byte[512];
    try
    {
      if (!this.isLoop.booleanValue())
        while (nBytesRead != -1) {
          nBytesRead = this.audioInputStream.read(abData, 0, abData.length);
          if (nBytesRead >= 0)
            auline.write(abData, 0, nBytesRead);
        }
      while (this.isLoop.booleanValue()) {
        while ((nBytesRead != -1) && (this.isContinue.booleanValue())) {
          nBytesRead = this.audioInputStream.read(abData, 0, abData.length);
          if (nBytesRead >= 0)
            auline.write(abData, 0, nBytesRead);
        }
        if (!this.isContinue.booleanValue()) {
          try {
            Thread.sleep(30L);
          }
          catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        else if (nBytesRead == -1) {
          this.audioInputStream.close();
          File soundFile = new File(this.filename);
          try {
            this.audioInputStream = AudioSystem.getAudioInputStream(soundFile);
          } catch (Exception e1) {
            e1.printStackTrace();
            return;
          }
          nBytesRead = 0;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      return;
    } finally {
      auline.drain();
      auline.close();
    }
    auline.drain();
    auline.close();
  }
}