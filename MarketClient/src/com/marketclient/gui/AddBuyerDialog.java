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
import com.marketclient.logic.NewBuyer;
import com.marketclient.main.CommonClient;

// Add new buyer dialog
public class AddBuyerDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 5722851537917710776L;
	
	private static final int NUMBER_OF_FIELDS_TO_ENTER = 4;

	private ClientMainLogic logic;
	private ClientMainUI parent;
	private NewBuyer newBuyer;

	private JPanel contentPanel;
	private JPanel buttonPanel;

	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField telephoneTextField;
	private JTextField idTextField;

	private Color background;
	private int numberOfEntrances = 0;

	public AddBuyerDialog(ClientMainUI parent) {

		super(parent, Dialog.ModalityType.APPLICATION_MODAL);
		this.parent = parent;
		this.logic = parent.getLogic();
		this.background = new Color(175, 238, 238);
		newBuyer = new NewBuyer();

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
		contentPanel.setBackground(background);

		JLabel firstNameLable = new JLabel(CommonClient.FIRSTNAME + ":");
		firstNameLable.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		firstNameLable.setBounds(33, 29, 89, 14);
		contentPanel.add(firstNameLable);

		firstNameTextField = new JTextField();
		firstNameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		firstNameTextField.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		firstNameLable.setLabelFor(firstNameTextField);
		firstNameTextField.setBounds(165, 28, 166, 28);
		firstNameTextField.setColumns(1);
		PlainDocument firstNameDoc = (PlainDocument) firstNameTextField.getDocument();
		firstNameDoc.setDocumentFilter(new TextLengthFilter(20));
		contentPanel.add(firstNameTextField);

		JLabel lastNameLabel = new JLabel(CommonClient.LASTNAME + ":");
		lastNameLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		lastNameLabel.setBounds(33, 80, 89, 14);
		contentPanel.add(lastNameLabel);

		lastNameTextField = new JTextField();
		lastNameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		lastNameLabel.setLabelFor(lastNameTextField);
		lastNameTextField.setBounds(165, 79, 166, 28);
		lastNameTextField.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		lastNameTextField.setColumns(1);
		PlainDocument lastNameDoc = (PlainDocument) lastNameTextField.getDocument();
		lastNameDoc.setDocumentFilter(new TextLengthFilter(20));
		contentPanel.add(lastNameTextField);

		JLabel telephoneLabel = new JLabel(CommonClient.TELEPHONE + ":");
		telephoneLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		telephoneLabel.setBounds(33, 184, 89, 19);
		contentPanel.add(telephoneLabel);

		telephoneTextField = new JTextField();
		telephoneTextField.setHorizontalAlignment(SwingConstants.CENTER);
		telephoneTextField.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		telephoneLabel.setLabelFor(telephoneTextField);
		telephoneTextField.setBounds(165, 183, 166, 28);
		telephoneTextField.setColumns(1);
		PlainDocument telephoneDoc = (PlainDocument) telephoneTextField.getDocument();
		telephoneDoc.setDocumentFilter(new TextLengthFilter(12));
		contentPanel.add(telephoneTextField);

		JLabel idLabel = new JLabel(CommonClient.ID + ":");
		idLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		idLabel.setBounds(33, 131, 46, 14);
		contentPanel.add(idLabel);

		idTextField = new JTextField();
		idTextField.setHorizontalAlignment(SwingConstants.CENTER);
		idTextField.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		idLabel.setLabelFor(idTextField);
		idTextField.setBounds(165, 130, 166, 28);
		idTextField.setColumns(1);
		PlainDocument idDoc = (PlainDocument) idTextField.getDocument();
		idDoc.setDocumentFilter(new TextLengthFilter(15));
		contentPanel.add(idTextField);

	}// setContentPanelComponents

	// Initializing and setting buttons to button panel
	private void setButtonPanelComponents() {

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setBackground(background);

		JButton addButton = new JButton(CommonClient.ADD_BUYER);
		addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addButton.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		addButton.setActionCommand(CommonClient.ADD_BUYER);
		addButton.setMultiClickThreshhold(2000);
		addButton.addActionListener(this);
		buttonPanel.add(addButton);

		JButton cancelButton = new JButton(CommonClient.CANCEL);
		cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelButton.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		cancelButton.setActionCommand(CommonClient.CANCEL);
		cancelButton.setMultiClickThreshhold(2000);
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);

	}// setButtonPanelComponents

	// Setting dialog parameters
	private void setJDialogParams() {

		setBounds(100, 100, 392, 339);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(parent);
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle(CommonClient.ADD_BUYER);

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
			JSONObject buyer = createNewBuyerJSONObject();
			String descr = firstNameTextField.getText() + " " + lastNameTextField.getText() + " was added.";
			JSONObject log = logic.getJsonManager().createNewLogJSONObject(CommonClient.ADD_BUYER, descr);

			if (isAllFieldsNotEmpty()) {

				if (sendToServer(buyer) && sendToServer(log)) {

					logic.refreshTableListOnAdding(newBuyer);
					logic.getBuyersList().add(newBuyer);
					cleanTextFileds();

					JOptionPane.showMessageDialog(this, CommonClient.NEW_BUYER_ADDED_MESSAGE, "",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, CommonClient.NEW_BUYER_NOT_ADDED_MESSAGE, "Error",
							JOptionPane.WARNING_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, CommonClient.EMPTY_FIELDS_MESSAGE, "", JOptionPane.WARNING_MESSAGE);
			}
		}

	}// actionPerformed

	// Cleaning text fields
	private void cleanTextFileds() {

		idTextField.setText("");
		firstNameTextField.setText("");
		lastNameTextField.setText("");
		telephoneTextField.setText("");
		numberOfEntrances = 0;

	}// cleanTextFileds

	// Checking the empty fields
	private boolean isAllFieldsNotEmpty() {

		if (numberOfEntrances == NUMBER_OF_FIELDS_TO_ENTER) {

			return true;
		}

		numberOfEntrances = 0;

		return false;

	}// isFieldsEmpty

	// Creating JSON object from dialog fields
	@SuppressWarnings("unchecked")
	private JSONObject createNewBuyerJSONObject() {

		JSONObject objectToSend = new JSONObject();
		objectToSend.put(CommonClient.MSG_ROOT, CommonClient.REQUEST_ADD);
		objectToSend.put(CommonClient.JSON_ROOT_TITLE, CommonClient.BUYER_ROOT);
		objectToSend.put(CommonClient.TYPE, CommonClient.BUYER_TYPE_NEW);
		setFirstNameValue(objectToSend);
		setLastNameValue(objectToSend);
		setIdValue(objectToSend);
		setTelephoneValue(objectToSend);

		return objectToSend;

	}// createNewClientJSONObject

	// Getting first name
	@SuppressWarnings("unchecked")
	private void setFirstNameValue(JSONObject buyer) {

		if (!firstNameTextField.getText().isEmpty()) {

			buyer.put(CommonClient.FIRSTNAME, firstNameTextField.getText());
			newBuyer.setFirstName(firstNameTextField.getText());
			numberOfEntrances++;
		}

	}// getFirstNameValue

	// Getting last name
	@SuppressWarnings("unchecked")
	private void setLastNameValue(JSONObject buyer) {

		if (!lastNameTextField.getText().isEmpty()) {

			buyer.put(CommonClient.LASTNAME, lastNameTextField.getText());
			newBuyer.setLastName(lastNameTextField.getText());
			numberOfEntrances++;
		}

	}// getLastNameValue

	// Getting Id
	@SuppressWarnings("unchecked")
	private void setIdValue(JSONObject buyer) {

		if (!idTextField.getText().isEmpty()) {

			buyer.put(CommonClient.ID, idTextField.getText());
			newBuyer.setId(idTextField.getText());
			numberOfEntrances++;
		}

	}// getIdValue

	// Getting telephone
	@SuppressWarnings("unchecked")
	private void setTelephoneValue(JSONObject buyer) {

		if (!telephoneTextField.getText().isEmpty()) {

			buyer.put(CommonClient.TELEPHONE, telephoneTextField.getText());
			newBuyer.setTelephone(telephoneTextField.getText());
			numberOfEntrances++;
		}

	}// getTelephoneValue

	// Send JSON object to server
	private boolean sendToServer(JSONObject buyer) {

		boolean isSent = false;

		logic.getClient().sendToServer(buyer);
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
