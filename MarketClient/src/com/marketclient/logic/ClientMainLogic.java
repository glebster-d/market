package com.marketclient.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import com.marketclient.gui.AddBuyerDialog;
import com.marketclient.gui.AddEmployeeDialog;
import com.marketclient.gui.ClientMainUI;
import com.marketclient.gui.DeleteEmployeeDialog;
import com.marketclient.gui.LoginDialog;
import com.marketclient.gui.StockDialog;
import com.marketclient.main.CommonClient;

// Main UI logic
public class ClientMainLogic implements ActionListener, ChangeListener {

	private Client client;
	private ClientMainUI mainUI;
	private JSONManager jsonManager;

	private boolean isAdmin;

	private String branchName;
	private String workId;
	private String userName;

	private ArrayList<Employee> employeesList;
	private ArrayList<Buyer> buyersList;

	public ClientMainLogic(ClientMainUI mainUI) {

		this.mainUI = mainUI;
		client = new Client();
		jsonManager = new JSONManager(this);

	}// ctor

	// Getting client
	public Client getClient() {

		return client;

	}// getClient

	// Getting JSON manager
	public JSONManager getJsonManager() {

		return jsonManager;

	}// getJsonManager

	// Getting branch name
	public String getBranchName() {

		return branchName;

	}// getBranchName

	// Setting branch name
	public void setBranchName(String branchName) {

		if (branchName != null) {
			
			this.branchName = branchName;
		}

	}// setBranchName

	// Getting work id
	public String getWorkId() {

		return workId;

	}// getWorkId

	// Setting work id
	public void setWorkId(String workId) {

		if (workId != null) {
			
			this.workId = workId;
		}

	}// setWorkId

	// Getting name of user
	public String getUserName() {

		return userName;

	}// getUserName

	// Getting buyers array list
	public ArrayList<Buyer> getBuyersList() {

		return buyersList;

	}// getBuyersList

	// Getting employee array list
	public ArrayList<Employee> getEmployeesList() {

		return employeesList;

	}// getEmployeesList

	// Setting name of user
	public void setUserName(String userName) {

		if (userName != null) {
			
			this.userName = userName;
		}

	}// setUserName

	// Starting client
	public void startClient() {

		if (client != null) {
			
			client.startClient();
		}

	}// startClient

	// Which user is connected
	public boolean isAdminConnected() {

		return isAdmin;

	}// isAdminConnected

	// Setting which user is connected
	public void setConnectedUser(boolean isAdmin) {

		this.isAdmin = isAdmin;

	}// setConnectedUser

	// Login operation before starting main client program
	public void loginOperation() {

		LoginDialog loginDialog = new LoginDialog(mainUI);
		loginDialog.showDialog();

		if (!loginDialog.isCancelled()) {

			mainUI.getStockBranchTitleLabel().setText(branchName);

			setBranchStock();
			populateEmployeeTable();
			populateBuyersTable();

			if (!isAdminConnected()) {

				mainUI.getBuyButton().setEnabled(false);
				mainUI.getBuyButton().setToolTipText(CommonClient.ADMIN_ONLY_TOOLTIP);
				mainUI.getAddEmployeeButton().setEnabled(false);
				mainUI.getAddEmployeeButton().setToolTipText(CommonClient.ADMIN_ONLY_TOOLTIP);
				mainUI.getDeleteEmployeeButton().setEnabled(false);
				mainUI.getDeleteEmployeeButton().setToolTipText(CommonClient.ADMIN_ONLY_TOOLTIP);
			}

			loginDialog.dispose();
			mainUI.showPanel();

		} else {

			mainUI.dispose();
		}

	}// login

	// Setting stock to label fields
	public void setBranchStock() {

		if (!branchName.isEmpty()) {

			Stock stock = jsonManager.getBranchStock();

			mainUI.getCustomPantsValueLabel().setText(stock.getPantsCategory().getCustomPantsAmount());
			mainUI.getSportPantsValueLabel().setText(stock.getPantsCategory().getSportPantsAmount());
			mainUI.getJeansValueLabel().setText(stock.getPantsCategory().getJeansAmount());
			mainUI.getCoatsValueLabel().setText(stock.getOvergarmentCategory().getCoatsAmount());
			mainUI.getSweatersValueLabel().setText(stock.getOvergarmentCategory().getSweatersAmount());
			mainUI.getButtonShirtsValueLabel().setText(stock.getOvergarmentCategory().getButtonedShirtsAmount());
			mainUI.getTshirtsValueLabel().setText(stock.getOvergarmentCategory().getTShirtsAmount());
		}

	}// setBranchStock

	// Fill employee table from list
	public void populateEmployeeTable() {

		DefaultTableModel model = (DefaultTableModel) mainUI.getEmployeesTable().getModel();
		employeesList = jsonManager.getBranchEmployeesList();

		if (employeesList != null) {

			Iterator<Employee> iterator = employeesList.iterator();

			while (iterator.hasNext()) {

				Employee employee = iterator.next();
				addTableRow(model, employee);
			}
		}

	}// populateEmployeeTable

