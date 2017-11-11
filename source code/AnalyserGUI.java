import javax.swing.*;
import java.awt.*;

/**
 * Created by Tomas Kovtun on 1/22/2017.
 */
public class AnalyserGUI{ 
	public static void main(String [] args){
		filledFrame frame = new filledFrame();
		frame.setVisible(true);
	}
		
}

class filledFrame extends JFrame {
	JTextField fileInput = new JTextField(20);
	JTextField fileInput2 = new JTextField(20);
	JButton generate = new JButton("Generate");
	JButton open = new JButton("Open");
	JPanel buttonPanel = new JPanel();
	JPanel inputPanel = new JPanel();
	JPanel dropPanel = new JPanel();

	public filledFrame() {
		String [] menuList = new String[]{"CE201", "CE202", "CE203"};
		JComboBox dropMenu = new JComboBox(menuList);
		this.dropPanel.add(dropMenu);
		this.dropPanel.add(this.open);
		this.inputPanel.add(this.fileInput);
		this.inputPanel.add(this.fileInput2);
		this.buttonPanel.add(this.generate);
		this.setLayout(new GridLayout(3, 0));
		this.add(this.inputPanel);
		this.add(this.dropPanel);
		this.add(this.buttonPanel);
		this.setSize(600, 600);
	}
}

	
