package com.marketclient.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.json.simple.JSONObject;

import com.marketclient.logic.ClientMainLogic;
import com.marketclient.main.CommonClient;

// Dialog for login operation
public class LoginDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 6154976763750983181L;
	
	private ClientMainLogic logic;
		
	private JPanel contentPanel;
	private JPanel buttonPanel;

	private JTextField workIdTextField;
	private JPasswordField passwordField;

	private JButton loginButton;
	private JButton cancelButton;

	private boolean isAdmin;
	private boolean isAllowedToLogin;
	private boolean isCancelled;
	
	private String workId = "";
	private String branchName = "";
	private String clientUserName = "";
		
	public LoginDialog(ClientMainUI parent) {
			
		super(parent, Dialog.ModalityType.APPLICATION_MODAL);
		this.logic = parent.getLogic();

		isAllowedToLogin = false;
		isCancelled = true;

		initLookAndFeel();
		setContentPanelComponents();
		setButtonPanelComponents();
		setJDialogParams();

	}// ctor

	// Setting "Nimbus" look and fell
	private void initLookAndFeel() {

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {

				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {

			System.out.println("Exception. Using the default look and feel.");
		}

	}// initLookAndFeel

	// Initializing and setting content fields to content panel
	private void setContentPanelComponents() {

		contentPanel = new JPanel();
		contentPanel.setLayout(null);

		JLabel usernameLabel = new JLabel(CommonClient.WORK_ID + ":");
		usernameLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		usernameLabel.setBounds(23, 36, 92, 31);
		contentPanel.add(usernameLabel);

		workIdTextField = new JTextField();
		workIdTextField.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		usernameLabel.setLabelFor(workIdTextField);
		workIdTextField.setBounds(125, 37, 224, 31);
		contentPanel.add(workIdTextField);
		workIdTextField.setColumns(10);

		JLabel passwordLabel = new JLabel(CommonClient.PASSWORD + ":");
		passwordLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		passwordLabel.setBounds(23, 97, 92, 31);
		contentPanel.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font(CommonClient.FONT, Font.PLAIN, 14));
		passwordLabel.setLabelFor(passwordField);
		passwordField.setBounds(125, 93, 224, 31);
		contentPanel.add(passwordField);

	}// setPanelComponents

	// Initializing and setting buttons to button panel
	private void setButtonPanelComponents() {

		buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 155, 393, 38);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		loginButton = new JButton(CommonClient.LOGIN);
		loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginButton.setFont(new Font(CommonClient.FONT, Font.BOLD, 18));
		loginButton.setActionCommand(CommonClient.LOGIN);
		loginButton.setMultiClickThreshhold(2000);
		loginButton.addActionListener(this);		
		buttonPanel.add(loginButton);

		cancelButton = new JButton(CommonClient.CANCEL);
		cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelButton.setFont(new Font(CommonClient.FONT, Font.BOLD, 18));
		cancelButton.setActionCommand(CommonClient.CANCEL);
		cancelButton.setMultiClickThreshhold(2000);
		cancelButton.addActionListener(this);		
		buttonPanel.add(cancelButton);

	}// setButtonPanelComponents

	// Setting dialog parameters
	private void setJDialogParams() {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();	
		
		setSize(400, 230);
		setLocation(screenSize.width / 2 - this.getSize().width / 2, screenSize.height / 2 - this.getSize().height / 2);		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle(CommonClient.DIALOG_LOGIN_TITLE);
		setAlwaysOnTop(true);
		setResizable(false);

	}// setJDialogParams

	// Make dialog visible
	public void showDialog() {

		setVisible(true);

	}// showPanel

	// Button click listener override method
	@Override
	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {

		case CommonClient.LOGIN:

			loginOperation();
			
			if (isAllowedToLogin) {
				
				logic.setBranchName(branchName);
				logic.setWorkId(workId);
				logic.setUserName(clientUserName);
				logic.setConnectedUser(isAdmin);
				isCancelled = false;
				setVisible(false);
			}
			
			break;

		case CommonClient.CANCEL:
			setVisible(false);		
			break;
		}

	}// actionPerformed

	// Indicate if Cancel button was pressed
	public boolean isCancelled(){
		
		return isCancelled;
	
	}// isCancelled
	
	// Checking the values from login fields with data on server and
	// starting client application
	private void loginOperation() {

		workId = workIdTextField.getText();
		String password = new String(passwordField.getPassword());

		if (!workId.isEmpty() && !password.isEmpty()) {
			
			JSONObject objectToServer = createLoginJSONObject(workId, password);		
			requestLoginFromServer(objectToServer);	
			
			workIdTextField.setText("");
			passwordField.setText("");
		} 
		else {
			JOptionPane.showMessageDialog(this, CommonClient.EMPTY_FIELDS_MESSAGE, "Warning", JOptionPane.WARNING_MESSAGE);
		}

	}// loginOperation
	
	// Connect to server via client and send JSON object to server
	private void requestLoginFromServer(JSONObject objectToServer) {
		
		if (logic.getClient().isConnected()) {
								
			logic.getClient().sendToServer(objectToServer);
			String response = (String) logic.getClient().receiveFromServer();

			if (response != null) {				
				
				JSONObject objectFromServer = logic.getJsonManager().getJSONObjectFromResponse(response);				

				if(objectFromServer != null){
					
					String message = (String) objectFromServer.get(CommonClient.MSG_ROOT);
					
					if(!message.equals(CommonClient.RESPONSE_BAD)) {
						
						isAdmin = message.equals(CommonClient.RESPONSE_ADMIN);						
						branchName = (String) objectFromServer.get(CommonClient.JSON_BRANCH_TITLE);
						clientUserName = (String) objectFromServer.get(CommonClient.LOGIN_PERSON);
						isAllowedToLogin = true;
					}
					else {						
						JOptionPane.showMessageDialog(this, CommonClient.USER_NOT_EXIST_MESSAGE, "Error", JOptionPane.WARNING_MESSAGE);						
					}				
				}						
			}
		}
		else{			
			JOptionPane.showMessageDialog(this, CommonClient.NO_CONNECTION_MESSAGE, "Error", JOptionPane.WARNING_MESSAGE);
		}	
		
	}// connectToServerForLogin		

	// Create JSON object for login operation 
	@SuppressWarnings("unchecked")
	private JSONObject createLoginJSONObject(String workId, String password){
				
		JSONObject object = new JSONObject();			
		object.put(CommonClient.JSON_ROOT_TITLE, CommonClient.LOGIN_ROOT);
		object.put(CommonClient.MSG_ROOT, CommonClient.REQUEST_READ);	
		object.put(CommonClient.WORK_ID, workId);
		object.put(CommonClient.PASSWORD, password);
		
		return object;
		
	}// createLoginJSONObject

}// class
