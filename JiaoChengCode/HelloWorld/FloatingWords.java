package HelloWorld;

import javax.swing.*;

import java.awt.*;

public class FloatingWords extends JComponent implements Runnable{
	String words;
	int wordX, wordY;
	static int refreshTime = 10;
	Color color;
	
	
	public FloatingWords(String words, int wordX, int wordY, Color c)
	{
		color = c;
		this.words = words;
		this.wordX = wordX; this.wordY = wordY;
		Thread t = new Thread(this);
		t.start();
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(color);
		g.setFont(new Font("Serif", Font.PLAIN, 32));
		g.drawString(this.words, wordX, wordY);
//		System.out.println("Repaint! " + wordX + " " + wordY);
	}

	public void run() 
	{
		try
		{
			while (true)
			{
				while (wordX >= -100)
				{
					wordX = wordX - 1;
					repaint();
					Thread.sleep(refreshTime);
				}
				while (wordX <= 300)
				{
					wordX = wordX + 1;
					repaint();
					Thread.sleep(refreshTime);
				}
			}
		}
		catch (InterruptedException e) {e.printStackTrace(System.err);}
	}
}
