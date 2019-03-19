package HelloWorld;

import java.applet.*;

import javax.swing.*;

import java.awt.*;

import java.awt.event.*;

public class UpdateApplet extends java.applet.Applet implements Runnable{
	Thread thread;
	boolean running;
	int updateInterval = 1000;
	
	public void run()
	{
		while (running)
		{
			repaint();
			try
			{
				Thread.sleep(updateInterval);
			}
			catch (InterruptedException e)
			{
				System.out.println("Starting...");
				return;
			}
		}
	}
	
	public void start()
	{
		System.out.println("starting ...");
		if (!running)
		{
			running = true;
			thread  = new Thread(this);
			thread.start();
		}
	}
	
	public void stop()
	{
		System.out.println("stopping ...");
		thread.interrupt();
		running = false;
	}
}
