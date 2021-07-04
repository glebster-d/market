package com.marketclient.main;

// Class for common constant values
public final class CommonClient {

	// ---------------- Network constants ---------------

	// Server port
	public static final int SERVER_PORT = 7000; 
	public static final String SERVER_NOT_WORKING = "Server Not Working!";

	public static final String MSG_ROOT = "Message"; 

	// Response message
	public static final String RESPONSE_ADMIN = "ADMIN"; 
	public static final String RESPONSE_USER = "USER"; 
	public static final String RESPONSE_BAD = "BAD"; 
	public static final String RESPONSE_OK = "OK"; 
	
	// Request message
	public static final String REQUEST_DELETE = "DELETE"; 
	public static final String REQUEST_ADD = "ADD"; 
	public static final String REQUEST_READ = "READ"; 
	public static final String REQUEST_UPDATE = "UPDATE"; 

	// -------------------- JSON -------------------------

	// JSON mapping keys
	public static final String JSON_ROOT_TITLE = "Root"; 
	public static final String JSON_BRANCH_TITLE = "Branch"; 

	// Stock JSON mapping keys
	public static final String STOCK_ROOT = "Stocks"; 
	public static final String STOCK_PANTS = "Pants"; 
	public static final String STOCK_OVERGARMENT = "Overgarment"; 
	public static final String STOCK_T_SHIRT = "T-Shirts"; 
	public static final String STOCK_COAT = "Coats"; 
	public static final String STOCK_SWEATER = "Sweaters"; 
	public static final String STOCK_BUTTON_SHIRT = "Button Shirts"; 
	public static final String STOCK_CUSTOM_PANTS = "Custom Pants"; 
	public static final String STOCK_JEANS = "Jeans"; 
	public static final String STOCK_SPORT_PANTS = "Sport Pants"; 

	// Buyer JSON mapping keys
	public static final String BUYER_ROOT = "Buyers"; 

	// Employee JSON mapping keys
	public static final String EMPLOYEE_ROOT = "Employees"; 

	// Login JSON mapping keys
	public static final String LOGIN_ROOT = "Logins"; 
	public static final String PASSWORD = "Password"; 
	public static final String LOGIN_PERSON = "Person"; 
	public static final String LOGIN_ADMIN = "Admins"; 
	public static final String LOGIN_USER = "Users"; 

	// Statistics JSON mapping keys
	public static final String STAT_ROOT = "Statistics"; 
	public static final String STAT_DATE = "Date"; 
	public static final String STAT_TIME = "Time"; 
	public static final String STAT_CATEGORY = "Category"; 
	public static final String STAT_ITEM = "Item";
	public static final String STAT_AMOUNT = "Amount";

	// Logs JSON mapping keys
	public static final String LOGS_ROOT = "Logs"; 
	public static final String LOGS_ARRAY = "Log"; 
	public static final String LOGS_DATE = "Date"; 
	public static final String LOGS_TIME = "Time"; 
	public static final String LOGS_PERSON = "Person"; 
	public static final String LOGS_ACTION = "Action"; 
	public static final String LOGS_DESCR = "Description"; 

	// ---------------------- GUI constants ----------------------------

	// Application font
	public static final String FONT = "SansSerif";

	// Login dialog
	public static final String DIALOG_LOGIN_TITLE = "Login to system";
	public static final String LOGIN = "Login";
	public static final String CANCEL = "Cancel";
	public static final String EMPTY_FIELDS_MESSAGE = "Fields cannot be empty!";
	public static final String USER_NOT_EXIST_MESSAGE = "User not exist!";
	public static final String NO_CONNECTION_MESSAGE = "Cannot connect to server!";

	// Stock dialog
	public static final String WRONG_VALUE_MESSAGE = "Check your inputed values!";
	public static final String NOT_ENOUGH_ITEMS_IN_STOCK_MESSAGE = "Not enough items in stock!";
	public static final String TRANSACTION_ROLLUP_MESSAGE = "Cannot accomplish operation. Transaction is rollbacked.";
	public static final String TRANSACTION_OK_MESSAGE = "Transaction accomplished.";
	public static final String GET_DISCOUNT = "Get discount";
	public static final String NOT_BRANCH_BUYER_MESSAGE = "This buyer is not client of the branch!";
	public static final String AMOUNT = "Amount";
	public static final String PRICE = "Price";
	public static final String PRICE_T_SHIRT = "79.90";
	public static final String PRICE_COAT = "450.00";
	public static final String PRICE_BUTTON_SHIRT = "119.00";
	public static final String PRICE_SWEATER = "139.99";
	public static final String PRICE_JEANS = "179.99";
	public static final String PRICE_SPORT_PANTS = "150.50";
	public static final String PRICE_CUSTOM_PANTS = "200.50";

