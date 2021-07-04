package com.marketclient.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;
import javax.swing.text.PlainDocument;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.marketclient.logic.Buyer;
import com.marketclient.logic.ClientMainLogic;
import com.marketclient.logic.NewBuyer;
import com.marketclient.logic.ReturnedBuyer;
import com.marketclient.main.CommonClient;

// Dialog for stock operations
public class StockDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = -4520482092911571632L;

	private Map<JLabel, String> updatedValues = new HashMap<>();

	private ClientMainLogic logic;
	private ClientMainUI parent;
	private Color background;
	private String operation;
	private StringBuilder descr;	
	private Buyer updatedBuyer;

	private JPanel contentPanel;
	private JPanel discountPanel;
	private JPanel buttonPanel;

	private JTextField jeansTextField;
	private JTextField cPantsTextField;
	private JTextField sPantsTextField;
	private JTextField bShirtsTextField;
	private JTextField coatsTextField;
	private JTextField tShirtsTextField;
	private JTextField sweatersTextField;
	private JTextField buyerIdTextField;

	private JLabel customPriceLabel;
	private JLabel sportPriceLabel;
	private JLabel jeansPriceLabel;
	private JLabel coatsPriceLabel;
	private JLabel sweatersPriceLabel;
	private JLabel btnShirtsPriceLabel;
	private JLabel tShirtsPriceLabel;
	
	private HashMap<String, String> pants;
	private HashMap<String, String> overgarment;

	public StockDialog(ClientMainUI parent, String operation) {

		super(parent, Dialog.ModalityType.APPLICATION_MODAL);
		this.parent = parent;
		this.operation = operation;
		this.logic = parent.getLogic();
		background = operation.equals(CommonClient.BUY) ? new Color(245, 222, 179) : new Color(144, 238, 144);
		
		initLookAndFeel();
		setContentPanelComponents();
		setButtonPanelComponents();
		disableBuyModeParams();
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

	// Set background color according to stock operation
	private void disableBuyModeParams() {

		if (operation.equals(CommonClient.BUY)) {

			customPriceLabel.setVisible(false);
			sportPriceLabel.setVisible(false);
			jeansPriceLabel.setVisible(false);
			coatsPriceLabel.setVisible(false);
			sweatersPriceLabel.setVisible(false);
			btnShirtsPriceLabel.setVisible(false);
			tShirtsPriceLabel.setVisible(false);
			discountPanel.setVisible(false);
		}

	}// disableBuyModeParams

	// Initializing and setting content fields to content panel
	private void setContentPanelComponents() {

		contentPanel = new JPanel();
		contentPanel.setLayout(null);
		contentPanel.setBackground(background);

		JPanel pantsPanel = new JPanel();
		pantsPanel.setBackground(background);
		pantsPanel.setBorder(new TitledBorder(null, CommonClient.PANTS_CATEGORY_TITLE, TitledBorder.LEADING,
				TitledBorder.TOP, new Font(CommonClient.FONT, Font.BOLD, 13), new Color(59, 59, 59)));
		pantsPanel.setBounds(16, 26, 266, 268);
		pantsPanel.setLayout(null);

		JLabel jeansLabel = new JLabel(CommonClient.STOCK_JEANS);
		jeansLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		jeansLabel.setBounds(16, 185, 46, 14);
		pantsPanel.add(jeansLabel);

		JLabel cPantsLabel = new JLabel(CommonClient.STOCK_CUSTOM_PANTS);
		cPantsLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		cPantsLabel.setBounds(16, 80, 101, 14);
		pantsPanel.add(cPantsLabel);

		JLabel sPantsLabel = new JLabel(CommonClient.STOCK_SPORT_PANTS);
		sPantsLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		sPantsLabel.setBounds(16, 134, 96, 14);
		pantsPanel.add(sPantsLabel);

		jeansTextField = new JTextField();
		jeansTextField.setHorizontalAlignment(SwingConstants.CENTER);
		jeansLabel.setLabelFor(jeansTextField);
		jeansTextField.setMaximumSize(new Dimension(1000, 1000));
		jeansTextField.setBounds(129, 178, 46, 28);
		jeansTextField.setColumns(1);
		PlainDocument jeansDoc = (PlainDocument) jeansTextField.getDocument();
		jeansDoc.setDocumentFilter(new TextLengthFilter(4));
		pantsPanel.add(jeansTextField);

		cPantsTextField = new JTextField();
		cPantsTextField.setHorizontalAlignment(SwingConstants.CENTER);
		cPantsLabel.setLabelFor(cPantsTextField);
		cPantsTextField.setMaximumSize(new Dimension(1000, 1000));
		cPantsTextField.setColumns(1);
		cPantsTextField.setBounds(129, 73, 46, 28);
		PlainDocument cPantsDoc = (PlainDocument) cPantsTextField.getDocument();
		cPantsDoc.setDocumentFilter(new TextLengthFilter(4));
		pantsPanel.add(cPantsTextField);

		sPantsTextField = new JTextField();
		sPantsTextField.setHorizontalAlignment(SwingConstants.CENTER);
		sPantsLabel.setLabelFor(sPantsTextField);
		sPantsTextField.setMaximumSize(new Dimension(1000, 1000));
		sPantsTextField.setColumns(1);
		sPantsTextField.setBounds(129, 127, 46, 28);
		PlainDocument sPantsDoc = (PlainDocument) sPantsTextField.getDocument();
		sPantsDoc.setDocumentFilter(new TextLengthFilter(4));
		pantsPanel.add(sPantsTextField);

		JPanel overgarPanel = new JPanel();
		overgarPanel.setBackground(background);
		overgarPanel.setLayout(null);
		overgarPanel.setBorder(new TitledBorder(null, CommonClient.OVERGARMENT_CATEGORY_TITLE, TitledBorder.LEADING,
				TitledBorder.TOP, new Font(CommonClient.FONT, Font.BOLD, 13), new Color(59, 59, 59)));
		overgarPanel.setBounds(294, 26, 278, 268);

		JLabel tShirtsLabel = new JLabel(CommonClient.STOCK_T_SHIRT);
		tShirtsLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		tShirtsLabel.setBounds(16, 210, 65, 14);
		overgarPanel.add(tShirtsLabel);

		JLabel coatsLabel = new JLabel(CommonClient.STOCK_COAT);
		coatsLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		coatsLabel.setBounds(16, 71, 54, 14);
		overgarPanel.add(coatsLabel);

		JLabel bShirtsLabel = new JLabel(CommonClient.STOCK_BUTTON_SHIRT);
		bShirtsLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		bShirtsLabel.setBounds(16, 164, 116, 14);
		overgarPanel.add(bShirtsLabel);

		JLabel sweatersLabel = new JLabel(CommonClient.STOCK_SWEATER);
		sweatersLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		sweatersLabel.setBounds(16, 111, 70, 14);
		overgarPanel.add(sweatersLabel);

		bShirtsTextField = new JTextField();
		bShirtsTextField.setHorizontalAlignment(SwingConstants.CENTER);
		bShirtsLabel.setLabelFor(bShirtsTextField);
		bShirtsTextField.setMaximumSize(new Dimension(1000, 1000));
		bShirtsTextField.setColumns(1);
		bShirtsTextField.setBounds(136, 157, 46, 28);
		PlainDocument bShirtsDoc = (PlainDocument) bShirtsTextField.getDocument();
		bShirtsDoc.setDocumentFilter(new TextLengthFilter(4));
		overgarPanel.add(bShirtsTextField);

		coatsTextField = new JTextField();
		coatsTextField.setHorizontalAlignment(SwingConstants.CENTER);
		coatsLabel.setLabelFor(coatsTextField);
		coatsTextField.setMaximumSize(new Dimension(1000, 1000));
		coatsTextField.setColumns(1);
		coatsTextField.setBounds(136, 64, 46, 28);
		PlainDocument coatsDoc = (PlainDocument) coatsTextField.getDocument();
		coatsDoc.setDocumentFilter(new TextLengthFilter(4));
		overgarPanel.add(coatsTextField);

		tShirtsTextField = new JTextField();
		tShirtsTextField.setHorizontalAlignment(SwingConstants.CENTER);
		tShirtsLabel.setLabelFor(tShirtsTextField);
		tShirtsTextField.setMaximumSize(new Dimension(1000, 1000));
		tShirtsTextField.setColumns(1);
		tShirtsTextField.setBounds(136, 203, 46, 28);
		PlainDocument tShirtsDoc = (PlainDocument) tShirtsTextField.getDocument();
		tShirtsDoc.setDocumentFilter(new TextLengthFilter(4));
		overgarPanel.add(tShirtsTextField);

		sweatersTextField = new JTextField();
		sweatersTextField.setHorizontalAlignment(SwingConstants.CENTER);
		sweatersLabel.setLabelFor(sweatersTextField);
		sweatersTextField.setMaximumSize(new Dimension(1000, 1000));
		sweatersTextField.setColumns(1);
		sweatersTextField.setBounds(136, 104, 46, 28);
		PlainDocument sweatersDoc = (PlainDocument) sweatersTextField.getDocument();
		sweatersDoc.setDocumentFilter(new TextLengthFilter(4));
		overgarPanel.add(sweatersTextField);

		contentPanel.add(pantsPanel);

		JLabel amountLabel1 = new JLabel(CommonClient.AMOUNT + ":");
		amountLabel1.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		amountLabel1.setBounds(125, 31, 55, 16);
		pantsPanel.add(amountLabel1);

		JLabel priceLabel1 = new JLabel(CommonClient.PRICE + ":");
		priceLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		priceLabel1.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		priceLabel1.setBounds(200, 31, 46, 16);
		pantsPanel.add(priceLabel1);

		customPriceLabel = new JLabel(CommonClient.PRICE_CUSTOM_PANTS);
		customPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		customPriceLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		customPriceLabel.setBounds(196, 79, 55, 16);
		pantsPanel.add(customPriceLabel);

		sportPriceLabel = new JLabel(CommonClient.PRICE_SPORT_PANTS);
		sportPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sportPriceLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		sportPriceLabel.setBounds(196, 133, 55, 16);
		pantsPanel.add(sportPriceLabel);

		jeansPriceLabel = new JLabel(CommonClient.PRICE_JEANS);
		jeansPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jeansPriceLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		jeansPriceLabel.setBounds(196, 184, 55, 16);
		pantsPanel.add(jeansPriceLabel);

		JLabel amountLabel2 = new JLabel(CommonClient.AMOUNT + ":");
		amountLabel2.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		amountLabel2.setBounds(132, 36, 54, 16);
		overgarPanel.add(amountLabel2);

		JLabel priceLabel2 = new JLabel(CommonClient.PRICE + ":");
		priceLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		priceLabel2.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		priceLabel2.setBounds(209, 36, 40, 16);
		overgarPanel.add(priceLabel2);

		coatsPriceLabel = new JLabel(CommonClient.PRICE_COAT);
		coatsPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		coatsPriceLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		coatsPriceLabel.setBounds(206, 70, 55, 16);
		overgarPanel.add(coatsPriceLabel);

		sweatersPriceLabel = new JLabel(CommonClient.PRICE_SWEATER);
		sweatersPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sweatersPriceLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		sweatersPriceLabel.setBounds(202, 110, 55, 16);
		overgarPanel.add(sweatersPriceLabel);

		btnShirtsPriceLabel = new JLabel(CommonClient.PRICE_BUTTON_SHIRT);
		btnShirtsPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		btnShirtsPriceLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		btnShirtsPriceLabel.setBounds(202, 163, 55, 16);
		overgarPanel.add(btnShirtsPriceLabel);

		tShirtsPriceLabel = new JLabel(CommonClient.PRICE_T_SHIRT);
		tShirtsPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tShirtsPriceLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		tShirtsPriceLabel.setBounds(202, 209, 55, 16);
		overgarPanel.add(tShirtsPriceLabel);
		contentPanel.add(overgarPanel);

		discountPanel = new JPanel();
		discountPanel.setBorder(
				new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		discountPanel.setBounds(26, 306, 546, 61);
		discountPanel.setLayout(null);
		discountPanel.setBackground(background);
		contentPanel.add(discountPanel);

		JLabel buyerIdLabel = new JLabel("Buyer Id:");
		buyerIdLabel.setBounds(40, 24, 80, 16);
		buyerIdLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		buyerIdLabel.setLabelFor(buyerIdTextField);
		discountPanel.add(buyerIdLabel);

		buyerIdTextField = new JTextField();
		buyerIdTextField.setBounds(160, 18, 184, 28);
		buyerIdTextField.setHorizontalAlignment(SwingConstants.CENTER);
		buyerIdTextField.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		buyerIdTextField.setColumns(1);
		PlainDocument idDoc = (PlainDocument) buyerIdTextField.getDocument();
		idDoc.setDocumentFilter(new TextLengthFilter(15));
		discountPanel.add(buyerIdTextField);

		JButton getDiscountBtn = new JButton(CommonClient.GET_DISCOUNT);
		getDiscountBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getDiscountBtn.setBounds(384, 18, 119, 28);
		getDiscountBtn.setActionCommand(CommonClient.GET_DISCOUNT);
		getDiscountBtn.addActionListener(this);
		getDiscountBtn.setMultiClickThreshhold(2000);
		getDiscountBtn.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		discountPanel.add(getDiscountBtn);

	}// setContentPanelComponents

	// Initializing and setting buttons to button panel
	private void setButtonPanelComponents() {

		buttonPanel = new JPanel();
		buttonPanel.setBackground(background);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JButton operationButton = new JButton(operation);
		operationButton.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		operationButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		operationButton.setActionCommand(operation);
		operationButton.addActionListener(this);
		operationButton.setMultiClickThreshhold(2000);
		buttonPanel.add(operationButton);

		JButton cancelButton = new JButton(CommonClient.CANCEL);
		cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelButton.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		cancelButton.setActionCommand(CommonClient.CANCEL);
		cancelButton.addActionListener(this);
		cancelButton.setMultiClickThreshhold(2000);
		buttonPanel.add(cancelButton);

	}// setButtonPanelComponents

	// Set dialog parameters
	private void setJDialogParams() {

		setBounds(100, 100, 595, 456);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(parent);
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle(operation + " stock");

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
		else if (e.getActionCommand().equals(CommonClient.GET_DISCOUNT)) {

			findAndUpdateDiscount();
		} 
		else {
			
			descr = new StringBuilder();
			pants = new HashMap<>();
			overgarment = new HashMap<>();
			JSONObject stock = createStockJSONObject();
			JSONObject stat = createStatJSONObject();
			JSONObject log = logic.getJsonManager().createNewLogJSONObject(operation, descr.toString());

			if (!isFieldsEmpty()) {

				if (sendToServer(stock) && sendToServer(log) && sendToServer(stat)) {

					updateMainClientFields();

					JOptionPane.showMessageDialog(this, CommonClient.TRANSACTION_OK_MESSAGE, "",
							JOptionPane.INFORMATION_MESSAGE);
					
					if(updatedBuyer != null) {
						
						JSONObject buyer = createUpdatedBuyerJSONObject(); 
						sendToServer(buyer);
					}
				} 
				else {

					JOptionPane.showMessageDialog(this, CommonClient.TRANSACTION_ROLLUP_MESSAGE, "Error",
							JOptionPane.WARNING_MESSAGE);
				}
			} 
			else {
				JOptionPane.showMessageDialog(this, CommonClient.EMPTY_FIELDS_MESSAGE, "", JOptionPane.WARNING_MESSAGE);
			}
			
			cleanTextFields();
		}

	}// actionPerformed

	// Creating updated buyer object
	@SuppressWarnings("unchecked")
	private JSONObject createUpdatedBuyerJSONObject() {

		JSONObject buyer = new JSONObject();
		buyer.put(CommonClient.JSON_ROOT_TITLE, CommonClient.BUYER_ROOT);
		buyer.put(CommonClient.MSG_ROOT, CommonClient.REQUEST_UPDATE); 
		buyer.put(CommonClient.FIRSTNAME, updatedBuyer.getFirstName());
		buyer.put(CommonClient.LASTNAME, updatedBuyer.getLastName());
		buyer.put(CommonClient.ID, updatedBuyer.getId());
		buyer.put(CommonClient.TELEPHONE, updatedBuyer.getTelephone());
		
		if(updatedBuyer instanceof NewBuyer) {
			
			buyer.put(CommonClient.TYPE, CommonClient.BUYER_TYPE_NEW);
			buyer.put(CommonClient.BUYER_UPDATE_TYPE, CommonClient.BUYER_TYPE_RETURNED);
		}
		
		if(updatedBuyer instanceof ReturnedBuyer) {
			
			buyer.put(CommonClient.TYPE, CommonClient.BUYER_TYPE_RETURNED);
			buyer.put(CommonClient.BUYER_UPDATE_TYPE, CommonClient.BUYER_TYPE_VIP);
		}

		return buyer;
		
	}// createUpdatedBuyerJSONObject

	// Creating new statistics object
	@SuppressWarnings("unchecked")
	private JSONObject createStatJSONObject() {
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		
		JSONObject stat = new JSONObject();
		stat.put(CommonClient.JSON_ROOT_TITLE, CommonClient.STAT_ROOT);
		stat.put(CommonClient.MSG_ROOT, CommonClient.REQUEST_ADD); 
		stat.put(CommonClient.JSON_BRANCH_TITLE, logic.getBranchName());
		stat.put(CommonClient.STAT_DATE, format.format(date));
		stat.put(CommonClient.STAT_TIME, DateFormat.getTimeInstance().format(date));
		
		JSONArray pantsArray = new JSONArray();
		
		for (Map.Entry<String, String> entry : pants.entrySet()) {
			
			String item = entry.getKey();
			String amount = entry.getValue();
			JSONObject object = new JSONObject();
			object.put(item, amount);			
			pantsArray.add(object);			
		}
		
		JSONArray overgarmentArray = new JSONArray();
		
		for(Map.Entry<String, String> entry : overgarment.entrySet()) {
			
			String item = entry.getKey();
			String amount = entry.getValue();
			JSONObject object = new JSONObject();
			object.put(item, amount);			
			overgarmentArray.add(object);		
		}
		
		stat.put(CommonClient.STOCK_PANTS, pantsArray);
		stat.put(CommonClient.STOCK_OVERGARMENT, overgarmentArray);
		
		return stat;
	
	}// createStatJSONObject

	// Checking for empty fields
	private boolean isFieldsEmpty() {

		return jeansTextField.getText().isEmpty() && cPantsTextField.getText().isEmpty()
				&& sPantsTextField.getText().isEmpty() && bShirtsTextField.getText().isEmpty()
				&& coatsTextField.getText().isEmpty() && tShirtsTextField.getText().isEmpty()
				&& sweatersTextField.getText().isEmpty();

	}// isFieldsEmpty

	// Updating discount according to buyer type
	private void findAndUpdateDiscount() {

		boolean isBranchBuyer = false;

		if (!buyerIdTextField.getText().isEmpty()) {

			String id = buyerIdTextField.getText();
			ArrayList<Buyer> buyers = logic.getBuyersList();

			for (Buyer buyer : buyers) {

				if (buyer.getId().equals(id)) {

					customPriceLabel.setText(buyer.calculatePrice(CommonClient.PRICE_CUSTOM_PANTS));
					sportPriceLabel.setText(buyer.calculatePrice(CommonClient.PRICE_SPORT_PANTS));
					jeansPriceLabel.setText(buyer.calculatePrice(CommonClient.PRICE_JEANS));
					coatsPriceLabel.setText(buyer.calculatePrice(CommonClient.PRICE_COAT));
					sweatersPriceLabel.setText(buyer.calculatePrice(CommonClient.PRICE_SWEATER));
					btnShirtsPriceLabel.setText(buyer.calculatePrice(CommonClient.PRICE_BUTTON_SHIRT));
					tShirtsPriceLabel.setText(buyer.calculatePrice(CommonClient.PRICE_T_SHIRT));
					isBranchBuyer = true;
					updatedBuyer = buyer;					
					break;
				}
			}

			if (!isBranchBuyer) {

				JOptionPane.showMessageDialog(this, CommonClient.NOT_BRANCH_BUYER_MESSAGE, "",
						JOptionPane.WARNING_MESSAGE);
			}
		}

	}// findAndUpdateDiscount

	// Send JSON object to server
	private boolean sendToServer(JSONObject obj) {

		boolean isSent = false;

		logic.getClient().sendToServer(obj);
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

	// Cleaning the text fields
	private void cleanTextFields() {

		jeansTextField.setText("");
		cPantsTextField.setText("");
		sPantsTextField.setText("");
		bShirtsTextField.setText("");
		coatsTextField.setText("");
		tShirtsTextField.setText("");
		sweatersTextField.setText("");
		buyerIdTextField.setText("");
		pants.clear();
		overgarment.clear();

	}// cleanTextFields

	// Update stock fields in main client
	private void updateMainClientFields() {

		for (Map.Entry<JLabel, String> element : updatedValues.entrySet()) {

			JLabel label = element.getKey();
			String value = element.getValue();
			label.setText(value);
		}

	}// updateMainClientFields

	// Create JSON object from dialog fields
	@SuppressWarnings("unchecked")
	private JSONObject createStockJSONObject() {

		JSONObject stock = new JSONObject();
		stock.put(CommonClient.JSON_ROOT_TITLE, CommonClient.STOCK_ROOT);
		stock.put(CommonClient.MSG_ROOT, CommonClient.REQUEST_UPDATE);
		stock.put(CommonClient.JSON_BRANCH_TITLE, logic.getBranchName());
		setJeansAmount(stock);
		setCustomPantsAmount(stock);
		setSportPantsAmount(stock);
		setButtonedShirtsAmount(stock);
		setCoatsAmount(stock);
		setTShirtsAmount(stock);
		setSweatersAmount(stock);

		return stock;

	}// createStockJSONObject

	// Get jeans amount
	@SuppressWarnings("unchecked")
	private void setJeansAmount(JSONObject object) {

		if (!jeansTextField.getText().isEmpty()) {

			String valueTosend = calculateStockValues(parent.getJeansValueLabel().getText(), jeansTextField.getText());

			if (valueTosend != null) {

				object.put(CommonClient.STOCK_JEANS, valueTosend);
				updatedValues.put(parent.getJeansValueLabel(), valueTosend);
				System.out.println(jeansTextField.getText());
				descr.append(String.format(" %s %s ", jeansTextField.getText(), CommonClient.STOCK_JEANS));
				pants.put(CommonClient.STOCK_JEANS, jeansTextField.getText());
			}
		} 
		else {
			object.put(CommonClient.STOCK_JEANS, parent.getJeansValueLabel().getText());
		}

	}// getJeansAmount

	// Get custom pants amount
	@SuppressWarnings("unchecked")
	private void setCustomPantsAmount(JSONObject object) {

		if (!cPantsTextField.getText().isEmpty()) {

			String valueTosend = calculateStockValues(parent.getCustomPantsValueLabel().getText(),
					cPantsTextField.getText());

			if (valueTosend != null) {

				object.put(CommonClient.STOCK_CUSTOM_PANTS, valueTosend);
				updatedValues.put(parent.getCustomPantsValueLabel(), valueTosend);
				descr.append(String.format(" %s %s ", cPantsTextField.getText(), CommonClient.STOCK_CUSTOM_PANTS));
				pants.put(CommonClient.STOCK_CUSTOM_PANTS, cPantsTextField.getText());
			}
		} 
		else {
			object.put(CommonClient.STOCK_CUSTOM_PANTS, parent.getCustomPantsValueLabel().getText());
		}

	}// getCustomPantsAmount

	// Get sport pants amount
	@SuppressWarnings("unchecked")
	private void setSportPantsAmount(JSONObject object) {

		if (!sPantsTextField.getText().isEmpty()) {

			String valueTosend = calculateStockValues(parent.getSportPantsValueLabel().getText(), sPantsTextField.getText());

			if (valueTosend != null) {

				object.put(CommonClient.STOCK_SPORT_PANTS, valueTosend);
				updatedValues.put(parent.getSportPantsValueLabel(), valueTosend);
				descr.append(String.format(" %s %s ", sPantsTextField.getText(), CommonClient.STOCK_SPORT_PANTS));
				pants.put(CommonClient.STOCK_SPORT_PANTS, sPantsTextField.getText());
			}
		} 
		else {
			object.put(CommonClient.STOCK_SPORT_PANTS, parent.getSportPantsValueLabel().getText());
		}

	}// getSportPantsAmount

	// Get buttoned shirts amount
	@SuppressWarnings("unchecked")
	private void setButtonedShirtsAmount(JSONObject object) {

		if (!bShirtsTextField.getText().isEmpty()) {

			String valueTosend = calculateStockValues(parent.getButtonShirtsValueLabel().getText(),
					bShirtsTextField.getText());

			if (valueTosend != null) {

				object.put(CommonClient.STOCK_BUTTON_SHIRT, valueTosend);
				updatedValues.put(parent.getButtonShirtsValueLabel(), valueTosend);				
				descr.append(String.format(" %s %s ", bShirtsTextField.getText(), CommonClient.STOCK_BUTTON_SHIRT));
				overgarment.put(CommonClient.STOCK_BUTTON_SHIRT, bShirtsTextField.getText());
			}
		} 
		else {
			object.put(CommonClient.STOCK_BUTTON_SHIRT, parent.getButtonShirtsValueLabel().getText());
		}

	}// getButtonedShirtsAmount

	// Get coats amount
	@SuppressWarnings("unchecked")
	private void setCoatsAmount(JSONObject object) {

		if (!coatsTextField.getText().isEmpty()) {

			String valueTosend = calculateStockValues(parent.getCoatsValueLabel().getText(), coatsTextField.getText());

			if (valueTosend != null) {

				object.put(CommonClient.STOCK_COAT, valueTosend);
				updatedValues.put(parent.getCoatsValueLabel(), valueTosend);				
				descr.append(String.format(" %s %s ", coatsTextField.getText(), CommonClient.STOCK_COAT));
				overgarment.put(CommonClient.STOCK_COAT, coatsTextField.getText());
			}
		} 
		else {
			object.put(CommonClient.STOCK_COAT, parent.getCoatsValueLabel().getText());
		}

	}// getCoatsAmount

	// Get T-shirts amount
	@SuppressWarnings("unchecked")
	private void setTShirtsAmount(JSONObject object) {

		if (!tShirtsTextField.getText().isEmpty()) {

			String valueTosend = calculateStockValues(parent.getTshirtsValueLabel().getText(), tShirtsTextField.getText());

			if (valueTosend != null) {

				object.put(CommonClient.STOCK_T_SHIRT, valueTosend);
				updatedValues.put(parent.getTshirtsValueLabel(), valueTosend);
				descr.append(String.format(" %s %s ", tShirtsTextField.getText(), CommonClient.STOCK_T_SHIRT));				
				overgarment.put(CommonClient.STOCK_T_SHIRT, tShirtsTextField.getText());
			}
		} 
		else {
			object.put(CommonClient.STOCK_T_SHIRT, parent.getTshirtsValueLabel().getText());
		}

	}// getTShirtsAmount

	// Get sweaters amount
	@SuppressWarnings("unchecked")
	private void setSweatersAmount(JSONObject object) {

		if (!sweatersTextField.getText().isEmpty()) {

			String valueTosend = calculateStockValues(parent.getSweatersValueLabel().getText(), sweatersTextField.getText());

			if (valueTosend != null) {

				object.put(CommonClient.STOCK_SWEATER, valueTosend);
				updatedValues.put(parent.getSweatersValueLabel(), valueTosend);				
				descr.append(String.format(" %s %s ", sweatersTextField.getText(), CommonClient.STOCK_SWEATER));
				overgarment.put(CommonClient.STOCK_SWEATER, sweatersTextField.getText());
			}
		} 
		else {
			object.put(CommonClient.STOCK_SWEATER, parent.getSweatersValueLabel().getText());
		}

	}// getSweatersAmount

	// Calculate stock values according to values from label in ClientMain class and
	// new values from dialog
	private String calculateStockValues(String valueFromMainLabel, String valueFromDialogTextField) {

		String valueToSend = null;
		int valueOld, valueNew, total = 0;

		// Try to parse strings to unsigned integer number if exception occurs
		// show warning dialog
		try {
			valueOld = Integer.parseUnsignedInt(valueFromMainLabel);
			valueNew = Integer.parseUnsignedInt(valueFromDialogTextField);

			// Update values according to dialog operation
			if (operation.equals(CommonClient.SELL)) {

				if (valueOld >= valueNew) {

					total = valueOld - valueNew;
				} 
				else {
					JOptionPane.showMessageDialog(this, CommonClient.NOT_ENOUGH_ITEMS_IN_STOCK_MESSAGE, "Error",
							JOptionPane.WARNING_MESSAGE);
				}
			} 
			else if (operation.equals(CommonClient.BUY)) {

				total = valueOld + valueNew;
			}

			valueToSend = String.valueOf(total);
		} 
		catch (NumberFormatException e) {

			JOptionPane.showMessageDialog(this, CommonClient.WRONG_VALUE_MESSAGE, "Error", JOptionPane.WARNING_MESSAGE);
		}

		return valueToSend;

	}// calculateStockValues

}// class
