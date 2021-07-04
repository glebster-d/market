package com.marketserver.logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

// Singleton server class
public class Server {
		
	private final static Server instance = new Server();

	private ServerSocket server = null;	
	private SimpleDateFormat format;
	private boolean serverStarted;
	
	// Private constructor 
	private Server() {

		format = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");
		
		try {
			server = new ServerSocket(CommonServer.SERVER_PORT);			
		} 
		catch (IOException e) {

			System.out.println("[" + format.format(new Date()) + "]" + 
								"IO Exception from Server constructor. Message: " + e.getMessage());			
		}
	
	}// ctor

	public static Server getInstance() {

		return instance;

	}// getInstance

	// Starting server
	public void startServer() {

		serverStarted = true;
		
		while (serverStarted) {
			
			System.out.println("[" + format.format(new Date()) + "]" + 
								" Server started! Listen to port: " + CommonServer.SERVER_PORT);
			
			try {				
				final Socket socket = server.accept();	
				
				System.out.println("[" + format.format(new Date()) + "]" + " Client connected from: " + socket.getInetAddress());
				
				SocketData socketData = new SocketData(socket);				
				ServerThread thread = new ServerThread(socketData);				
				thread.start();						
			} 
			catch (IOException e) {

				System.out.println("[" + format.format(new Date()) + "]" + 
									"IO Exception from StartServer method. Message: " + e.getMessage());				
			}
		}
	}// startServer		
	
}// class
