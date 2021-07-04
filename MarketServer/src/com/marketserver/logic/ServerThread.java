package com.marketserver.logic;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// Thread for server's main CRUD operations
@SuppressWarnings("unchecked")
public class ServerThread extends Thread {

	private boolean isReseted;
	private SocketData socketData;
	private FileManager fileManager;

	public ServerThread(SocketData socketData) {

		this.socketData = socketData;
		isReseted = false;
		fileManager = FileManager.getInstance();

	}// ctor

	@Override
	public void run() {

		super.run();

		while (!isReseted) {
			analyzeRequests();
		}

	} // run

	// Analyzing request according to request header
	private void analyzeRequests() {

		JSONParser parser = new JSONParser();

		try {
			JSONObject parsedObject = (JSONObject) parser.parse(socketData.getInputStream().readUTF().toString());
			String root = (String) parsedObject.get(CommonServer.JSON_ROOT_TITLE);

			switch (root) {
			
			case CommonServer.LOGIN_ROOT:
				proceedRequestForLoginObject(parsedObject);
				break;
			
			case CommonServer.STOCK_ROOT:
				proceedRequestForStockObject(parsedObject);
				break;
			
			case CommonServer.EMPLOYEE_ROOT:
				proceedRequestForEmployeeObject(parsedObject);
				break;
			
			case CommonServer.BUYER_ROOT:
				proceedRequestForBuyerObject(parsedObject);
				break;

			case CommonServer.STAT_ROOT:
				proceedRequestForStatisticsObject(parsedObject);
				break;

			case CommonServer.LOGS_ROOT:
				proceedRequestForLogsObject(parsedObject);
				break;
			}
		} 
		catch (IOException | ParseException e) {

			System.out.println("Exception from AnalyzeRequests method. Message: " + e.getMessage());
			isReseted = true;
		}

	}// analyzeRequests

	// Switch to logging operation according to request
	private void proceedRequestForLogsObject(JSONObject object) {

		String request = (String) object.get(CommonServer.MSG_ROOT);

		switch (request) {

		case CommonServer.REQUEST_READ:
			getLogs(object);
			break;

		case CommonServer.REQUEST_ADD:
			addLog(object);
			break;
		}
		
	}// proceedRequestForLogsObject

	// Updating logs in specific branch	
	private void addLog(JSONObject object) {
		
		JSONObject jsonDataFromFile = (JSONObject) fileManager.readFromJsonFile(CommonServer.LOGS_FILE);
		JSONObject jsonRootObject = (JSONObject) jsonDataFromFile.get(CommonServer.LOGS_ROOT);
		String branchName = (String) object.get(CommonServer.JSON_BRANCH_TITLE);

		JSONObject responseObject = new JSONObject();
		responseObject.put(CommonServer.JSON_ROOT_TITLE, CommonServer.LOGS_ROOT);
		responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_BAD);
		