	// Fill buyers table from list
	public void populateBuyersTable() {

		DefaultTableModel model = (DefaultTableModel) mainUI.getBuyersTable().getModel();
		buyersList = jsonManager.getBuyersList();

		if (buyersList != null) {

			Iterator<Buyer> iterator = buyersList.iterator();

			while (iterator.hasNext()) {

				Buyer buyer = iterator.next();
				addTableRow(model, buyer);
			}
		}

	}// populateBuyersTable

	// Refreshing table when new object added
	public void refreshTableListOnAdding(Object object) {

		if (object != null) {

			if (object instanceof Employee) {

				DefaultTableModel model = (DefaultTableModel) mainUI.getEmployeesTable().getModel();
				addTableRow(model, (Employee) object);
			}

			if (object instanceof Buyer) {

				DefaultTableModel model = (DefaultTableModel) mainUI.getBuyersTable().getModel();
				addTableRow(model, object);
			}
		}

	}// refreshTableListOnAdding

	// Adding row to buyer table
	private void addTableRow(DefaultTableModel model, Object object) {

		Object[] rowData = null;

		if (object instanceof Employee) {

			rowData = new Object[7];
			rowData[0] = ((Employee) object).getWorkId();
			rowData[1] = ((Employee) object).getFirstName();
			rowData[2] = ((Employee) object).getLastName();
			rowData[3] = ((Employee) object).getId();
			rowData[4] = ((Employee) object).getAccount();
			rowData[5] = ((Employee) object).getTelephone();
			rowData[6] = ((Employee) object).getPosition();
		}

		if (object instanceof Buyer) {

			rowData = new Object[5];
			rowData[0] = ((Buyer) object).getId();
			rowData[1] = ((Buyer) object).getFirstName();
			rowData[2] = ((Buyer) object).getLastName();
			rowData[3] = ((Buyer) object).getTelephone();

			if (object instanceof VipBuyer) {

				rowData[4] = CommonClient.BUYER_TYPE_VIP;
			}

			if (object instanceof ReturnedBuyer) {

				rowData[4] = CommonClient.BUYER_TYPE_RETURNED;
			}

			if (object instanceof NewBuyer) {

				rowData[4] = CommonClient.BUYER_TYPE_NEW;
			}
		}

		model.addRow(rowData);

	}// addTableRow

