package org.elsys.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		while(true) {
			try {
				serverSocket = new ServerSocket(10000);
				Socket[] users = new Socket[2];
				int i = 0;
				while(i < 2) {
					Socket clientSocket = serverSocket.accept();
					users[i] = clientSocket;
					System.out.println("client connected from " + clientSocket.getInetAddress());
					i++;
				}
				
				PrintWriter out1 = new PrintWriter(users[0].getOutputStream(), true);
				BufferedReader in1 = new BufferedReader(new InputStreamReader(users[0].getInputStream()));
				
				PrintWriter out2 = new PrintWriter(users[1].getOutputStream(), true);
				BufferedReader in2 = new BufferedReader(new InputStreamReader(users[1].getInputStream()));
						    
				Runnable runnable = new ClientHandler(in1, out2);
				Thread t1 = new Thread(runnable);
				t1.start();
				
				Runnable runnable2 = new ClientHandler(in2, out1);
				Thread t2 = new Thread(runnable2);
				t2.start();
			
			} catch (Throwable t) {
				System.out.println(t.getMessage());
			}
//				finally {
//				if (serverSocket != null && !serverSocket.isClosed()) {
//					serverSocket.close();
//				}
//			
//				System.out.println("Server closed");
//			}
			
		}
	}
}