	// Add client dialog
	public static final String NEW_BUYER_ADDED_MESSAGE = "New buyer added.";
	public static final String NEW_BUYER_NOT_ADDED_MESSAGE = "New buyer was not added! Try another time.";

	// Add employee dialog
	public static final String EMPLOYEE_ADDED_MESSAGE = "Employee added.";
	public static final String EMPLOYEE_NOT_ADDED_MESSAGE = "Employee was not added. Try another time.";

	// Delete employee dialog
	public static final String EMPLOYEE_DELETED_MESSAGE = "Employee deleted.";
	public static final String EMPLOYEE_NOT_DELETED_MESSAGE = "Employee was not deleted. Try another time.";

	// Client Main
	public static final String CLIENT_MAIN_TITLE = "Client";
	public static final String ADMIN_ONLY_TOOLTIP = "Allowed only for administrator";
	public static final String CANNOT_LOAD_BUYERS = "Cannot load buyers list.";
	public static final String CANNOT_LOAD_EMPLOYEES = "Cannot load employees list.";
	public static final String CANNOT_LOAD_STOCK = "Cannot load stock.";
	public static final Object CANNOT_LOAD_STAT = "Cannot load statistics.";	
	public static final String NO_STAT_CHOOSEN_MESSAGE = "No information to store. Refresh statistics";	
	public static final String NO_STAT_CHOOSEN_FOR_REFRESH_MESSAGE = "Choose statistics before refreshing!";
	public static final String CANNOT_LOAD_LOGS = "Cannot load system logs.";

	// Stock tab components
	public static final String STOCK_LABEL_TITLE = "Current stock in:";
	public static final String PANTS_CATEGORY_TITLE = "Pants Category";
	public static final String OVERGARMENT_CATEGORY_TITLE = "Overgarment Category";
	public static final String SELL = "Sell";
	public static final String BUY = "Buy";

	// Buyers tab components
	public static final String CLIENTS_SCROLL_PANEL_TITLE = "Clients List:";
	public static final String ADD_BUYER = "Add Buyer";
	public static final String DISCOUNTS = "Discounts";

	public static final String ID = "Id"; 
	public static final String FIRSTNAME = "First Name"; 
	public static final String LASTNAME = "Last Name"; 
	public static final String TELEPHONE = "Telephone"; 
	public static final String TYPE = "Type"; 
	public static final String BUYER_UPDATE_TYPE = "Type Update"; 

	public static final String BUYER_TYPE_VIP = "Vip"; 
	public static final String BUYER_TYPE_NEW = "New"; 
	public static final String BUYER_TYPE_RETURNED = "Returned"; 

	// Employee tab components
	public static final String EMPLOYEES_SCROLL_PANEL_TITLE = "Employees List:";
	public static final String ADD_EMPLOYEE = "Add Employee";
	public static final String DELETE_EMPLOYEE = "Delete Employee";

	public static final String WORK_ID = "Work Id"; 
	public static final String ACCOUNT = "Account"; 
	public static final String POSITION = "Position"; 

	public static final String JOB_POSITION_MANAGER = "Manager"; 
	public static final String JOB_POSITION_SELLER = "Seller"; 
	public static final String JOB_POSITION_CASHIER = "Cashier"; 

	// Statistics tab components
	public static final String RADIO_BUTTON_ALL_SELLINGS = "All sellings";
	public static final String RADIO_BUTTON_PANTS = "Pants category";
	public static final String RADIO_BUTTON_OVERGARMENT = "Overgarment category";
	public static final String RADIO_BUTTON_VIP = "VIP clients";
	public static final String REFRESH = "Refresh";
	public static final String SAVE = "Save to file";
	public static final String STAT_OVERGARMENT_CATEGORY_SELLING_TITLE = "OVERGARMENT CATEGORY SELLING STATISTICS";
	public static final String STAT_PANTS_CATEGORY_SELLING_TITLE = "PANTS CATEGORY SELLING STATISTICS";
	public static final String STAT_COMPLETE_TITLE = "COMPLETE BRANCH STATISTICS";
	public static final String STAT_VIP_BUYERS_TITLE ="VIP BUYERS";		
	public static final String SAVED_TO_FILE = "Saved to file!";
	
	// Logs tab components
	public static final String GET_LOGS = "Get Logs";
	public static final String LOGS_TITLE = "SYSTEM LOGS";	

	// Tabs titles
	public static final String TAB_CURRENT_STOCK = "Current Stock";
	public static final String TAB_BUYERS = "Buyers";
	public static final String TAB_EMPLOYEES = "Employees";
	public static final String TAB_STATISTICS = "Statistics";
	public static final String TAB_LOGS = "Logs";		

}// class
