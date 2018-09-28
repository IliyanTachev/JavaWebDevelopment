package org.elsys.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient{
	public static void main(String[] args) {
		Socket client1 =  null;
		try {
			client1 = new Socket("localhost", 10000);
			PrintWriter out = new PrintWriter(client1.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(client1.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

			Runnable runnable = new ClientThread(in, out, stdIn);
			Thread t = new Thread(runnable);
			t.start();
			while(true) {
				System.out.println(in.readLine());
			}
		
		} catch (Throwable t) {
			System.out.println(t.getMessage());
		}
		
	}
}