	// Button click listener override method
	@Override
	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {

		case CommonClient.BUY:
			StockDialog buyDialog = new StockDialog(mainUI, CommonClient.BUY);
			buyDialog.showDialog();
			break;

		case CommonClient.SELL:
			StockDialog sellDialog = new StockDialog(mainUI, CommonClient.SELL);
			sellDialog.showDialog();
			break;

		case CommonClient.ADD_BUYER:
			AddBuyerDialog clientDialog = new AddBuyerDialog(mainUI);
			clientDialog.showDialog();
			break;		

		case CommonClient.ADD_EMPLOYEE:
			AddEmployeeDialog aEmployeeDialog = new AddEmployeeDialog(mainUI);
			aEmployeeDialog.showDialog();
			break;

		case CommonClient.DELETE_EMPLOYEE:
			DeleteEmployeeDialog dEmployeeDialog = new DeleteEmployeeDialog(mainUI);
			dEmployeeDialog.showDialog();
			break;

		case CommonClient.REFRESH:
			if (mainUI.getButtonGroup().getSelection() != null) {

				String button = mainUI.getButtonGroup().getSelection().getActionCommand();
				changeStatisticsInformation(button);
			} else {
				JOptionPane.showMessageDialog(mainUI, CommonClient.NO_STAT_CHOOSEN_FOR_REFRESH_MESSAGE, "",
						JOptionPane.INFORMATION_MESSAGE);
			}

			break;

		case CommonClient.SAVE:
			savingStatisticsToFile();
			break;

		case CommonClient.GET_LOGS:
			getCompleteLogs();
			break;

		}// switch

	}// actionPerformed

	// Getting logs
	private void getCompleteLogs() {

		StringBuilder builder = new StringBuilder();

		builder.append(CommonClient.LOGS_TITLE);
		builder.append("\n\n");

		if (!branchName.isEmpty()) {

			ArrayList<Log> logs = jsonManager.getSystemLogs();

			for (Log entry : logs) {

				String line = String.format("[ %s %s ] [Action: %s] [Person: %s] : %s%s", entry.getDate(), entry.getTime(),
						entry.getAction(), entry.getPerson(), entry.getDescription(), "\n");

				builder.append(line);
			}

			mainUI.getLogsTextArea().setText(builder.toString());
		}

	}// getCompleteLogs

	// Saving chosen statistics to file
	private void savingStatisticsToFile() {

		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy-hh-MM-ss");
		String fileName = format.format(new Date()) + ".txt";

		if (!mainUI.getStatisticsTextArea().getText().isEmpty()) {

			String userHomeFolder = System.getProperty("user.home");

			File textFile = new File(userHomeFolder, fileName);

			try (FileWriter file = new FileWriter(textFile)) {

				file.write(mainUI.getStatisticsTextArea().getText());
				file.flush();
				
				JOptionPane.showMessageDialog(mainUI, CommonClient.SAVED_TO_FILE, "",
						JOptionPane.INFORMATION_MESSAGE);
				
			} catch (IOException e) {

				System.out.println("IOException from SavingStatisticsToFile method. Message: " + e.getMessage());
			}
		} else {
			JOptionPane.showMessageDialog(mainUI, CommonClient.NO_STAT_CHOOSEN_MESSAGE, "",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}// savingStatisticsToFile

	// Changing statistics information according to radio button choice
	private void changeStatisticsInformation(String button) {

		switch (button) {

		case CommonClient.RADIO_BUTTON_ALL_SELLINGS:
			getCompleteStatistics();
			break;

		case CommonClient.RADIO_BUTTON_OVERGARMENT:
			getOvergarmentCategoryStatistics();
			break;

		case CommonClient.RADIO_BUTTON_PANTS:
			getPantsCategoryStatistics();
			break;

		case CommonClient.RADIO_BUTTON_VIP:
			getVipBuyers();
			break;
		}

	}// changeStatisticsInformation

	// Getting all VIP buyer of chain store
	private void getVipBuyers() {

		StringBuilder builder = new StringBuilder();

		builder.append(CommonClient.STAT_VIP_BUYERS_TITLE);
		builder.append("\n\n");

		if (!branchName.isEmpty()) {

			ArrayList<Buyer> statistics = jsonManager.getBuyersList();

			for (Buyer entry : statistics) {

				if (entry instanceof VipBuyer) {

					String line = String.format("[ ID: %s ] %s %s : Telephone: %s %s", entry.getId(),
							entry.getFirstName(), entry.getLastName(), entry.getTelephone(), "\n");

					builder.append(line);
				}
			}

			mainUI.getStatisticsTextArea().setText(builder.toString());
		}

	}// getVipBuyers

	// Getting pants category statistics for specific branch
	private void getPantsCategoryStatistics() {

		StringBuilder builder = new StringBuilder();

		builder.append(CommonClient.STAT_PANTS_CATEGORY_SELLING_TITLE);
		builder.append("\n\n");

		if (!branchName.isEmpty()) {

			ArrayList<Statistics> statistics = jsonManager.getBranchStatistics();

			for (Statistics entry : statistics) {

				if (entry.getCategory().equals(CommonClient.STOCK_PANTS)) {

					String line = String.format("[ %s %s ] [Category: %s] %s : %s%s", entry.getDate(), entry.getTime(),
							entry.getCategory(), entry.getItem(), entry.getAmount(), "\n");

					builder.append(line);
				}
			}

			mainUI.getStatisticsTextArea().setText(builder.toString());
		}

	}// getPantsCategoryStatistics

	// Getting overgarment category statistics for specific branch
	private void getOvergarmentCategoryStatistics() {

		StringBuilder builder = new StringBuilder();

		builder.append(CommonClient.STAT_OVERGARMENT_CATEGORY_SELLING_TITLE);
		builder.append("\n\n");

		if (!branchName.isEmpty()) {

			ArrayList<Statistics> statistics = jsonManager.getBranchStatistics();

			for (Statistics entry : statistics) {

				if (entry.getCategory().equals(CommonClient.STOCK_OVERGARMENT)) {

					String line = String.format("[ %s %s ] [Category: %s] %s : %s%s", entry.getDate(), entry.getTime(),
							entry.getCategory(), entry.getItem(), entry.getAmount(), "\n");

					builder.append(line);
				}
			}

			mainUI.getStatisticsTextArea().setText(builder.toString());
		}

	}// getOvergarmentCategoryStatistics

	// Getting complete statistics for specific branch
	private void getCompleteStatistics() {

		StringBuilder builder = new StringBuilder();

		builder.append(CommonClient.STAT_COMPLETE_TITLE);
		builder.append("\n\n");

		if (!branchName.isEmpty()) {

			ArrayList<Statistics> statistics = jsonManager.getBranchStatistics();

			for (Statistics entry : statistics) {

				String line = String.format("[ %s %s ] [Category: %s] %s : %s%s", entry.getDate(), entry.getTime(),
						entry.getCategory(), entry.getItem(), entry.getAmount(), "\n");

				builder.append(line);
			}

			mainUI.getStatisticsTextArea().setText(builder.toString());
		}

	}// getCompleteStatistics()

	// State changed listener
	@Override
	public void stateChanged(ChangeEvent e) {
		
		JTabbedPane pane = (JTabbedPane) e.getSource();
		int index = pane.getSelectedIndex();
		String title = pane.getTitleAt(index);
		
		if(title.equals(CommonClient.TAB_BUYERS)) {
			
			cleanBuyersTable();
			populateBuyersTable();
		}		
	}// stateChanged

	// Cleaning buyers table
	private void cleanBuyersTable() {
		
		DefaultTableModel model = (DefaultTableModel) mainUI.getBuyersTable().getModel();
		int numberOfRows = model.getRowCount();
		
		while(numberOfRows > 0) {
			
			model.removeRow(0);
			numberOfRows = model.getRowCount();
		}		
	}// cleanBuyersTable

}// class
