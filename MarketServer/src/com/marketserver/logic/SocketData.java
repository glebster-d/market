package com.marketserver.logic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

// Class representing single socket entity
public class SocketData {
	
	private Socket socket;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;	
	
	public SocketData(Socket socket){
		
		this.socket = socket;
		
		try {			
			inputStream = new DataInputStream(socket.getInputStream());
			outputStream = new DataOutputStream(socket.getOutputStream());				
		} 
		catch (IOException e) {
			
			System.out.println("IOException from SocketData constructor. Message: " + e.getMessage());
		}
				
	}// ctor

	public Socket getSocket() {
		
		return socket;
		
	}// getSocket
	
	public DataInputStream getInputStream() {
		
		return inputStream;
		
	}// getInputStream

	public DataOutputStream getOutputStream() {
		
		return outputStream;
		
	}// getOutputStream	

}// class