		if (jsonDataFromFile != null) {

			JSONArray logs = (JSONArray) jsonRootObject.get(branchName);

			JSONObject objectToAdd = new JSONObject();
			objectToAdd.put(CommonServer.LOGS_DATE, object.get(CommonServer.LOGS_DATE));
			objectToAdd.put(CommonServer.LOGS_TIME, object.get(CommonServer.LOGS_TIME));
			objectToAdd.put(CommonServer.LOGS_ACTION, object.get(CommonServer.LOGS_ACTION));
			objectToAdd.put(CommonServer.LOGS_PERSON, object.get(CommonServer.LOGS_PERSON));
			objectToAdd.put(CommonServer.LOGS_DESCR, object.get(CommonServer.LOGS_DESCR));

			logs.add(objectToAdd);
			jsonRootObject.put(branchName, logs);
			jsonDataFromFile.put(CommonServer.LOGS_ROOT, jsonRootObject);
			fileManager.writeToJsonFile(CommonServer.LOGS_FILE, jsonDataFromFile);
			responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_OK);
		}

		try {
			socketData.getOutputStream().writeUTF(responseObject.toJSONString());
			socketData.getOutputStream().flush();
		} 
		catch (IOException e) {

			System.out.println("IOException from UpdateStatistics method. Message: " + e.getMessage());
		}
		
	}// addLog

	// Getting logs of specific branch
	private void getLogs(JSONObject object) {
		
		JSONObject jsonDataFromFile = (JSONObject) fileManager.readFromJsonFile(CommonServer.LOGS_FILE);
		JSONObject jsonRootObject = (JSONObject) jsonDataFromFile.get(CommonServer.LOGS_ROOT);
		String branchName = (String) object.get(CommonServer.JSON_BRANCH_TITLE);

		JSONObject responseObject = new JSONObject();
		responseObject.put(CommonServer.JSON_ROOT_TITLE, CommonServer.LOGS_ROOT);
		responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_BAD);
		
		if (jsonDataFromFile != null) {

			JSONArray logs = (JSONArray) jsonRootObject.get(branchName);
			responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_OK);
			responseObject.put(branchName, logs);
		}

		try {
			socketData.getOutputStream().writeUTF(responseObject.toJSONString());
			socketData.getOutputStream().flush();
		} 
		catch (IOException e) {

			System.out.println("IOException from GetStatistics method. Message: " + e.getMessage());
		}
		
	}// getLogs

	// Switch to statistics operation according to request
	private void proceedRequestForStatisticsObject(JSONObject object) {

		String request = (String) object.get(CommonServer.MSG_ROOT);

		switch (request) {

		case CommonServer.REQUEST_READ:
			getStatistics(object);
			break;

		case CommonServer.REQUEST_ADD:
			addStatistics(object);
			break;
		}

	}// proceedRequestForStatisticsObject

	// Updating statistics in specific branch
	private void addStatistics(JSONObject object) {

		JSONObject jsonDataFromFile = (JSONObject) fileManager.readFromJsonFile(CommonServer.STATISTICS_FILE);
		JSONObject jsonRootObject = (JSONObject) jsonDataFromFile.get(CommonServer.STAT_ROOT);
		String branchName = (String) object.get(CommonServer.JSON_BRANCH_TITLE);

		JSONObject responseObject = new JSONObject();
		responseObject.put(CommonServer.JSON_ROOT_TITLE, CommonServer.STAT_ROOT);
		responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_BAD);

		if (jsonDataFromFile != null) { 

			JSONArray statistics = (JSONArray) jsonRootObject.get(branchName);
			JSONArray pantsArray = (JSONArray) object.get(CommonServer.STOCK_PANTS);
			JSONArray overgarmentArray = (JSONArray) object.get(CommonServer.STOCK_OVERGARMENT);
			
			getValuesFromCategoryArray(statistics, pantsArray, object, CommonServer.STOCK_PANTS);
			getValuesFromCategoryArray(statistics, overgarmentArray, object, CommonServer.STOCK_OVERGARMENT);
			
			jsonRootObject.put(branchName, statistics);
			jsonDataFromFile.put(CommonServer.STAT_ROOT, jsonRootObject);
			fileManager.writeToJsonFile(CommonServer.STATISTICS_FILE, jsonDataFromFile);
			responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_OK);
		}

		try {
			socketData.getOutputStream().writeUTF(responseObject.toJSONString());
			socketData.getOutputStream().flush();
		} catch (IOException e) {

			System.out.println("IOException from UpdateStatistics method. Message: " + e.getMessage());
		}

	}// addStatistics

	// Getting values from category array and updating the statistics array
	private void getValuesFromCategoryArray(JSONArray statArray, JSONArray categoryArray, JSONObject recievedObject, String category) {
		
		Iterator<JSONObject> categoryArrayIterator = categoryArray.iterator();
		
		while(categoryArrayIterator.hasNext()) {
			
			JSONObject objectToAdd = new JSONObject();
			objectToAdd.put(CommonServer.STAT_DATE, recievedObject.get(CommonServer.STAT_DATE));
			objectToAdd.put(CommonServer.STAT_TIME, recievedObject.get(CommonServer.STAT_TIME));
			objectToAdd.put(CommonServer.STAT_CATEGORY, category);
			
			JSONObject pantsArrayValue = categoryArrayIterator.next();				
			Set<String> valueKeys = pantsArrayValue.keySet();
			Iterator<String> keysIterator = valueKeys.iterator();
			
			while(keysIterator.hasNext()) {
				
				String item = keysIterator.next();
				String value = (String) pantsArrayValue.get(item);
				objectToAdd.put(CommonServer.STAT_ITEM, item);
				objectToAdd.put(CommonServer.STAT_AMOUNT, value);
			}
			
			statArray.add(objectToAdd);	
		}		
		
	}// getValuesFromCategoryArray

	// Getting statistics of specific branch
	private void getStatistics(JSONObject object) {

		JSONObject jsonDataFromFile = (JSONObject) fileManager.readFromJsonFile(CommonServer.STATISTICS_FILE);
		JSONObject jsonRootObject = (JSONObject) jsonDataFromFile.get(CommonServer.STAT_ROOT);
		String branchName = (String) object.get(CommonServer.JSON_BRANCH_TITLE);

		JSONObject responseObject = new JSONObject();
		responseObject.put(CommonServer.JSON_ROOT_TITLE, CommonServer.STAT_ROOT);
		responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_BAD);

		if (jsonDataFromFile != null) {

			JSONArray statistics = (JSONArray) jsonRootObject.get(branchName);
			responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_OK);
			responseObject.put(branchName, statistics);
		}

		try {
			socketData.getOutputStream().writeUTF(responseObject.toJSONString());
			socketData.getOutputStream().flush();
		} catch (IOException e) {

			System.out.println("IOException from GetStatistics method. Message: " + e.getMessage());
		}

	}// getStatistics

	// Switch to stock operation according to request
	private void proceedRequestForStockObject(JSONObject object) {

		String request = (String) object.get(CommonServer.MSG_ROOT);

		switch (request) {

		case CommonServer.REQUEST_READ:
			getStock(object);
			break;

		case CommonServer.REQUEST_UPDATE:
			updateStock(object);
			break;
		}

	}// proceedRequestForStockObject

	// Updating stock in specific branch
	private void updateStock(JSONObject object) {

		JSONObject jsonDataFromFile = (JSONObject) fileManager.readFromJsonFile(CommonServer.STOCK_FILE);
		JSONObject jsonRootObject = (JSONObject) jsonDataFromFile.get(CommonServer.STOCK_ROOT);
		String branchName = (String) object.get(CommonServer.JSON_BRANCH_TITLE);

		JSONObject responseObject = new JSONObject();
		responseObject.put(CommonServer.JSON_ROOT_TITLE, CommonServer.STOCK_ROOT);
		responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_BAD);

		if (jsonDataFromFile != null) {

			JSONObject stock = (JSONObject) jsonRootObject.get(branchName);

			JSONObject pants = (JSONObject) stock.get(CommonServer.STOCK_PANTS);
			pants.put(CommonServer.STOCK_JEANS, object.get(CommonServer.STOCK_JEANS));
			pants.put(CommonServer.STOCK_CUSTOM_PANTS, object.get(CommonServer.STOCK_CUSTOM_PANTS));
			pants.put(CommonServer.STOCK_SPORT_PANTS, object.get(CommonServer.STOCK_SPORT_PANTS));

			JSONObject overgarment = (JSONObject) stock.get(CommonServer.STOCK_OVERGARMENT);
			overgarment.put(CommonServer.STOCK_BUTTON_SHIRT, object.get(CommonServer.STOCK_BUTTON_SHIRT));
			overgarment.put(CommonServer.STOCK_COAT, object.get(CommonServer.STOCK_COAT));
			overgarment.put(CommonServer.STOCK_SWEATER, object.get(CommonServer.STOCK_SWEATER));
			overgarment.put(CommonServer.STOCK_T_SHIRT, object.get(CommonServer.STOCK_T_SHIRT));

			stock.put(CommonServer.STOCK_PANTS, pants);
			stock.put(CommonServer.STOCK_OVERGARMENT, overgarment);
			jsonRootObject.put(branchName, stock);
			jsonDataFromFile.put(CommonServer.STOCK_ROOT, jsonRootObject);
			fileManager.writeToJsonFile(CommonServer.STOCK_FILE, jsonDataFromFile);
			responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_OK);
		}

		try {
			socketData.getOutputStream().writeUTF(responseObject.toJSONString());
			socketData.getOutputStream().flush();
		} catch (IOException e) {

			System.out.println("IOException from UpdateStock method. Message: " + e.getMessage());
		}

	}// updateStock

	// Getting stock of specific branch
	private void getStock(JSONObject object) {

		JSONObject jsonDataFromFile = (JSONObject) fileManager.readFromJsonFile(CommonServer.STOCK_FILE);
		JSONObject jsonRootObject = (JSONObject) jsonDataFromFile.get(CommonServer.STOCK_ROOT);
		String branchName = (String) object.get(CommonServer.JSON_BRANCH_TITLE);

		JSONObject responseObject = new JSONObject();
		responseObject.put(CommonServer.JSON_ROOT_TITLE, CommonServer.STOCK_ROOT);
		responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_BAD);

		if (jsonDataFromFile != null) {

			JSONObject stock = (JSONObject) jsonRootObject.get(branchName);

			responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_OK);
			responseObject.put(branchName, stock);
		}

		try {
			socketData.getOutputStream().writeUTF(responseObject.toJSONString());
			socketData.getOutputStream().flush();
		} catch (IOException e) {

			System.out.println("IOException from GetStock method. Message: " + e.getMessage());
		}

	}// getStock

	// Switch to buyer operation according to request
	private void proceedRequestForBuyerObject(JSONObject object) {

		String request = (String) object.get(CommonServer.MSG_ROOT);

		switch (request) {

		case CommonServer.REQUEST_READ:
			getBuyers();
			break;

		case CommonServer.REQUEST_ADD:
			addBuyer(object);
			break;

		case CommonServer.REQUEST_UPDATE:
			updateBuyer(object);
			break;
		}

	}// proceedRequestForBuyerObject

	// Updating buyer in specific array
	private void updateBuyer(JSONObject object) {

		JSONArray arrayOld = null, arrayNew = null;
		JSONObject jsonDataFromFile = (JSONObject) fileManager.readFromJsonFile(CommonServer.BUYERS_FILE);
		JSONObject jsonRootObject = (JSONObject) jsonDataFromFile.get(CommonServer.BUYER_ROOT);

		String type = (String) object.get(CommonServer.TYPE);
		String typeToUpdate = (String) object.get(CommonServer.BUYER_UPDATE_TYPE);

		JSONObject responseObject = new JSONObject();
		responseObject.put(CommonServer.JSON_ROOT_TITLE, CommonServer.BUYER_ROOT);
		responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_BAD);

		if (jsonDataFromFile != null) {

			JSONObject objectToUpdate = new JSONObject();
			objectToUpdate.put(CommonServer.FIRSTNAME, object.get(CommonServer.FIRSTNAME));
			objectToUpdate.put(CommonServer.LASTNAME, object.get(CommonServer.LASTNAME));
			objectToUpdate.put(CommonServer.TELEPHONE, object.get(CommonServer.TELEPHONE));
			objectToUpdate.put(CommonServer.ID, object.get(CommonServer.ID));

			arrayOld = (JSONArray) jsonRootObject.get(type);

			if (arrayOld.remove(objectToUpdate)) {

				arrayNew = (JSONArray) jsonRootObject.get(typeToUpdate);
				arrayNew.add(objectToUpdate);

				jsonRootObject.put(type, arrayOld);
				jsonRootObject.put(typeToUpdate, arrayNew);
				jsonDataFromFile.put(CommonServer.BUYER_ROOT, jsonRootObject);
				fileManager.writeToJsonFile(CommonServer.BUYERS_FILE, jsonDataFromFile);
				responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_OK);
			}
		}

		try {
			socketData.getOutputStream().writeUTF(responseObject.toJSONString());
			socketData.getOutputStream().flush();
		} catch (IOException e) {

			System.out.println("IOException from UpdateBuyer method. Message: " + e.getMessage());
		}

	}// updateBuyer

	// Adding buyer to type array
	private void addBuyer(JSONObject object) {
				
		JSONObject jsonDataFromFile = (JSONObject) fileManager.readFromJsonFile(CommonServer.BUYERS_FILE);
		JSONObject jsonRootObject = (JSONObject) jsonDataFromFile.get(CommonServer.BUYER_ROOT);
		String type = (String) object.get(CommonServer.TYPE);

		JSONObject responseObject = new JSONObject();
		responseObject.put(CommonServer.JSON_ROOT_TITLE, CommonServer.BUYER_ROOT);
		responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_BAD);

		if (jsonDataFromFile != null) {

			JSONObject objectToAdd  = new JSONObject();
			objectToAdd.put(CommonServer.FIRSTNAME, object.get(CommonServer.FIRSTNAME));
			objectToAdd.put(CommonServer.LASTNAME, object.get(CommonServer.LASTNAME));
			objectToAdd.put(CommonServer.TELEPHONE, object.get(CommonServer.TELEPHONE));
			objectToAdd.put(CommonServer.ID, object.get(CommonServer.ID));

			JSONArray array = (JSONArray) jsonRootObject.get(type);
			array.add(objectToAdd);
			jsonRootObject.put(type, array);
			jsonDataFromFile.put(CommonServer.BUYER_ROOT, jsonRootObject);
			fileManager.writeToJsonFile(CommonServer.BUYERS_FILE, jsonDataFromFile);
			responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_OK);			
		}

		try {
			socketData.getOutputStream().writeUTF(responseObject.toJSONString());
			socketData.getOutputStream().flush();			
		} 
		catch (IOException e) {

			System.out.println("IOException from AddBuyer method. Message: " + e.getMessage());
		}	
	}// addBuyer

	// // Getting buyers arrays from JSON file
	private void getBuyers() {

		JSONObject jsonDataFromFile = (JSONObject) fileManager.readFromJsonFile(CommonServer.BUYERS_FILE);
		JSONObject jsonRootObject = (JSONObject) jsonDataFromFile.get(CommonServer.BUYER_ROOT);

		JSONObject responseObject = new JSONObject();
		responseObject.put(CommonServer.JSON_ROOT_TITLE, CommonServer.BUYER_ROOT);
		responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_BAD);

		if (jsonDataFromFile != null) {

			JSONArray arrayNew = (JSONArray) jsonRootObject.get(CommonServer.BUYER_TYPE_NEW);
			JSONArray arrayRet = (JSONArray) jsonRootObject.get(CommonServer.BUYER_TYPE_RETURNED);
			JSONArray arrayVip = (JSONArray) jsonRootObject.get(CommonServer.BUYER_TYPE_VIP);

			responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_OK);
			responseObject.put(CommonServer.BUYER_TYPE_NEW, arrayNew);
			responseObject.put(CommonServer.BUYER_TYPE_RETURNED, arrayRet);
			responseObject.put(CommonServer.BUYER_TYPE_VIP, arrayVip);
		}

		try {
			socketData.getOutputStream().writeUTF(responseObject.toJSONString());
			socketData.getOutputStream().flush();
		} catch (IOException e) {

			System.out.println("IOException from GetBuyers method. Message: " + e.getMessage());
		}

	}// getBuyers

	// Switch to employee operation according to request
	private void proceedRequestForEmployeeObject(JSONObject object) {

		String request = (String) object.get(CommonServer.MSG_ROOT);

		switch (request) {

		case CommonServer.REQUEST_READ:
			getEmployees(object);
			break;

		case CommonServer.REQUEST_DELETE:
			deleteEmployee(object);
			break;

		case CommonServer.REQUEST_ADD:
			addEmployee(object);
			break;

		case CommonServer.REQUEST_UPDATE:
			updateEmployee(object);
			break;
		}

	}// proceedRequestForEmployeeObject

	// Updating employee in specific branch
	private void updateEmployee(JSONObject object) {

		JSONObject jsonDataFromFile = (JSONObject) fileManager.readFromJsonFile(CommonServer.EMPLOYEES_FILE);
		JSONObject jsonRootObject = (JSONObject) jsonDataFromFile.get(CommonServer.EMPLOYEE_ROOT);
		String branchName = (String) object.get(CommonServer.JSON_BRANCH_TITLE);

		JSONObject responseObject = new JSONObject();
		responseObject.put(CommonServer.JSON_ROOT_TITLE, CommonServer.EMPLOYEE_ROOT);
		responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_BAD);

		if (jsonDataFromFile != null) {

			JSONArray employees = (JSONArray) jsonRootObject.get(branchName);

			JSONObject objectToAdd = new JSONObject();
			objectToAdd.put(CommonServer.FIRSTNAME, object.get(CommonServer.FIRSTNAME));
			objectToAdd.put(CommonServer.LASTNAME, object.get(CommonServer.LASTNAME));
			objectToAdd.put(CommonServer.TELEPHONE, object.get(CommonServer.TELEPHONE));
			objectToAdd.put(CommonServer.ID, object.get(CommonServer.ID));
			objectToAdd.put(CommonServer.WORK_ID, object.get(CommonServer.WORK_ID));
			objectToAdd.put(CommonServer.ACCOUNT, object.get(CommonServer.ACCOUNT));
			objectToAdd.put(CommonServer.POSITION, object.get(CommonServer.POSITION));
			
			Iterator<JSONObject> iterator = employees.iterator();
			
			while(iterator.hasNext()) {
				
				JSONObject obj = iterator.next();
				
				if (obj.get(CommonServer.ID).equals(objectToAdd.get(CommonServer.ID))) {
					employees.add(objectToAdd);
					break;
				}
			}		

			jsonRootObject.put(branchName, employees);
			jsonDataFromFile.put(CommonServer.EMPLOYEE_ROOT, jsonRootObject);
			fileManager.writeToJsonFile(CommonServer.EMPLOYEES_FILE, jsonDataFromFile);
			responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_OK);
		}

		try {
			socketData.getOutputStream().writeUTF(responseObject.toJSONString());
			socketData.getOutputStream().flush();
		} catch (IOException e) {

			System.out.println("IOException from UpdateEmployee method. Message: " + e.getMessage());
		}

	}// updateEmployee

	// Adding employee to specific branch
	private void addEmployee(JSONObject object) {

		JSONObject jsonDataFromFile = (JSONObject) fileManager.readFromJsonFile(CommonServer.EMPLOYEES_FILE);
		JSONObject responseObject = new JSONObject();
		responseObject.put(CommonServer.JSON_ROOT_TITLE, CommonServer.EMPLOYEE_ROOT);
		responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_BAD);

		if (jsonDataFromFile != null) {
			
			String branchName = (String) object.get(CommonServer.JSON_BRANCH_TITLE);
			JSONObject jsonRootObject = (JSONObject) jsonDataFromFile.get(CommonServer.EMPLOYEE_ROOT);
			JSONArray employees = (JSONArray) jsonRootObject.get(branchName);

			JSONObject objectToAdd = new JSONObject();
			objectToAdd.put(CommonServer.FIRSTNAME, object.get(CommonServer.FIRSTNAME));
			objectToAdd.put(CommonServer.LASTNAME, object.get(CommonServer.LASTNAME));
			objectToAdd.put(CommonServer.TELEPHONE, object.get(CommonServer.TELEPHONE));
			objectToAdd.put(CommonServer.ID, object.get(CommonServer.ID));
			objectToAdd.put(CommonServer.WORK_ID, object.get(CommonServer.WORK_ID));
			objectToAdd.put(CommonServer.ACCOUNT, object.get(CommonServer.ACCOUNT));
			objectToAdd.put(CommonServer.POSITION, object.get(CommonServer.POSITION));
			
			employees.add(objectToAdd);
			jsonRootObject.put(branchName, employees);
			jsonDataFromFile.put(CommonServer.EMPLOYEE_ROOT, jsonRootObject);
			fileManager.writeToJsonFile(CommonServer.EMPLOYEES_FILE, jsonDataFromFile);
			responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_OK);
		}

		try {
			socketData.getOutputStream().writeUTF(responseObject.toJSONString());
			socketData.getOutputStream().flush();
		} catch (IOException e) {

			System.out.println("IOException from AddEmployee method. Message: " + e.getMessage());
		}

	}// addEmployee

	// Deleting employee from specific branch array
	private void deleteEmployee(JSONObject object) {

		String responseMsg = CommonServer.RESPONSE_BAD;
		JSONObject jsonDataFromEmployeeFile = (JSONObject) fileManager.readFromJsonFile(CommonServer.EMPLOYEES_FILE);
		JSONObject jsonRootObject = (JSONObject) jsonDataFromEmployeeFile.get(CommonServer.EMPLOYEE_ROOT);
		String branchName = (String) object.get(CommonServer.JSON_BRANCH_TITLE);

		JSONObject responseObject = new JSONObject();
		responseObject.put(CommonServer.JSON_ROOT_TITLE, CommonServer.EMPLOYEE_ROOT);
		responseObject.put(CommonServer.MSG_ROOT, responseMsg);

		if (jsonDataFromEmployeeFile != null) {

			JSONArray employees = (JSONArray) jsonRootObject.get(branchName);
			Iterator<JSONObject> iterator = employees.iterator();

			while (iterator.hasNext()) {

				JSONObject temp = iterator.next();

				if (temp.get(CommonServer.ID).equals(object.get(CommonServer.ID))) {

					if (temp.get(CommonServer.WORK_ID).equals(object.get(CommonServer.WORK_ID))) {
						
						JSONObject login = new JSONObject();
						login.put(CommonServer.WORK_ID, temp.get(CommonServer.WORK_ID));
						login.put(CommonServer.JSON_BRANCH_TITLE, branchName);
						login.put(CommonServer.LOGIN_PERSON, temp.get(CommonServer.FIRSTNAME) + " " + temp.get(CommonServer.LASTNAME));
						deleteLogin(login);
						
						responseMsg = employees.remove(temp) ? CommonServer.RESPONSE_OK : CommonServer.RESPONSE_BAD;
						break;
					}
				}
			}

			jsonRootObject.put(branchName, employees);
			jsonDataFromEmployeeFile.put(CommonServer.EMPLOYEE_ROOT, jsonRootObject);
			fileManager.writeToJsonFile(CommonServer.EMPLOYEES_FILE, jsonDataFromEmployeeFile);
			responseObject.put(CommonServer.MSG_ROOT, responseMsg);
		}

		try {
			socketData.getOutputStream().writeUTF(responseObject.toJSONString());
			socketData.getOutputStream().flush();
		} catch (IOException e) {

			System.out.println("IOException from DeleteEmployee method. Message: " + e.getMessage());
		}

	}// deleteEmployee

	// Getting employee list from JSON file
	private void getEmployees(JSONObject object) {

		JSONObject jsonDataFromFile = (JSONObject) fileManager.readFromJsonFile(CommonServer.EMPLOYEES_FILE);
		JSONObject jsonRootObject = (JSONObject) jsonDataFromFile.get(CommonServer.EMPLOYEE_ROOT);
		String branchName = (String) object.get(CommonServer.JSON_BRANCH_TITLE);

		JSONObject responseObject = new JSONObject();
		responseObject.put(CommonServer.JSON_ROOT_TITLE, CommonServer.EMPLOYEE_ROOT);
		responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_BAD);

		if (jsonDataFromFile != null) {

			JSONArray employees = (JSONArray) jsonRootObject.get(branchName);
			responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_OK);
			responseObject.put(branchName, employees);
		}

		try {
			socketData.getOutputStream().writeUTF(responseObject.toJSONString());
			socketData.getOutputStream().flush();
		} catch (IOException e) {

			System.out.println("IOException from GetEmployees method. Message: " + e.getMessage());
		}

	}// getEmployees

	// Switch to login operation according to request
	private void proceedRequestForLoginObject(JSONObject object) {

		String request = (String) object.get(CommonServer.MSG_ROOT);

		switch (request) {

		case CommonServer.REQUEST_READ:
			loginChecking(object);
			break;

		case CommonServer.REQUEST_ADD:
			addLogin(object);
			break;
		}

	}// proceedRequestForLoginObject

	// Adding login to type array
	private void addLogin(JSONObject object) {

		JSONObject jsonDataFromFile = (JSONObject) fileManager.readFromJsonFile(CommonServer.LOGIN_FILE);
		
		JSONObject responseObject = new JSONObject();
		responseObject.put(CommonServer.JSON_ROOT_TITLE, CommonServer.LOGIN_ROOT);
		responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_BAD);
		
		if (jsonDataFromFile != null) {
			
			JSONObject jsonRootObject = (JSONObject) jsonDataFromFile.get(CommonServer.LOGIN_ROOT);
			
			JSONObject objectToAdd = new JSONObject();
			objectToAdd.put(CommonServer.JSON_BRANCH_TITLE, object.get(CommonServer.JSON_BRANCH_TITLE));
			objectToAdd.put(CommonServer.PASSWORD, object.get(CommonServer.PASSWORD));
			objectToAdd.put(CommonServer.WORK_ID, object.get(CommonServer.WORK_ID));
			objectToAdd.put(CommonServer.LOGIN_PERSON, object.get(CommonServer.LOGIN_PERSON));			
			
			JSONArray loginArray = (JSONArray) jsonRootObject.get(object.get(CommonServer.TYPE));
			loginArray.add(objectToAdd);
			jsonRootObject.put(object.get(CommonServer.TYPE), loginArray);
			jsonDataFromFile.put(CommonServer.LOGIN_ROOT, jsonRootObject);
			fileManager.writeToJsonFile(CommonServer.LOGIN_FILE, jsonDataFromFile);
			responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_OK);
		}
		
		try {
			socketData.getOutputStream().writeUTF(responseObject.toJSONString());
			socketData.getOutputStream().flush();
		} catch (IOException e) {

			System.out.println("IOException from AddEmployee method. Message: " + e.getMessage());
		}

	}// addLogin
	
	// Deleting login
	private void deleteLogin(JSONObject object) {
		
		JSONObject jsonDataFromFile = (JSONObject) fileManager.readFromJsonFile(CommonServer.LOGIN_FILE);
		
		JSONObject responseObject = new JSONObject();
		responseObject.put(CommonServer.JSON_ROOT_TITLE, CommonServer.LOGIN_ROOT);
		responseObject.put(CommonServer.MSG_ROOT, CommonServer.RESPONSE_BAD);
		
		if (jsonDataFromFile != null) {
			
			JSONObject jsonRootObject = (JSONObject) jsonDataFromFile.get(CommonServer.LOGIN_ROOT);
			JSONArray adminArray = (JSONArray) jsonRootObject.get(CommonServer.LOGIN_ADMIN_ARRAY);
			JSONArray usersArray = (JSONArray) jsonRootObject.get(CommonServer.LOGIN_USER_ARRAY);
			
			Iterator<JSONObject> iteratorAdmin = adminArray.iterator();
			
			while(iteratorAdmin.hasNext()) {
				
				JSONObject temp = iteratorAdmin.next();
				
				if(object.get(CommonServer.WORK_ID).equals(temp.get(CommonServer.WORK_ID)) && 
						object.get(CommonServer.LOGIN_PERSON).equals(temp.get(CommonServer.LOGIN_PERSON))) {
					
					adminArray.remove(adminArray.indexOf(temp));
					break;
				}
			}
			
			Iterator<JSONObject> iteratorUser = usersArray.iterator();
			
			while(iteratorUser.hasNext()) {
				
				JSONObject temp = iteratorUser.next();
				
				if(object.get(CommonServer.WORK_ID).equals(temp.get(CommonServer.WORK_ID)) && 
						object.get(CommonServer.LOGIN_PERSON).equals(temp.get(CommonServer.LOGIN_PERSON))) {
					
					usersArray.remove(usersArray.indexOf(temp));
					break;
				}
			}			
			
			jsonRootObject.put(CommonServer.LOGIN_ADMIN_ARRAY, adminArray);
			jsonRootObject.put(CommonServer.LOGIN_USER_ARRAY, usersArray);
			jsonDataFromFile.put(CommonServer.LOGIN_ROOT, jsonRootObject);
			fileManager.writeToJsonFile(CommonServer.LOGIN_FILE, jsonDataFromFile);
		}
		
	}// deleteLogin

	// Make login checking operation
	private void loginChecking(JSONObject object) {

		JSONObject jsonDataFromFile = (JSONObject) fileManager.readFromJsonFile(CommonServer.LOGIN_FILE);
		JSONObject responseObject = new JSONObject();
		String responseMsg = CommonServer.RESPONSE_BAD;

		if (jsonDataFromFile != null) {

			String username = (String) object.get(CommonServer.WORK_ID);
			String password = (String) object.get(CommonServer.PASSWORD);
			JSONObject jsonRootObject = (JSONObject) jsonDataFromFile.get(CommonServer.LOGIN_ROOT);
			JSONArray adminArray = (JSONArray) jsonRootObject.get(CommonServer.LOGIN_ADMIN_ARRAY);
			JSONArray usersArray = (JSONArray) jsonRootObject.get(CommonServer.LOGIN_USER_ARRAY);

			boolean isAdmin = findUsernameAndPasswordInLoginsArray(adminArray, responseObject, username, password);

			if (isAdmin) {

				responseMsg = CommonServer.RESPONSE_ADMIN;
			} else {
				boolean isUser = findUsernameAndPasswordInLoginsArray(usersArray, responseObject, username, password);

				if (isUser) {

					responseMsg = CommonServer.RESPONSE_USER;
				}
			}

			responseObject.put(CommonServer.MSG_ROOT, responseMsg);
		}

		try {
			socketData.getOutputStream().writeUTF(responseObject.toJSONString());
			socketData.getOutputStream().flush();
		} catch (IOException e) {

			System.out.println("IOException from LoginChecking method. Message: " + e.getMessage());
		}

	}// loginChecking

	// Find user in array
	private boolean findUsernameAndPasswordInLoginsArray(JSONArray array, JSONObject object, String username, String password) {

		boolean isFound = false;

		if (array != null) {

			Iterator<JSONObject> iterator = array.iterator();

			while (iterator.hasNext()) {

				JSONObject entry = iterator.next();
				String user = (String) entry.get(CommonServer.WORK_ID);
				String pass = (String) entry.get(CommonServer.PASSWORD);

				if (username.equals(user) && password.equals(pass)) {

					object.put(CommonServer.JSON_BRANCH_TITLE, entry.get(CommonServer.JSON_BRANCH_TITLE));
					object.put(CommonServer.LOGIN_PERSON, entry.get(CommonServer.LOGIN_PERSON));
					isFound = true;
					break;
				}
			}
		}

		return isFound;

	}// findInArray

}// class
