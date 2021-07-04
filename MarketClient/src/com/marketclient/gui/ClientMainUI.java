package com.marketclient.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.marketclient.logic.ClientMainLogic;
import com.marketclient.main.CommonClient;

// Main UI class
public class ClientMainUI extends JFrame {

	private static final long serialVersionUID = -631287431854732995L;

	private ClientMainLogic logic;

	private JPanel contentPane;
	private JPanel currentStockTabPanel;
	private JPanel buyersTabPanel;
	private JPanel employeesTabPanel;
	private JPanel statisticsTabPanel;
	private JPanel logsTabPanel;

	private JLabel stockBranchTitleLabel;
	private JLabel customPantsValueLabel;
	private JLabel sportPantsValueLabel;
	private JLabel jeansValueLabel;
	private JLabel coatsValueLabel;
	private JLabel sweatersValueLabel;
	private JLabel buttonShirtsValueLabel;
	private JLabel tshirtsValueLabel;

	private JTabbedPane tabbedPane;

	private JTable buyersTable;
	private JTable employeesTable;

	private JTextArea statisticsTextArea;
	private JTextArea logsTextArea;

	private JButton buyBtn;
	private JButton addEmployeeBtn;
	private JButton deleteEmployeeBtn;

	private ButtonGroup buttonGroup;

	public ClientMainUI() {

		logic = new ClientMainLogic(this);
		logic.startClient();

		if (logic.getClient().isConnected()) {

			initLookAndFeel();
			initContentPanePanel();
			initTabPane();
			setTabPanelsComponents();
			setTabbedPane();
			setJFrameParams();
		}
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

	// Initializing content panel
	private void initContentPanePanel() {

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

	}// initContentPanePanel

	// Initializing tabbed pane
	private void initTabPane() {

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 720, 420);
		contentPane.add(tabbedPane);

	}// initTabPane

	// Initializing and setting content fields to panels
	private void setTabPanelsComponents() {

		setStockTabComponents();
		setBuyersTabComponents();
		setEmployeeTabComponents();
		setStatisticsTabComponents();
		setLogsTabComponents();		

	}// setTabPanelsComponents

