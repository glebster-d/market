package com.marketclient.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.text.PlainDocument;

import org.json.simple.JSONObject;

import com.marketclient.logic.ClientMainLogic;
import com.marketclient.logic.Employee;
import com.marketclient.main.CommonClient;

// Dialog for adding new employee
public class AddEmployeeDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 2402565481552961227L;
	private static final int NUMBER_OF_FIELDS_TO_ENTER = 11;

	private ClientMainLogic logic;
	private ClientMainUI parent;
	private Employee newEmployee;

	private JPanel contentPanel;
	private JPanel buttonPanel;

	private JTextField idTextField;
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField workIdTextField;
	private JTextField telephoneTextField;
	private JTextField accountTextField;
	private JTextField passwordTextField;

	private JComboBox<String> positionComboBox;
	private int numberOfEntrances = 0;

	public AddEmployeeDialog(ClientMainUI parent) {

		super(parent, Dialog.ModalityType.APPLICATION_MODAL);
		this.parent = parent;
		this.logic = parent.getLogic();
		newEmployee = new Employee();

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

		String[] jobPositions = new String[] { CommonClient.JOB_POSITION_MANAGER, CommonClient.JOB_POSITION_SELLER,
				CommonClient.JOB_POSITION_CASHIER };

		contentPanel = new JPanel();
		contentPanel.setBackground(new Color(204, 255, 153));
		contentPanel.setLayout(null);

		JLabel firstNameLabel = new JLabel(CommonClient.FIRSTNAME + ":");
		firstNameLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		firstNameLabel.setBounds(29, 40, 86, 16);
		contentPanel.add(firstNameLabel);

		firstNameTextField = new JTextField();
		firstNameLabel.setLabelFor(firstNameTextField);
		firstNameTextField.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		firstNameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		firstNameTextField.setBounds(127, 34, 140, 28);
		firstNameTextField.setColumns(1);
		PlainDocument firstNameDoc = (PlainDocument) firstNameTextField.getDocument();
		firstNameDoc.setDocumentFilter(new TextLengthFilter(20));
		contentPanel.add(firstNameTextField);

		JLabel lastNameLabel = new JLabel(CommonClient.LASTNAME + ":");
		lastNameLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		lastNameLabel.setBounds(299, 40, 86, 16);
		contentPanel.add(lastNameLabel);

		lastNameTextField = new JTextField();
		lastNameLabel.setLabelFor(lastNameTextField);
		lastNameTextField.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		lastNameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		lastNameTextField.setBounds(397, 34, 140, 28);
		lastNameTextField.setColumns(1);
		PlainDocument lastNameDoc = (PlainDocument) lastNameTextField.getDocument();
		lastNameDoc.setDocumentFilter(new TextLengthFilter(20));
		contentPanel.add(lastNameTextField);

		JLabel idLabel = new JLabel(CommonClient.ID + ":");
		idLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		idLabel.setBounds(29, 105, 86, 16);
		contentPanel.add(idLabel);

		idTextField = new JTextField();
		idLabel.setLabelFor(idTextField);
		idTextField.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		idTextField.setHorizontalAlignment(SwingConstants.CENTER);
		idTextField.setBounds(127, 99, 140, 28);
		idTextField.setColumns(1);
		PlainDocument idDoc = (PlainDocument) idTextField.getDocument();
		idDoc.setDocumentFilter(new TextLengthFilter(8));
		contentPanel.add(idTextField);

		JLabel workIdLabel = new JLabel(CommonClient.WORK_ID + ":");
		workIdLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		workIdLabel.setBounds(299, 105, 86, 16);
		contentPanel.add(workIdLabel);

		workIdTextField = new JTextField();
		workIdLabel.setLabelFor(workIdTextField);
		workIdTextField.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		workIdTextField.setHorizontalAlignment(SwingConstants.CENTER);
		workIdTextField.setBounds(397, 99, 140, 28);
		workIdTextField.setColumns(1);
		PlainDocument workIdDoc = (PlainDocument) workIdTextField.getDocument();
		workIdDoc.setDocumentFilter(new TextLengthFilter(8));
		contentPanel.add(workIdTextField);

		JLabel telephoneLabel = new JLabel(CommonClient.TELEPHONE + ":");
		telephoneLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		telephoneLabel.setBounds(29, 170, 86, 16);
		contentPanel.add(telephoneLabel);

		telephoneTextField = new JTextField();
		telephoneLabel.setLabelFor(telephoneTextField);
		telephoneTextField.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		telephoneTextField.setHorizontalAlignment(SwingConstants.CENTER);
		telephoneTextField.setBounds(127, 164, 140, 28);
		telephoneTextField.setColumns(1);
		PlainDocument telephoneDoc = (PlainDocument) telephoneTextField.getDocument();
		telephoneDoc.setDocumentFilter(new TextLengthFilter(12));
		contentPanel.add(telephoneTextField);

		JLabel accountLabel = new JLabel(CommonClient.ACCOUNT + ":");
		accountLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		accountLabel.setBounds(299, 170, 86, 16);
		contentPanel.add(accountLabel);

		accountTextField = new JTextField();
		accountLabel.setLabelFor(accountTextField);
		accountTextField.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		accountTextField.setHorizontalAlignment(SwingConstants.CENTER);
		accountTextField.setBounds(397, 164, 140, 28);
		accountTextField.setColumns(1);
		PlainDocument accountDoc = (PlainDocument) accountTextField.getDocument();
		accountDoc.setDocumentFilter(new TextLengthFilter(12));
		contentPanel.add(accountTextField);

		JLabel positionLabel = new JLabel(CommonClient.POSITION + ":");
		positionLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		positionLabel.setBounds(163, 234, 86, 16);
		contentPanel.add(positionLabel);

		positionComboBox = new JComboBox<String>();
		positionLabel.setLabelFor(positionComboBox);
		positionComboBox.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		positionComboBox.setModel(new DefaultComboBoxModel<String>(jobPositions));
		positionComboBox.setBounds(272, 229, 145, 26);
		contentPanel.add(positionComboBox);

		JLabel passwordLabel = new JLabel(CommonClient.PASSWORD + ":");
		passwordLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		passwordLabel.setBounds(163, 285, 75, 16);
		contentPanel.add(passwordLabel);

		passwordTextField = new JTextField();
		passwordLabel.setLabelFor(passwordTextField);
		passwordTextField.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		passwordTextField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordTextField.setBounds(277, 279, 135, 28);
		passwordTextField.setColumns(1);
		PlainDocument passwordDoc = (PlainDocument) passwordTextField.getDocument();
		passwordDoc.setDocumentFilter(new TextLengthFilter(30));
		contentPanel.add(passwordTextField);

	}// setContentPanelComponents

	// Initializing and setting buttons to button panel
	private void setButtonPanelComponents() {

		buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(204, 255, 153));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JButton addButton = new JButton(CommonClient.ADD_EMPLOYEE);
		addButton.setMultiClickThreshhold(2000);
		addButton.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addButton.setActionCommand(CommonClient.ADD_EMPLOYEE);
		addButton.addActionListener(this);
		buttonPanel.add(addButton);

		JButton cancelButton = new JButton(CommonClient.CANCEL);
		cancelButton.setMultiClickThreshhold(2000);
		cancelButton.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelButton.setActionCommand(CommonClient.CANCEL);
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);

	}// setButtonPanelComponents

	// Set dialog parameters
	private void setJDialogParams() {

		setBounds(100, 100, 614, 428);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(parent);
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle(CommonClient.ADD_EMPLOYEE);

	}// setJDialogParams

	// Make dialog visible
	public void showDialog() {

		setVisible(true);

	}// showDialog

	// Button click listener override method
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals(CommonClient.CANCEL)) {

			dispose();
		} 
		else {

			String descr = firstNameTextField.getText() + " " + lastNameTextField.getText() + " was added.";
			JSONObject log = logic.getJsonManager().createNewLogJSONObject(CommonClient.ADD_EMPLOYEE, descr);
			JSONObject employee = createNewEmployeeJSONObject();
			JSONObject login = createNewLoginJSONObject();

			if (isAllFieldsNotEmpty()) {

				if (sendToServer(employee) && sendToServer(login) && sendToServer(log)) {

					logic.getEmployeesList().add(newEmployee);
					logic.refreshTableListOnAdding(newEmployee);
					cleanTextFileds();

					JOptionPane.showMessageDialog(this, CommonClient.EMPLOYEE_ADDED_MESSAGE, "",
								JOptionPane.INFORMATION_MESSAGE);					
				} 
				else {
					JOptionPane.showMessageDialog(this, CommonClient.EMPLOYEE_NOT_ADDED_MESSAGE, "Error",
							JOptionPane.WARNING_MESSAGE);
				}
			} 
			else {
				JOptionPane.showMessageDialog(this, CommonClient.EMPTY_FIELDS_MESSAGE, "", JOptionPane.WARNING_MESSAGE);
			}
		}

	}// actionPerformed

	// Cleaning text fields
	private void cleanTextFileds() {

		idTextField.setText("");
		firstNameTextField.setText("");
		lastNameTextField.setText("");
		workIdTextField.setText("");
		telephoneTextField.setText("");
		accountTextField.setText("");
		passwordTextField.setText("");

	}// cleanTextFileds

	// Checking the empty fields
	private boolean isAllFieldsNotEmpty() {

		if (numberOfEntrances == NUMBER_OF_FIELDS_TO_ENTER) {

			return true;
		}

		numberOfEntrances = 0;

		return false;

	}// isFieldsEmpty

	// Creating JSON login object
	@SuppressWarnings("unchecked")
	private JSONObject createNewLoginJSONObject() {

		JSONObject objectToSend = new JSONObject();
		objectToSend.put(CommonClient.JSON_ROOT_TITLE, CommonClient.LOGIN_ROOT);
		objectToSend.put(CommonClient.MSG_ROOT, CommonClient.REQUEST_ADD);
		objectToSend.put(CommonClient.JSON_BRANCH_TITLE, logic.getBranchName());
		setWorkId(objectToSend);
		setPerson(objectToSend);
		setPassword(objectToSend);
		setType(objectToSend);

		return objectToSend;

	}// createNewLoginJSONObject

	// Setting value of JSON object according to choice from position combo box
	@SuppressWarnings("unchecked")
	private void setType(JSONObject objectToSend) {

		if (!positionComboBox.hasFocus()) {

			String type = positionComboBox.getSelectedItem().equals(CommonClient.JOB_POSITION_MANAGER)
					? CommonClient.LOGIN_ADMIN
					: CommonClient.LOGIN_USER;
			objectToSend.put(CommonClient.TYPE, type);
			numberOfEntrances++;
		}

	}// setType

	// Setting value of JSON object from password text field
	@SuppressWarnings("unchecked")
	private void setPassword(JSONObject objectToSend) {

		if (!passwordTextField.getText().isEmpty()) {

			objectToSend.put(CommonClient.PASSWORD, passwordTextField.getText());
			numberOfEntrances++;
		}

	}// setPassword

	// Setting value of JSON object from first name and last name text fields
	@SuppressWarnings("unchecked")
	private void setPerson(JSONObject objectToSend) {

		if (!firstNameTextField.getText().isEmpty() && !lastNameTextField.getText().isEmpty()) {

			String person = firstNameTextField.getText() + " " + lastNameTextField.getText();
			objectToSend.put(CommonClient.LOGIN_PERSON, person);
			numberOfEntrances++;
		}

	}// setPerson

	// Creating JSON object from dialog fields
	@SuppressWarnings("unchecked")
	private JSONObject createNewEmployeeJSONObject() {

		JSONObject objectToSend = new JSONObject();
		objectToSend.put(CommonClient.JSON_ROOT_TITLE, CommonClient.EMPLOYEE_ROOT);
		objectToSend.put(CommonClient.MSG_ROOT, CommonClient.REQUEST_ADD);
		objectToSend.put(CommonClient.JSON_BRANCH_TITLE, logic.getBranchName());
		setFirstName(objectToSend);
		setLastName(objectToSend);
		setTelephone(objectToSend);
		setAccount(objectToSend);
		setPosition(objectToSend);
		setWorkId(objectToSend);
		setId(objectToSend);

		return objectToSend;

	}// createJSONObject

	// Setting value of JSON object from position combo box
	@SuppressWarnings("unchecked")
	private void setPosition(JSONObject objectToSend) {

		if (!positionComboBox.hasFocus()) {

			objectToSend.put(CommonClient.POSITION, positionComboBox.getSelectedItem().toString());
			newEmployee.setPosition(positionComboBox.getSelectedItem().toString());
			numberOfEntrances++;
		}

	}// setPosition

	// Setting value of JSON object from account text field
	@SuppressWarnings("unchecked")
	private void setAccount(JSONObject objectToSend) {

		if (!accountTextField.getText().isEmpty()) {

			objectToSend.put(CommonClient.ACCOUNT, accountTextField.getText());
			newEmployee.setAccount(accountTextField.getText());
			numberOfEntrances++;
		}

	}// setAccount

	// Setting value of JSON object from telephone text field
	@SuppressWarnings("unchecked")
	private void setTelephone(JSONObject objectToSend) {

		if (!telephoneTextField.getText().isEmpty()) {

			objectToSend.put(CommonClient.TELEPHONE, telephoneTextField.getText());
			newEmployee.setTelephone(telephoneTextField.getText());
			numberOfEntrances++;
		}

	}// setTelephone

	// Setting value of JSON object from last name text field
	@SuppressWarnings("unchecked")
	private void setLastName(JSONObject objectToSend) {

		if (!lastNameTextField.getText().isEmpty()) {

			objectToSend.put(CommonClient.LASTNAME, lastNameTextField.getText());
			newEmployee.setLastName(lastNameTextField.getText());
			numberOfEntrances++;
		}

	}// setLastName

	// Setting value of JSON object from first name text field
	@SuppressWarnings("unchecked")
	private void setFirstName(JSONObject objectToSend) {

		if (!firstNameTextField.getText().isEmpty()) {

			objectToSend.put(CommonClient.FIRSTNAME, firstNameTextField.getText());
			newEmployee.setFirstName(firstNameTextField.getText());
			numberOfEntrances++;
		}

	}// setFirstName

	// Setting value of JSON object from id text field
	@SuppressWarnings("unchecked")
	private void setId(JSONObject objectToSend) {

		if (!idTextField.getText().isEmpty()) {

			objectToSend.put(CommonClient.ID, idTextField.getText());
			newEmployee.setId(idTextField.getText());
			numberOfEntrances++;
		}

	}// setId

	// Setting value of JSON object from work id text field
	@SuppressWarnings("unchecked")
	private void setWorkId(JSONObject objectToSend) {

		if (!workIdTextField.getText().isEmpty()) {

			objectToSend.put(CommonClient.WORK_ID, workIdTextField.getText());
			newEmployee.setWorkId(workIdTextField.getText());
			numberOfEntrances++;
		}

	}// setWorkId

	// Send JSON object to server
	private boolean sendToServer(JSONObject objectToSend) {

		boolean isSent = false;

		logic.getClient().sendToServer(objectToSend);
		String response = logic.getClient().receiveFromServer();

		if (response != null) {

			JSONObject objectFromServer = logic.getJsonManager().getJSONObjectFromResponse(response);

			if (objectFromServer != null) {

				String message = (String) objectFromServer.get(CommonClient.MSG_ROOT);

				if (message.equals(CommonClient.RESPONSE_OK)) {

					isSent = true;
				}
			}
		}

		return isSent;

	}// sendToServer

}// class
