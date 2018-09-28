package org.elsys.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ClientHandler implements Runnable{
	PrintWriter out;
	BufferedReader in;
	
	public ClientHandler(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
	}
	
	@Override
	public void run() {
		String inputLine;
			
		try {
			while((inputLine = in.readLine()) != null) {
				out.println(inputLine);
				System.out.println("Message was successfully sent.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