	// Initializing and setting content fields in stock tab
	private void setStockTabComponents() {

		Color background = new Color(204, 255, 0);
		currentStockTabPanel = new JPanel();
		currentStockTabPanel.setBackground(background);
		currentStockTabPanel.setLayout(null);

		JPanel stockTitlePanel = new JPanel();
		stockTitlePanel.setBackground(background);
		stockTitlePanel.setBounds(218, 22, 283, 44);
		stockTitlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

		JLabel stockTitleLabel = new JLabel(CommonClient.STOCK_LABEL_TITLE);
		stockTitleLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 18));
		stockTitlePanel.add(stockTitleLabel);

		stockBranchTitleLabel = new JLabel();
		stockBranchTitleLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 18));
		stockTitlePanel.add(stockBranchTitleLabel);

		JPanel stockCategoryPanel = new JPanel();
		stockCategoryPanel.setBackground(background);
		stockCategoryPanel.setBounds(57, 88, 606, 225);
		stockCategoryPanel.setLayout(null);

		JPanel pantsCategoryPanel = new JPanel();
		pantsCategoryPanel.setBorder(new TitledBorder(null, CommonClient.PANTS_CATEGORY_TITLE, TitledBorder.LEADING,
				TitledBorder.TOP, new Font(CommonClient.FONT, Font.BOLD, 14), new Color(59, 59, 59)));
		pantsCategoryPanel.setBackground(background);
		pantsCategoryPanel.setBounds(0, 0, 262, 225);
		pantsCategoryPanel.setLayout(null);
		stockCategoryPanel.add(pantsCategoryPanel);

		JLabel customPantsLabel = new JLabel(CommonClient.STOCK_CUSTOM_PANTS);
		customPantsLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		customPantsLabel.setBounds(28, 42, 100, 19);
		pantsCategoryPanel.add(customPantsLabel);

		customPantsValueLabel = new JLabel();
		customPantsLabel.setLabelFor(customPantsValueLabel);
		customPantsValueLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		customPantsValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		customPantsValueLabel.setBounds(172, 43, 55, 16);
		pantsCategoryPanel.add(customPantsValueLabel);

		JLabel sportPantsLabel = new JLabel(CommonClient.STOCK_SPORT_PANTS);
		sportPantsLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		sportPantsLabel.setBounds(28, 103, 85, 19);
		pantsCategoryPanel.add(sportPantsLabel);

		sportPantsValueLabel = new JLabel();
		sportPantsLabel.setLabelFor(sportPantsValueLabel);
		sportPantsValueLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		sportPantsValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sportPantsValueLabel.setBounds(172, 104, 55, 16);
		pantsCategoryPanel.add(sportPantsValueLabel);

		JLabel jeansLabel = new JLabel(CommonClient.STOCK_JEANS);
		jeansLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		jeansLabel.setBounds(28, 164, 46, 19);
		pantsCategoryPanel.add(jeansLabel);

		jeansValueLabel = new JLabel();
		jeansLabel.setLabelFor(jeansValueLabel);
		jeansValueLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		jeansValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jeansValueLabel.setBounds(172, 165, 55, 16);
		pantsCategoryPanel.add(jeansValueLabel);

		JPanel shirtsAndCoatsPanelLabel = new JPanel();
		shirtsAndCoatsPanelLabel
				.setBorder(new TitledBorder(null, CommonClient.OVERGARMENT_CATEGORY_TITLE, TitledBorder.LEADING,
						TitledBorder.TOP, new Font(CommonClient.FONT, Font.BOLD, 14), new Color(59, 59, 59)));
		shirtsAndCoatsPanelLabel.setBackground(background);
		shirtsAndCoatsPanelLabel.setBounds(344, 0, 262, 225);
		shirtsAndCoatsPanelLabel.setLayout(null);
		stockCategoryPanel.add(shirtsAndCoatsPanelLabel);

		JLabel coatLabel = new JLabel(CommonClient.STOCK_COAT);
		coatLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		coatLabel.setBounds(34, 32, 55, 16);
		shirtsAndCoatsPanelLabel.add(coatLabel);

		coatsValueLabel = new JLabel();
		coatLabel.setLabelFor(coatsValueLabel);
		coatsValueLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		coatsValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		coatsValueLabel.setBounds(172, 32, 55, 16);
		shirtsAndCoatsPanelLabel.add(coatsValueLabel);

		JLabel sweaterLabel = new JLabel(CommonClient.STOCK_SWEATER);
		sweaterLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		sweaterLabel.setBounds(34, 80, 69, 16);
		shirtsAndCoatsPanelLabel.add(sweaterLabel);

		sweatersValueLabel = new JLabel();
		sweaterLabel.setLabelFor(sweatersValueLabel);
		sweatersValueLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		sweatersValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sweatersValueLabel.setBounds(172, 80, 55, 16);
		shirtsAndCoatsPanelLabel.add(sweatersValueLabel);

		JLabel buttonShirtLable = new JLabel(CommonClient.STOCK_BUTTON_SHIRT);
		buttonShirtLable.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		buttonShirtLable.setBounds(34, 128, 106, 16);
		shirtsAndCoatsPanelLabel.add(buttonShirtLable);

		buttonShirtsValueLabel = new JLabel();
		buttonShirtLable.setLabelFor(buttonShirtsValueLabel);
		buttonShirtsValueLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		buttonShirtsValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		buttonShirtsValueLabel.setBounds(172, 128, 55, 16);
		shirtsAndCoatsPanelLabel.add(buttonShirtsValueLabel);

		JLabel tshirtLabel = new JLabel(CommonClient.STOCK_T_SHIRT);
		tshirtLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		tshirtLabel.setBounds(34, 176, 69, 16);
		shirtsAndCoatsPanelLabel.add(tshirtLabel);

		tshirtsValueLabel = new JLabel();
		tshirtLabel.setLabelFor(tshirtsValueLabel);
		tshirtsValueLabel.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		tshirtsValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tshirtsValueLabel.setBounds(172, 176, 55, 16);
		shirtsAndCoatsPanelLabel.add(tshirtsValueLabel);

		JPanel stockBtnPanel = new JPanel();
		stockBtnPanel.setBackground(background);
		stockBtnPanel.setBounds(31, 341, 228, 38);
		stockBtnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton sellBtn = new JButton(CommonClient.SELL);
		sellBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		sellBtn.setActionCommand(CommonClient.SELL);
		sellBtn.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		sellBtn.setMultiClickThreshhold(2000);
		sellBtn.addActionListener(logic);
		stockBtnPanel.add(sellBtn);

		buyBtn = new JButton(CommonClient.BUY);
		buyBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buyBtn.setActionCommand(CommonClient.BUY);
		buyBtn.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		buyBtn.setMultiClickThreshhold(2000);
		buyBtn.addActionListener(logic);
		stockBtnPanel.add(buyBtn);

		currentStockTabPanel.add(stockTitlePanel);
		currentStockTabPanel.add(stockCategoryPanel);
		currentStockTabPanel.add(stockBtnPanel);

	}// setStockTabComponents

	// Initializing and setting content fields in client tab
	private void setBuyersTabComponents() {

		Color background = new Color(255, 248, 220);
		buyersTabPanel = new JPanel();
		buyersTabPanel.setBackground(background);
		buyersTabPanel.setLayout(null);

		initBuyersTable();

		JPanel clientsScrollPanel = new JPanel();
		clientsScrollPanel.setBorder(new TitledBorder(null, CommonClient.CLIENTS_SCROLL_PANEL_TITLE, TitledBorder.LEFT,
				TitledBorder.TOP, null, new Color(59, 59, 59)));
		clientsScrollPanel.setBackground(background);
		clientsScrollPanel.setBounds(0, 14, 715, 314);
		clientsScrollPanel.setLayout(null);

		JScrollPane clientsScrollPane = new JScrollPane(buyersTable);
		clientsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		clientsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		clientsScrollPane.setBounds(12, 18, 690, 282);
		clientsScrollPanel.add(clientsScrollPane);

		JPanel clientsBtnPanel = new JPanel();
		clientsBtnPanel.setBackground(background);
		clientsBtnPanel.setBounds(12, 338, 213, 41);

		JButton addClientBtn = new JButton(CommonClient.ADD_BUYER);
		addClientBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addClientBtn.setActionCommand(CommonClient.ADD_BUYER);
		addClientBtn.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		addClientBtn.setMultiClickThreshhold(2000);
		addClientBtn.addActionListener(logic);
		clientsBtnPanel.add(addClientBtn);	

		buyersTabPanel.add(clientsScrollPanel);
		buyersTabPanel.add(clientsBtnPanel);

	}// setClientTabComponents

	// Setting clients table
	private void initBuyersTable() {

		String[] columnNames = { CommonClient.ID, CommonClient.FIRSTNAME, CommonClient.LASTNAME, CommonClient.TELEPHONE,
				CommonClient.TYPE };

		buyersTable = new JTable();
		buyersTable.setShowVerticalLines(true);
		buyersTable.setShowHorizontalLines(true);
		buyersTable.getTableHeader().setReorderingAllowed(false);
		buyersTable.setRowSelectionAllowed(false);
		buyersTable.setCellSelectionEnabled(false);
		buyersTable.setColumnSelectionAllowed(false);
		buyersTable.setModel(new TableModel(columnNames));

		buyersTable.getColumnModel().getColumn(0).setMaxWidth(200);
		buyersTable.getColumnModel().getColumn(0).setMinWidth(90);
		buyersTable.getColumnModel().getColumn(0).setPreferredWidth(200);
		buyersTable.getColumnModel().getColumn(1).setMaxWidth(200);
		buyersTable.getColumnModel().getColumn(1).setMinWidth(90);
		buyersTable.getColumnModel().getColumn(1).setPreferredWidth(200);
		buyersTable.getColumnModel().getColumn(2).setMaxWidth(200);
		buyersTable.getColumnModel().getColumn(2).setMinWidth(90);
		buyersTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		buyersTable.getColumnModel().getColumn(3).setMaxWidth(200);
		buyersTable.getColumnModel().getColumn(3).setMinWidth(90);
		buyersTable.getColumnModel().getColumn(3).setPreferredWidth(200);
		buyersTable.getColumnModel().getColumn(4).setMaxWidth(200);
		buyersTable.getColumnModel().getColumn(4).setMinWidth(90);
		buyersTable.getColumnModel().getColumn(4).setPreferredWidth(200);
		
	}// initBuyersTable

	// Initializing and setting content fields in employee tab
	private void setEmployeeTabComponents() {

		Color background = new Color(176, 224, 230);
		employeesTabPanel = new JPanel();
		employeesTabPanel.setBackground(background);
		employeesTabPanel.setLayout(null);

		initEmployeesTable();

		JPanel employeeScrollPanel = new JPanel();
		employeeScrollPanel.setBorder(new TitledBorder(null, CommonClient.EMPLOYEES_SCROLL_PANEL_TITLE,
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		employeeScrollPanel.setBackground(background);
		employeeScrollPanel.setBounds(0, 14, 715, 314);
		employeeScrollPanel.setLayout(null);

		JScrollPane employeeScrollPane = new JScrollPane(employeesTable);
		employeeScrollPane.setBounds(12, 18, 690, 282);
		employeeScrollPanel.add(employeeScrollPane);

		JPanel employeeBtnPanel = new JPanel();
		employeeBtnPanel.setBackground(background);
		employeeBtnPanel.setBounds(12, 338, 288, 41);

		addEmployeeBtn = new JButton(CommonClient.ADD_EMPLOYEE);
		addEmployeeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addEmployeeBtn.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		addEmployeeBtn.setActionCommand(CommonClient.ADD_EMPLOYEE);
		addEmployeeBtn.addActionListener(logic);
		addEmployeeBtn.setMultiClickThreshhold(2000);
		employeeBtnPanel.add(addEmployeeBtn);

		deleteEmployeeBtn = new JButton(CommonClient.DELETE_EMPLOYEE);
		deleteEmployeeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		deleteEmployeeBtn.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		deleteEmployeeBtn.setActionCommand(CommonClient.DELETE_EMPLOYEE);
		deleteEmployeeBtn.addActionListener(logic);
		deleteEmployeeBtn.setMultiClickThreshhold(2000);
		employeeBtnPanel.add(deleteEmployeeBtn);

		employeesTabPanel.add(employeeScrollPanel);
		employeesTabPanel.add(employeeBtnPanel);

	}// setEmployeeTabComponents

	// Setting employee table
	private void initEmployeesTable() {

		String[] columnNames = { CommonClient.WORK_ID, CommonClient.FIRSTNAME, CommonClient.LASTNAME, CommonClient.ID,
				CommonClient.ACCOUNT, CommonClient.TELEPHONE, CommonClient.POSITION };

		employeesTable = new JTable();
		employeesTable.setShowVerticalLines(true);
		employeesTable.setShowHorizontalLines(true);
		employeesTable.getTableHeader().setReorderingAllowed(false);
		employeesTable.setRowSelectionAllowed(false);
		employeesTable.setCellSelectionEnabled(false);
		employeesTable.setColumnSelectionAllowed(false);
		employeesTable.setModel(new TableModel(columnNames));

		employeesTable.getColumnModel().getColumn(0).setMaxWidth(110);
		employeesTable.getColumnModel().getColumn(0).setPreferredWidth(90);
		employeesTable.getColumnModel().getColumn(1).setMaxWidth(100);
		employeesTable.getColumnModel().getColumn(1).setPreferredWidth(90);
		employeesTable.getColumnModel().getColumn(2).setMaxWidth(100);
		employeesTable.getColumnModel().getColumn(2).setPreferredWidth(90);
		employeesTable.getColumnModel().getColumn(3).setMaxWidth(100);
		employeesTable.getColumnModel().getColumn(3).setPreferredWidth(90);
		employeesTable.getColumnModel().getColumn(4).setMaxWidth(110);
		employeesTable.getColumnModel().getColumn(4).setPreferredWidth(90);
		employeesTable.getColumnModel().getColumn(5).setMaxWidth(100);
		employeesTable.getColumnModel().getColumn(5).setPreferredWidth(90);
		employeesTable.getColumnModel().getColumn(6).setMaxWidth(100);
		employeesTable.getColumnModel().getColumn(6).setPreferredWidth(90);		

	}// setEmployeesTable

	// Initializing and setting content fields in statistics tab
	private void setStatisticsTabComponents() {

		statisticsTabPanel = new JPanel();
		statisticsTabPanel.setLayout(null);

		setRadioButtonsPanelComponents();

		JPanel staticticsTextViewPanel = new JPanel();
		staticticsTextViewPanel.setBorder(
				new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		staticticsTextViewPanel.setBounds(6, 58, 705, 283);
		staticticsTextViewPanel.setLayout(null);

		statisticsTextArea = new JTextArea();
		statisticsTextArea.setFont(new Font(CommonClient.FONT, Font.PLAIN, 17));
		statisticsTextArea.setMargin(new Insets(10, 10, 0, 10));
		statisticsTextArea.setBounds(6, 6, 691, 265);
		statisticsTextArea.setEditable(false);
		statisticsTextArea.setWrapStyleWord(true);
		statisticsTextArea.setLineWrap(true);

		JScrollPane statisticsScrollPane = new JScrollPane(statisticsTextArea);
		statisticsScrollPane.setBounds(6, 6, 691, 265);
		statisticsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		statisticsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		staticticsTextViewPanel.add(statisticsScrollPane);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(16, 343, 207, 41);		
		
		JButton saveToFileBtn = new JButton(CommonClient.SAVE);
		saveToFileBtn.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		saveToFileBtn.setActionCommand(CommonClient.SAVE);
		saveToFileBtn.setMultiClickThreshhold(2000);
		saveToFileBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		saveToFileBtn.addActionListener(logic);
		buttonPanel.add(saveToFileBtn);

		statisticsTabPanel.add(staticticsTextViewPanel);
		statisticsTabPanel.add(buttonPanel);

	}// setStatisticsTabComponents

	// Setting radio buttons panel components
	private void setRadioButtonsPanelComponents() {

		buttonGroup = new ButtonGroup();

		JPanel radioBtnsPanel = new JPanel();
		radioBtnsPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		radioBtnsPanel.setBounds(6, 6, 705, 50);

		GridBagLayout gbl_radioBtnsPanel = new GridBagLayout();
		gbl_radioBtnsPanel.columnWidths = new int[] { 82, 103, 0, 142, 79, 72, 0, 0, 0, 0, 0 };
		gbl_radioBtnsPanel.rowHeights = new int[] { 28, 0 };
		gbl_radioBtnsPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_radioBtnsPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		radioBtnsPanel.setLayout(gbl_radioBtnsPanel);

		JRadioButton totalSellingsRadioBtn = new JRadioButton(CommonClient.RADIO_BUTTON_ALL_SELLINGS);
		totalSellingsRadioBtn.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		totalSellingsRadioBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		totalSellingsRadioBtn.setActionCommand(CommonClient.RADIO_BUTTON_ALL_SELLINGS);		
		GridBagConstraints gbc_totalSellingsRadioBtn = new GridBagConstraints();
		gbc_totalSellingsRadioBtn.ipadx = 10;
		gbc_totalSellingsRadioBtn.fill = GridBagConstraints.BOTH;
		gbc_totalSellingsRadioBtn.insets = new Insets(0, 35, 0, 5);
		gbc_totalSellingsRadioBtn.gridx = 0;
		gbc_totalSellingsRadioBtn.gridy = 0;
		radioBtnsPanel.add(totalSellingsRadioBtn, gbc_totalSellingsRadioBtn);

		JRadioButton pantsSellingsRadioBtn = new JRadioButton(CommonClient.RADIO_BUTTON_PANTS);
		pantsSellingsRadioBtn.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		pantsSellingsRadioBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pantsSellingsRadioBtn.setActionCommand(CommonClient.RADIO_BUTTON_PANTS);		
		GridBagConstraints gbc_pantsSellingsRadioBtn = new GridBagConstraints();
		gbc_pantsSellingsRadioBtn.ipadx = 5;
		gbc_pantsSellingsRadioBtn.fill = GridBagConstraints.BOTH;
		gbc_pantsSellingsRadioBtn.insets = new Insets(0, 0, 0, 5);
		gbc_pantsSellingsRadioBtn.gridx = 1;
		gbc_pantsSellingsRadioBtn.gridy = 0;
		radioBtnsPanel.add(pantsSellingsRadioBtn, gbc_pantsSellingsRadioBtn);

		JRadioButton overgarmentSellingsRadioBtn = new JRadioButton(CommonClient.RADIO_BUTTON_OVERGARMENT);
		overgarmentSellingsRadioBtn.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		overgarmentSellingsRadioBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		overgarmentSellingsRadioBtn.setActionCommand(CommonClient.RADIO_BUTTON_OVERGARMENT);		
		GridBagConstraints gbc_overgarmentSellingsRadioBtn = new GridBagConstraints();
		gbc_overgarmentSellingsRadioBtn.ipadx = 5;
		gbc_overgarmentSellingsRadioBtn.fill = GridBagConstraints.BOTH;
		gbc_overgarmentSellingsRadioBtn.insets = new Insets(0, 0, 0, 5);
		gbc_overgarmentSellingsRadioBtn.gridx = 2;
		gbc_overgarmentSellingsRadioBtn.gridy = 0;
		radioBtnsPanel.add(overgarmentSellingsRadioBtn, gbc_overgarmentSellingsRadioBtn);

		JRadioButton vipRadioBtn = new JRadioButton(CommonClient.RADIO_BUTTON_VIP);
		vipRadioBtn.setFont(new Font(CommonClient.FONT, Font.BOLD, 13));
		vipRadioBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		vipRadioBtn.setActionCommand(CommonClient.RADIO_BUTTON_VIP);		
		GridBagConstraints gbc_vipRadioBtn = new GridBagConstraints();
		gbc_vipRadioBtn.ipadx = 5;
		gbc_vipRadioBtn.fill = GridBagConstraints.BOTH;
		gbc_vipRadioBtn.insets = new Insets(0, 0, 0, 5);
		gbc_vipRadioBtn.gridx = 3;
		gbc_vipRadioBtn.gridy = 0;
		radioBtnsPanel.add(vipRadioBtn, gbc_vipRadioBtn);

		JButton refreshInRadionBtn = new JButton(CommonClient.REFRESH);
		refreshInRadionBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		refreshInRadionBtn.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		refreshInRadionBtn.setActionCommand(CommonClient.REFRESH);
		refreshInRadionBtn.setMultiClickThreshhold(2000);
		refreshInRadionBtn.addActionListener(logic);
		GridBagConstraints gbc_refreshInRadionBtn = new GridBagConstraints();
		gbc_refreshInRadionBtn.fill = GridBagConstraints.BOTH;
		gbc_refreshInRadionBtn.insets = new Insets(0, 0, 0, 5);
		gbc_refreshInRadionBtn.gridx = 4;
		gbc_refreshInRadionBtn.gridy = 0;
		radioBtnsPanel.add(refreshInRadionBtn, gbc_refreshInRadionBtn);

		buttonGroup.add(totalSellingsRadioBtn);		
		buttonGroup.add(pantsSellingsRadioBtn);
		buttonGroup.add(overgarmentSellingsRadioBtn);
		buttonGroup.add(vipRadioBtn);
		
		statisticsTabPanel.add(radioBtnsPanel);

	}// setRadioButtonsPanelComponents

	// Initializing and setting content fields in log tab
	private void setLogsTabComponents() {

		logsTabPanel = new JPanel();
		logsTabPanel.setBackground(new Color(250, 250, 210));
		logsTabPanel.setLayout(null);

		JPanel logsScrollPanel = new JPanel();
		logsScrollPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		logsScrollPanel.setBackground(new Color(255, 255, 224));
		logsScrollPanel.setBounds(6, 6, 705, 311);
		logsScrollPanel.setLayout(null);
		logsTabPanel.add(logsScrollPanel);

		logsTextArea = new JTextArea();
		logsTextArea.setFont(new Font(CommonClient.FONT, Font.PLAIN, 16));
		logsTextArea.setMargin(new Insets(10, 10, 0, 10));
		logsTextArea.setRequestFocusEnabled(false);
		logsTextArea.setBounds(10, 12, 685, 285);
		logsTextArea.setWrapStyleWord(true);
		logsTextArea.setEditable(false);
		logsTextArea.setLineWrap(true);

		JScrollPane logsScrollPane = new JScrollPane(logsTextArea);
		logsScrollPane.setBounds(10, 12, 685, 285);
		logsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		logsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		logsScrollPanel.add(logsScrollPane);

		JButton getLogsBtn = new JButton(CommonClient.GET_LOGS);
		getLogsBtn.setFont(new Font(CommonClient.FONT, Font.BOLD, 14));
		getLogsBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getLogsBtn.setActionCommand(CommonClient.GET_LOGS);
		getLogsBtn.setBounds(310, 344, 100, 28);
		getLogsBtn.setMultiClickThreshhold(2000);
		getLogsBtn.addActionListener(logic);
		logsTabPanel.add(getLogsBtn);

	}// setLogsTabComponents

	// Setting tabbed pane
	private void setTabbedPane() {

		tabbedPane.addTab(CommonClient.TAB_CURRENT_STOCK, null, currentStockTabPanel, null);
		tabbedPane.addTab(CommonClient.TAB_BUYERS, null, buyersTabPanel, null);
		tabbedPane.addTab(CommonClient.TAB_EMPLOYEES, null, employeesTabPanel, null);
		tabbedPane.addTab(CommonClient.TAB_STATISTICS, null, statisticsTabPanel, null);
		tabbedPane.addTab(CommonClient.TAB_LOGS, null, logsTabPanel, null);
		tabbedPane.addChangeListener(logic);

	}// setTabbedPane

	// Setting frame parameters
	private void setJFrameParams() {

		setTitle(CommonClient.CLIENT_MAIN_TITLE);
		setBounds(300, 200, 720, 450);
		setContentPane(contentPane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		setResizable(false);

	}// setJFrameParams

	// Show frame
	public void showPanel() {

		setVisible(true);

	}// showPanel

	// Getting logic
	public ClientMainLogic getLogic() {

		return logic;

	}// getLogic

	// Login operation before starting main client program
	public void login() {

		logic.loginOperation();

	}// login

	// Getting buy button
	public JButton getBuyButton() {

		return buyBtn;

	}// getBuyButton

	// Getting add employee button
	public JButton getAddEmployeeButton() {

		return addEmployeeBtn;

	}// getAddEmployeeButton

	// Getting delete employee button
	public JButton getDeleteEmployeeButton() {

		return deleteEmployeeBtn;

	}// getDeleteEmployeeButton

	// Getting custom pants stock value label
	public JLabel getCustomPantsValueLabel() {

		return customPantsValueLabel;

	}// getCustomPantsValueLabel

	// Getting sport pants stock value label
	public JLabel getSportPantsValueLabel() {

		return sportPantsValueLabel;

	}// getSportPantsValueLabel

	// Getting jeans stock value label
	public JLabel getJeansValueLabel() {

		return jeansValueLabel;

	}// getJeansValueLabel

	// Getting coats stock value label
	public JLabel getCoatsValueLabel() {

		return coatsValueLabel;

	}// getCoatsValueLabel

	// Getting sweaters stock value label
	public JLabel getSweatersValueLabel() {

		return sweatersValueLabel;

	}// getSweatersValueLabel

	// Getting button shirts stock value label
	public JLabel getButtonShirtsValueLabel() {

		return buttonShirtsValueLabel;

	}// getButtonShirtsValueLabel

	// Getting T-shirts stock value label
	public JLabel getTshirtsValueLabel() {

		return tshirtsValueLabel;

	}// getTshirtsValueLabel

	// Getting buyers table
	public JTable getBuyersTable() {

		return buyersTable;

	}// getBuyersTable

	// Getting employee table
	public JTable getEmployeesTable() {

		return employeesTable;

	}// getEmployeesTable

	// Getting label for branch name
	public JLabel getStockBranchTitleLabel() {

		return stockBranchTitleLabel;

	}// getStockBranchTitleLabel

	// Getting statistics text area
	public JTextArea getStatisticsTextArea() {

		return statisticsTextArea;

	}// getStatisticsTextArea
	
	
	// Getting log text area
	public JTextArea getLogsTextArea() {
		
		return logsTextArea;
		
	}// getLogsTextArea

	// Getting button group
	public ButtonGroup getButtonGroup() {
		
		return buttonGroup;
	
	}// getButtonGroup

	// Checking whether server is running
	public boolean isServerRunning() {

		return logic.getClient().isConnected();

	}// isServerRunning

}// class
