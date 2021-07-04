package com.marketclient.logic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;

import com.marketclient.main.CommonClient;

public class Client {
	
	private Socket socket;
	private DataInputStream fromServerInputStream;
	private DataOutputStream toServerOutputStream;	
	private boolean isConnected = false;
	
	// Starting client
	public void startClient() {

		// If client not connected try to get connection.
		if (!isConnected) {

			try {
				socket = new Socket("localhost", CommonClient.SERVER_PORT);
				fromServerInputStream = new DataInputStream(socket.getInputStream());
				toServerOutputStream = new DataOutputStream(socket.getOutputStream());				
				isConnected = true;
			} 
			catch (UnknownHostException e) {

				closeConnection();
				System.out.println("UnknownHostException from StartClient method. Message: " + e.getMessage());
			} 
			catch (IOException e) {

				closeConnection();
				System.out.println("IOException from StartClient method. Message: " + e.getMessage());
			}
		}

	}// startClient

	// Return if connected
	public boolean isConnected() {

		return isConnected;

	}// isConnected

	// Sending JSON object to server
	public void sendToServer(JSONObject object) {

		// If client connected and received object not null, try to send object to
		// server
		if (isConnected) {

			if (object != null) {

				if (toServerOutputStream != null) {

					try {
						toServerOutputStream.writeUTF(object.toJSONString());
						toServerOutputStream.flush();
					} 
					catch (IOException e) {

						System.out.println("IO Exception from SendToServer method. Message: " + e.getMessage());
					}
				}
			}
		}

	} // sendToServer

	// Receive response from server
	public String receiveFromServer() {

		String object = null;

		if (isConnected) {

			try {
				object = fromServerInputStream.readUTF();
			} 
			catch (IOException e) {

				System.out.println("IOException from RecieveFromServer method. Message: " + e.getMessage());
			}
		}

		return object;

	}// recieveFromServer	

	// Closing socket and disconnect
	public void closeConnection() {

		if (socket != null) {

			try {
				socket.close();

			} catch (IOException e) {

				System.out.println("IO Exception from CloseConnection method. Message: " + e.getMessage());
			}
		}

		isConnected = false;

	}// closeConnection

}// class
