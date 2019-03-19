package HelloWorld;

import javax.swing.*;

import java.awt.*;

public class FloatingWords extends JComponent implements Runnable{
	static String words;
	boolean running;
	double floatSpeed;
	static int boxWidth, boxLength;
	double wordX, wordY;
	static int refreshTime = 20;
	Thread t;
	
	public FloatingWords(String words, double floatSpeed, int boxWidth, int boxLength)
	{
		FloatingWords.words = words;
		this.floatSpeed = floatSpeed;
		FloatingWords.boxLength = boxLength;
		FloatingWords.boxWidth = boxWidth;
		this.wordX = boxLength; this.wordY = boxWidth / 2;
		t = new Thread(this);
		t.start();
	}
	
	public void paintWords(Graphics g)
	{
		g.setColor(Color.black);
		g.setFont(new Font("Serif", Font.PLAIN, 64));
		g.drawString(words, (int)wordX, (int)wordY);
	}

	public void run() 
	{
		try
		{
			while (running)
			{
				wordX = wordX - floatSpeed * refreshTime;
				repaint();
				System.out.println(wordX);
				Thread.sleep(refreshTime);
			}
		}
		catch (InterruptedException e) {e.printStackTrace(System.err);}
	}
}
