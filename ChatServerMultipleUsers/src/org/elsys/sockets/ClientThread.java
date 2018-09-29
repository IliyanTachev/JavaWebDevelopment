package org.elsys.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable{
	PrintWriter out;
	BufferedReader in;
	BufferedReader stdIn;
	Scanner scanner = null;
	
	public ClientThread(BufferedReader in, PrintWriter out, BufferedReader stdIn) {
		this.in = in;
		this.out = out;
		this.stdIn = stdIn;
		scanner = new Scanner(System.in);
	}
	
	@Override
	public void run() {
		try {
			String userInput;
			String username;
			System.out.print("Enter your username: ");
			username = scanner.nextLine();
			while((userInput = stdIn.readLine()) != null)
			{
				if(userInput.equals("exit")) {
					out.println(username + " has left the server... ");
					break;
				}
				
				out.println(username + ": " + userInput);
			}
		
		} catch (Throwable t) {
			System.out.println(t.getMessage());
		} 
	}
}