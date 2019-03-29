package LoginUI;

import java.awt.Color;

import javax.swing.JFrame;

import LoginUI.*;

public class displayUI {
	static JFrame frame = new JFrame("Login");
	public static void main(String[] args)
	{
		LoginInterface li = new LoginInterface();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(li);
		frame.setSize(300, 200);
		frame.setVisible(true);
	}
}
