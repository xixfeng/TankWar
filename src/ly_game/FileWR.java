package ly_game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWR {
public void WriteMap(int data[][]) {
		
	    try {
	          // д��Txt�ļ� 
	          FileOutputStream getFile = new FileOutputStream(".\\maptxt\\output.txt"); // ���·�������û����Ҫ����һ���µ�output.txt�ļ�  
	          DataOutputStream getData = new DataOutputStream(getFile);
	     
	    		for(int i=0;i<24;i++) {
				for(int j=0;j<40;j++) {
					getData.writeInt(data[i][j]);
					}
				}  
	            getData.close();
	         } catch (Exception e) {  
	             e.printStackTrace();  
	            }  

	    }
		public int[][] ReadMap() {
			int temp[][];
			temp = new int[24][40];
			 try { 
		   		    FileInputStream createFile = new FileInputStream(".\\maptxt\\output.txt");

		   		    DataInputStream readData=new DataInputStream(createFile);
		    	   		    	
	 	    for(int i=0;i<24;i++) 
		   			 {
		   					for(int j=0;j<40;j++) 
		   					{
		   					   if(readData.available()>0) //����available()�ж��ļ����Ƿ����
		   					   {
		   		   		           int tempnum = readData.readInt();
		   		   		           temp[i][j] = tempnum;
		   		   		          
		   		   		           }
		   				       else{
		   					        break;
		   					   }
		   					   
		   				     }
		   					
		   			 }  
	 	    
		   		    readData.close();

		   		    createFile.close();

		   		     }catch (IOException e) {  

		   		         e.printStackTrace();  
		   		     }

		return temp;
		}

}
