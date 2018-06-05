package ly_game;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

public class Record {
	public static void record(int autotanknum) {
		File file = new File("record.txt");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 获取时间
		try {
			FileOutputStream out = new FileOutputStream(file);
			byte recordout[] = (df.format(System.currentTimeMillis()) + " " + autotanknum).getBytes();
			out.write(recordout);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}