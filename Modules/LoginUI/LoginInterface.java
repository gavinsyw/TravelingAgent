package LoginUI;

import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

import java.lang.*;

public class LoginInterface extends JComponent implements ActionListener{
	JButton loginButton;	
	JTextField usrName;
	JPasswordField pwd;
	static JLabel l1 = new JLabel("Username: "), l2 = new JLabel("Password: ");
	static int msgX = 100, msgY = 200;
	
	public LoginInterface()
	{
		loginButton = new JButton("Login");
		usrName = new JTextField();
		pwd = new JPasswordField();
		usrName.setBounds(100, 20, 100, 30);
		pwd.setBounds(100, 75, 100, 30);
		l1.setBounds(20, 20, 80, 30);
		l2.setBounds(20, 75, 80, 30);
		loginButton.setBounds(100, 120, 100, 30);
		add(l1);
		add(l2);
		add(usrName);
		add(pwd);
		add(loginButton);
		loginButton.addActionListener(this);
	}
	
	public void paintComponent(Graphics g)
	{
		g.drawString("Login", 150, 10);
	}

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == loginButton)
		{
			String inputUsrName = usrName.getText();
			String inputPwd = new String(pwd.getPassword());
			System.out.println(inputUsrName + " " + inputPwd);
			boolean if_login = loginAction(inputUsrName, inputPwd);
			if (if_login)
				loginSucceed();
			else
				loginFailed();
		}
	}
	
	private boolean loginAction(String usrname, String usrpwd)
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
