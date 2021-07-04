package com.marketserver.logic;

// Class for common constant values
public final class CommonServer {

	// ---------------- Network constants ---------------

	// Server port
	public static final int SERVER_PORT = 7000; 

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

	// -------------------- Files -------------------------

	// Files path
	public static final String EMPLOYEES_FILE = "src/Employees.json"; 
	public static final String BUYERS_FILE = "src/Buyers.json"; 
	public static final String STOCK_FILE = "src/Stocks.json"; 
	public static final String LOGIN_FILE = "src/Logins.json"; 
	public static final String STATISTICS_FILE = "src/Statistics.json"; 
	public static final String LOGS_FILE = "src/Logs.json"; 

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
	public static final String ID = "Id"; 
	public static final String FIRSTNAME = "First Name"; 
	public static final String LASTNAME = "Last Name"; 
	public static final String TELEPHONE = "Telephone"; 
	public static final String TYPE = "Type"; 
	public static final String BUYER_UPDATE_TYPE = "Type Update"; 
	
	// Buyer client types
	public static final String BUYER_TYPE_VIP = "Vip"; 
	public static final String BUYER_TYPE_NEW = "New"; 
	public static final String BUYER_TYPE_RETURNED = "Returned"; 

	// Employee JSON mapping keys
	public static final String EMPLOYEE_ROOT = "Employees"; 
	public static final String WORK_ID = "Work Id"; 
	public static final String ACCOUNT = "Account"; 
	public static final String POSITION = "Position"; 
	
	// Employee job position types
	public static final String JOB_POSITION_MANAGER = "Manager"; 
	public static final String JOB_POSITION_SELLER = "Seller"; 
	public static final String JOB_POSITION_CASHIER = "Cashier"; 

	// Login JSON mapping keys
	public static final String LOGIN_ROOT = "Logins"; 
	public static final String LOGIN_ADMIN_ARRAY = "Admins"; 
	public static final String LOGIN_USER_ARRAY = "Users"; 	
	public static final String PASSWORD = "Password"; 
	public static final String LOGIN_PERSON = "Person"; 
		
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

}// class
