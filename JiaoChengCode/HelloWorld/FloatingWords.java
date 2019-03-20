package HelloWorld;

import javax.swing.*;

import java.awt.*;

public class FloatingWords extends JComponent implements Runnable{
	String words;
	int wordX, wordY;
	static int refreshTime = 20;
	
	public FloatingWords(String words, int wordX, int wordY)
	{
		this.words = words;
		this.wordX = wordX; this.wordY = wordY;
		Thread t = new Thread(this);
		t.start();
	}
	
	public void paintWords(Graphics g)
	{
		g.setColor(Color.blue);
		g.setFont(new Font("Serif", Font.PLAIN, 32));
		g.drawString(this.words, wordX, wordY);
		System.out.println("Repaint! " + wordX + " " + wordY);
	}

	public void run() 
	{
		try
		{
			while (wordX >= 0)
			{
				wordX = wordX - 1;
				repaint();
				Thread.sleep(20);
			}
		}
		catch (InterruptedException e) {e.printStackTrace(System.err);}
	}
}
