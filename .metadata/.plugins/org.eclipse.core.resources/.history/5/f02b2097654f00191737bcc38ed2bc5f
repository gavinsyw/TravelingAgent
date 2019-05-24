package LoginUI;

import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

import java.lang.*;

public class LoginInterface extends JComponent implements ActionListener{
	JButton loginButton;	
	static int msgX = 100, msgY = 200;
	
	public LoginInterface()
	{
		loginButton = new JButton("Login");
	}
	
	public void paintComponent(Graphics g)
	{
		g.drawString("Login", 100, 100);
	}

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == loginButton)
		{
			boolean if_login = loginAction();
			if (if_login)
				loginSucceed();
			else
				loginFailed();
		}
	}
	
	private boolean loginAction()
	{
		return true;
	}
	
	private void loginSucceed() // paint the interface when login succeed
	{
		return;
	}
	
	private void loginFailed() // paint the interface hwne login failed
	{
		return;
	}
}
