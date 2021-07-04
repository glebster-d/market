package com.marketclient.main;

import java.awt.EventQueue;
import javax.swing.JOptionPane;

import com.marketclient.gui.ClientMainUI;

public class Program {
		
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {

				try {									
					ClientMainUI mainView = new ClientMainUI();
					
					if(mainView.isServerRunning()) {
					
						mainView.login();
					}
					else {
						JOptionPane.showMessageDialog(mainView, CommonClient.SERVER_NOT_WORKING, "Error", JOptionPane.WARNING_MESSAGE);
						mainView.dispose();
					}					
				} 
				catch (Exception e) {
					
					System.out.println("Exception from client Main. Message: " + e.getMessage());
				}
			}
		});
	}
}// class
