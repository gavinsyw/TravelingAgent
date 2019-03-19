package Threads;

import java.util.*;

public class Consumer implements Runnable{
	Producer producer;
	String name;
	
	Consumer(String name, Producer producer) {
		this.producer = producer;
		this.name = name;
	}
	
	public void run()
	{
		while (true)
		{
			String message = producer.getMessage();
			System.out.println(name + "Got message: " + message);
			try {
				Thread.sleep(2000);
			}catch(InterruptedException e) {}
		}
	}
	
	public static void main(String args[])
	{
		Producer producer = new Producer();
		new Thread(producer).start();
		
		Consumer consumer = new Consumer("One", producer);
		new Thread(consumer).start();
		consumer = new Consumer("Two", producer);
		new Thread(consumer).start();
	}
}
