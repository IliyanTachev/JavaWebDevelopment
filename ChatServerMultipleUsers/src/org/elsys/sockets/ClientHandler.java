package org.elsys.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable{
	BufferedReader in;
	List<PrintWriter> outs;
	
	public ClientHandler(BufferedReader in, List<PrintWriter> outs) {
		this.in = in;
		this.outs = outs;
	}
	
	@Override
	public void run() {
		String inputLine;
			
		try {
			while((inputLine = in.readLine()) != null) {
				for(int i=0;i<outs.size();i++) {
					outs.get(i).println(inputLine);
				}
//				out.println(inputLine);
				System.out.println("Message was successfully sent.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
