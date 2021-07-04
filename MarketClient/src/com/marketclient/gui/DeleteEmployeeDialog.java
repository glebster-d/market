package com.marketclient.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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
import com.marketclient.main.CommonClient;

// Dialog for employee deleting
public class DeleteEmployeeDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 7925684407871537091L;

	private ClientMainLogic logic;
	private ClientMainUI parent;

	private JPanel contentPanel;
	private JPanel buttonPanel;

	private JTextField workIdTextField;
	private JTextField idTextField;

	public DeleteEmployeeDialog(ClientMainUI parent) {

		super(parent, Dialog.ModalityType.APPLICATION_MODAL);
		this.parent = parent;
		this.logic = parent.getLogic();
		
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
		contentPanel.setBackground(new Color(255, 255, 102));
		contentPanel.setLayout(null);

		JLabel workIdLabel = new JLabel("Work ID:");
		workIdLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		workIdLabel.setBounds(32, 50, 122, 28);
		contentPanel.add(workIdLabel);

		workIdTextField = new JTextField();
		workIdLabel.setLabelFor(workIdTextField);
		workIdTextField.setHorizontalAlignment(SwingConstants.CENTER);
		workIdTextField.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		workIdTextField.setBounds(156, 51, 140, 28);
		workIdTextField.setColumns(1);
		PlainDocument workIdDoc = (PlainDocument) workIdTextField.getDocument();
		workIdDoc.setDocumentFilter(new TextLengthFilter(8));
		contentPanel.add(workIdTextField);

		JLabel idLabel = new JLabel("ID:");
		idLabel.setDisabledIcon(null);
		idLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		idLabel.setBounds(32, 137, 122, 28);
		contentPanel.add(idLabel);

		idTextField = new JTextField();
		idLabel.setLabelFor(idTextField);
		idTextField.setHorizontalAlignment(SwingConstants.CENTER);
		idTextField.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		idTextField.setBounds(156, 138, 140, 28);
		idTextField.setColumns(1);
		PlainDocument idDoc = (PlainDocument) idTextField.getDocument();
		idDoc.setDocumentFilter(new TextLengthFilter(8));
		contentPanel.add(idTextField);

	}// setContentPanelComponents

	// Initializing and setting buttons to button panel
	private void setButtonPanelComponents() {

		buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(255, 255, 102));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JButton deleteButton = new JButton(CommonClient.DELETE_EMPLOYEE);
		deleteButton.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		deleteButton.setMultiClickThreshhold(2000);
		deleteButton.setActionCommand(CommonClient.DELETE_EMPLOYEE);
		deleteButton.addActionListener(this);
		buttonPanel.add(deleteButton);

		JButton cancelButton = new JButton(CommonClient.CANCEL);
		cancelButton.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelButton.setMultiClickThreshhold(2000);
		cancelButton.setActionCommand(CommonClient.CANCEL);
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);

	}// setButtonPanelComponents

	// Set dialog parameters
	private void setJDialogParams() {

		setBounds(100, 100, 341, 362);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(parent);
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle(CommonClient.DELETE_EMPLOYEE);

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

			JSONObject object = createJSONObject();

			if (!isFieldsEmpty()) {

				sendToServer(object);
				clearTextFields();
			} 
			else {
				JOptionPane.showMessageDialog(this, CommonClient.EMPTY_FIELDS_MESSAGE, "", JOptionPane.WARNING_MESSAGE);
			}
		}

	}// actionPerformed

	private void clearTextFields() {

		workIdTextField.setText("");
		idTextField.setText("");

	}// clearTextFields

	// Checking for empty fields
	private boolean isFieldsEmpty() {

		return workIdTextField.getText().isEmpty() && idTextField.getText().isEmpty();

	}// isFieldsEmpty

	// Send JSON object to server
	private void sendToServer(JSONObject obj) {

		logic.getClient().sendToServer(obj);
		String response = logic.getClient().receiveFromServer();

		if (response != null) {

			JSONObject objectFromServer = logic.getJsonManager().getJSONObjectFromResponse(response);

			if (objectFromServer != null) {

				String message = (String) objectFromServer.get(CommonClient.MSG_ROOT);

				if (message.equals(CommonClient.RESPONSE_OK)) {

					JOptionPane.showMessageDialog(this, CommonClient.EMPLOYEE_DELETED_MESSAGE, "",
							JOptionPane.INFORMATION_MESSAGE);
				}
				else {

					JOptionPane.showMessageDialog(this, CommonClient.EMPLOYEE_NOT_DELETED_MESSAGE, "Error",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		}		

	}// sendToServer

	// Create JSON object from dialog fields
	@SuppressWarnings("unchecked")
	private JSONObject createJSONObject() {

		JSONObject objectToSend = new JSONObject();
		objectToSend.put(CommonClient.MSG_ROOT, CommonClient.REQUEST_DELETE);
		objectToSend.put(CommonClient.JSON_ROOT_TITLE, CommonClient.EMPLOYEE_ROOT);
		objectToSend.put(CommonClient.JSON_BRANCH_TITLE, logic.getBranchName());
		setWorkId(objectToSend);
		setId(objectToSend);

		return objectToSend;

	}// createJSONObject

	// Setting employee id from text field to JSON object
	@SuppressWarnings("unchecked")
	private void setId(JSONObject empData) {

		if (!idTextField.getText().isEmpty()) {

			empData.put(CommonClient.ID, idTextField.getText());
		}

	}// setId

	// Setting employee work id from text field to JSON object
	@SuppressWarnings("unchecked")
	private void setWorkId(JSONObject empData) {

		if (!workIdTextField.getText().isEmpty()) {

			empData.put(CommonClient.WORK_ID, workIdTextField.getText());
		}

	}// setWorkId

}// class
