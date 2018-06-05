package ly_game;

import javax.sound.sampled.*;  
import java.io.*;  
  
public class MusicPlayer {  
    private String musicPath; //音频文件的路径  
    private volatile boolean run = true;  //记录音频是否播放  
      
    private AudioInputStream audioStream;  
    private AudioFormat audioFormat;  
    private SourceDataLine sourceDataLine;  
      
    /*public MusicPlayer(String musicPath) {  
        this.musicPath = musicPath;  
        prefetch();  
        //得到音频文件的路径开始转换
    } */
    public void prepareMusic (String musicFilePath) {
    	musicPath = musicFilePath;
    	prefetch(); 
    	
    }
    
    //数据准备  
     private void prefetch(){  
        try{  
        //获取音频输入流  
        audioStream = AudioSystem.getAudioInputStream(new File(musicPath));  
        //获取dataline支持的音频编码格式
        audioFormat = audioStream.getFormat();  
        
        //包装音频信息  ,datalineInfo接受音频格式和文件大小构造一个新的类(该类可以包含不同的频率信息)
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,  audioFormat,AudioSystem.NOT_SPECIFIED);
        
        //使用包装音频信息后的Info类创建源数据行，充当混频器的源  
        sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);  
        
        //用原格式打开数据行
        sourceDataLine.open(audioFormat);  
        sourceDataLine.start();  
          
        }catch(UnsupportedAudioFileException ex){  
            ex.printStackTrace();  
        }catch(LineUnavailableException ex){  
            ex.printStackTrace();  
        }catch(IOException ex){  
            ex.printStackTrace();  
        }  
          
    }  
    //析构函数:关闭音频读取流和数据行  
    protected void finalize() throws Throwable{  
        super.finalize();  
      
        sourceDataLine.drain();  
        sourceDataLine.close();  
        audioStream.close();  
    }  
      
    //播放音频:通过loop参数设置是否循环播放  
    private void playMusic(boolean loop)throws InterruptedException {  
        try{  
                if(loop){  
                    while(true){  
                        playMusic();  
                    }  
                }else{  
                    playMusic();  
                    //清空数据行并关闭  
                    sourceDataLine.drain();  
                    sourceDataLine.close();  
                    audioStream.close();  
                }  
              
        }catch(IOException ex){  
            ex.printStackTrace();  
        }  
          
          
    }  
    private void playMusic(){  
        try{  
            synchronized(this){  
                run = true;  
            }  
            //通过数据行读取音频数据流，发送到混音器;  
            //数据流传输过程：AudioInputStream -> SourceDataLine;  
            audioStream = AudioSystem.getAudioInputStream(new File(musicPath));  
            int count;  
            byte tempBuff[] = new byte[1024];  
              
                while((count = audioStream.read(tempBuff,0,tempBuff.length)) != -1){  
                    synchronized(this){  
                    while(!run)  
                        wait();  
                    }  
                    sourceDataLine.write(tempBuff,0,count);  
                              
            }  
  
        }catch(UnsupportedAudioFileException ex){  
            ex.printStackTrace();  
        }catch(IOException ex){  
            ex.printStackTrace();  
        }catch(InterruptedException ex){  
            ex.printStackTrace();  
        }  
          
    }  
      
      
    //暂停播放音频  
    private void stopMusic(){  
        synchronized(this){  
            run = false;  
            notifyAll();  
        }  
    }  
    //继续播放音乐  
       
    //外部调用控制方法:生成音频主线程；  
    public void start(boolean loop){  
       
                try {  
                    playMusic(loop);  
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                }  
           
    }  
      
    //外部调用控制方法：暂停音频线程  
    public void stop(){  
          
                stopMusic();  
    }  
}