import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

import HelloWorld.*;

public class HelloJava2 {
	static JFrame frame = new JFrame("Hello Java2");
	public static void main(String[] args)
	{
		HelloComponent hc = new HelloComponent("Hello Java!");
		FloatingWords fw = new FloatingWords("Hello!", 500, 150, Color.green);
//		frame.add(hc);
		frame.add(fw);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		frame.setVisible(true);
	}
}

class HelloComponent extends JComponent implements MouseMotionListener, ActionListener, Runnable
{
	String theMessage;
	int messageX = 125, messageY = 95;
	JButton theButton;
	Clock c;
//	FloatingWords w;
	
	int colorIndex;
	static Color[] someColors = {
			Color.black, Color.red, Color.green, Color.blue, Color.magenta
	};
	
	boolean blinkState;
	
	public HelloComponent(String message)
	{
		theMessage = message;
		theButton = new JButton("Change Color");
		c = new Clock();
//		w = new FloatingWords("Hello!", 300, 150);
		setLayout(new FlowLayout());
		add(theButton);
		theButton.addActionListener(this);
		addMouseMotionListener(this);
		Thread t1 = new Thread(this);
		t1.start();
		Thread t2 = new Thread(c);
		t2.start();
	}

	public void paintComponent(Graphics g)
	{
		g.setColor(blinkState ? getBackground() : currentColor());
		Font font = new Font("Serif", Font.PLAIN, 64);
		g.setFont(font);
		c.paint(g);
//		w.paintWords(g);
		g.drawString(theMessage, messageX, messageY);
		System.out.println("Repaint!");	
	}
	
	public void mouseDragged(MouseEvent e)
	{
		messageX = e.getX();
		messageY = e.getY();
		repaint();
	}
	
	public void mouseMoved(MouseEvent e)
	{
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == theButton)
			changeColor();
	}
	
	/**
	 * 
	 */
	synchronized private void changeColor()
	{
		if (++colorIndex == someColors.length)
			colorIndex = 0;
		setForeground(currentColor());
		repaint();
	}
	
	synchronized private Color currentColor()
	{
		return someColors[colorIndex];
	}
	
	public void run()
	{
		try 
		{
			while (true)
			{
				blinkState = !blinkState;
				repaint();
				Thread.sleep(300);
			}
		}
		catch (InterruptedException ie) 
		{
			ie.printStackTrace(System.err);
		}
	}
}

