package Clientend;

import java.io.*;
import java.net.*;

public class Clientend {

	public static void main(String[] args) throws Exception {
		File file = new File("src/resources/testFile2.txt");
		file.createNewFile();
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		
		Socket server = new Socket("10.162.5.84", 8080);
		
		InputStream netln = server.getInputStream();
		InputStream in = new DataInputStream(new BufferedInputStream(netln));
		
		byte[] buf = new byte[2048];
		int num = in.read(buf);
		while (num != -1) {
			raf.write(buf, 0, num);
			raf.skipBytes(num);
			num = in.read(buf);
		}
		
		in.close();
		raf.close();
	}

}
