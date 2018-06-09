package frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JLabel;

public class RecordJDialog extends MyJDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecordJDialog(Tankframe frame) {
		super(frame);
		File file = new File("record.txt");
		String record = null;
		Container container = getContentPane();
		setBackground(Color.BLACK);
		setLayout(new FlowLayout(1, 1, 1));// 流式布局
		try {
			FileInputStream in = new FileInputStream(file);
			byte recordin[] = new byte[1024];
			int len = in.read(recordin);
			record = new String(recordin, 0, len);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		JLabel jl = new JLabel(record.toString());
		jl.setFont(new Font("宋体", Font.BOLD, 30));
		jl.setForeground(Color.WHITE);
		container.add(jl);
	}
}
