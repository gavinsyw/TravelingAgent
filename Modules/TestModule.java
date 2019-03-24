import LoginUI.*;

import java.awt.*;

import javax.swing.*;


public class TestModule {
	
	static JFrame frame = new JFrame("Test Module");
	
	public static void main(String[] args) 
	{
		LoginInterface li = new LoginInterface();
		frame.add(li);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		frame.setVisible(true);
	}

}
