package com.marketserver.main;

import com.marketserver.logic.Server;

public class Program {

	public static void main(String[] args) {

		try {
			
			Server.getInstance().startServer();
		} 
		catch (Exception e) {
			
			System.out.println("Exception from server Main. Message: " + e.getMessage());
		}
	}

}// class