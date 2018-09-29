package org.elsys.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		List<PrintWriter> outs = new ArrayList<PrintWriter>();
		
			try {
				serverSocket = new ServerSocket(10000);
				int i=0;
				while(true) {
					Socket clientSocket = serverSocket.accept();
					System.out.println("client connected from " + clientSocket.getInetAddress());
					BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
					outs.add(out);
					Runnable runnable = new ClientHandler(in, outs);
					Thread t = new Thread(runnable);
					t.start();
				}
			
			} catch (Throwable t) {
				System.out.println(t.getMessage());
			}
	}
}
