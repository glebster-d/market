package com.marketclient.logic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.marketclient.main.CommonClient;

@SuppressWarnings("unchecked") 
public class JSONManager {

	private ClientMainLogic logic;

	public JSONManager(ClientMainLogic logic) {

		this.logic = logic;

	}// ctor

	// Getting stock of specific branch
	public Stock getBranchStock() {

		Stock stock = null;
		JSONObject jsonObject = requestStockFromServer();

		if (jsonObject != null) {

			JSONObject pantsObject = (JSONObject) jsonObject.get(CommonClient.STOCK_PANTS);
			Pants pants = new Pants();

			pants.setCustomPantsAmount(pantsObject.get(CommonClient.STOCK_CUSTOM_PANTS).toString());
			pants.setJeansAmount(pantsObject.get(CommonClient.STOCK_JEANS).toString());
			pants.setSportPantsAmount(pantsObject.get(CommonClient.STOCK_SPORT_PANTS).toString());

			JSONObject overgarmentObject = (JSONObject) jsonObject.get(CommonClient.STOCK_OVERGARMENT);
			Overgarment overgarment = new Overgarment();

			overgarment.setButtonedShirtsAmount(overgarmentObject.get(CommonClient.STOCK_BUTTON_SHIRT).toString());
			overgarment.setCoatsAmount(overgarmentObject.get(CommonClient.STOCK_COAT).toString());
			overgarment.setSweatersAmount(overgarmentObject.get(CommonClient.STOCK_SWEATER).toString());
			overgarment.setTShirtsAmount(overgarmentObject.get(CommonClient.STOCK_T_SHIRT).toString());

			stock = new Stock(pants, overgarment);
		}

		return stock;

	}// getBranchStocks

	// Requesting stock from server
	private JSONObject requestStockFromServer() {

		JSONObject stock = null;

		JSONObject object = new JSONObject();
		object.put(CommonClient.MSG_ROOT, CommonClient.REQUEST_READ);
		object.put(CommonClient.JSON_ROOT_TITLE, CommonClient.STOCK_ROOT);
		object.put(CommonClient.JSON_BRANCH_TITLE, logic.getBranchName());
		logic.getClient().sendToServer(object);

		String response = (String) logic.getClient().receiveFromServer();

		if (response != null) {

			JSONObject responseObj = getJSONObjectFromResponse(response);

			if (responseObj != null) {

				String message = (String) responseObj.get(CommonClient.MSG_ROOT);

				if (message.equals(CommonClient.RESPONSE_OK)) {

					stock = (JSONObject) responseObj.get(logic.getBranchName());
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, CommonClient.CANNOT_LOAD_STOCK, "Error", JOptionPane.WARNING_MESSAGE);
		}

		return stock;

	}// requestStockFromServer

	// Getting buyers list from server
	public ArrayList<Buyer> getBuyersList() {

		HashMap<String, JSONArray> buyersHashMap;
		ArrayList<Buyer> list = new ArrayList<>();

		buyersHashMap = requestBuyersArraysFromServer();

		if (buyersHashMap != null) {

			for (Entry<String, JSONArray> entry : buyersHashMap.entrySet()) {

				String type = entry.getKey();
				JSONArray array = entry.getValue();
				insertBuyers(list, array, type);
			}
		}

		return list;

	}// getBuyersList

	// Inserting buyers to array list from JSON array
	private void insertBuyers(ArrayList<Buyer> list, JSONArray array, String type) {

		if (array != null) {

			Iterator<JSONObject> iterator = array.iterator();
			JSONObject temp = new JSONObject();

			while (iterator.hasNext()) {

				Buyer buyer = null;

				if (type.equals(CommonClient.BUYER_TYPE_VIP)) {

					buyer = new VipBuyer();
				}

				if (type.equals(CommonClient.BUYER_TYPE_NEW)) {

					buyer = new NewBuyer();
				}

				if (type.equals(CommonClient.BUYER_TYPE_RETURNED)) {

					buyer = new ReturnedBuyer();
				}

				temp = iterator.next();
				buyer.setId(temp.get(CommonClient.ID).toString());
				buyer.setFirstName(temp.get(CommonClient.FIRSTNAME).toString());
				buyer.setLastName(temp.get(CommonClient.LASTNAME).toString());
				buyer.setTelephone(temp.get(CommonClient.TELEPHONE).toString());

				list.add(buyer);
			}
		}

	}// insertBuyers

	// Requesting arrays of clients from server
	private HashMap<String, JSONArray> requestBuyersArraysFromServer() {

		HashMap<String, JSONArray> buyersHashMap = null;

		JSONObject object = new JSONObject();
		object.put(CommonClient.MSG_ROOT, CommonClient.REQUEST_READ);
		object.put(CommonClient.JSON_ROOT_TITLE, CommonClient.BUYER_ROOT);
		logic.getClient().sendToServer(object);

		String response = (String) logic.getClient().receiveFromServer();

		if (response != null) {

			JSONObject responseObj = getJSONObjectFromResponse(response);

			if (responseObj != null) {

				String message = (String) responseObj.get(CommonClient.MSG_ROOT);

				if (message.equals(CommonClient.RESPONSE_OK)) {

					buyersHashMap = new HashMap<>();

					JSONArray arrayNew = (JSONArray) responseObj.get(CommonClient.BUYER_TYPE_NEW);
					JSONArray arrayRet = (JSONArray) responseObj.get(CommonClient.BUYER_TYPE_RETURNED);
					JSONArray arrayVip = (JSONArray) responseObj.get(CommonClient.BUYER_TYPE_VIP);

					buyersHashMap.put(CommonClient.BUYER_TYPE_NEW, arrayNew);
					buyersHashMap.put(CommonClient.BUYER_TYPE_RETURNED, arrayRet);
					buyersHashMap.put(CommonClient.BUYER_TYPE_VIP, arrayVip);
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, CommonClient.CANNOT_LOAD_BUYERS, "Error", JOptionPane.WARNING_MESSAGE);
		}

		return buyersHashMap;

	}// requestBuyersArraysFromServer

	// Getting employee list from server
	public ArrayList<Employee> getBranchEmployeesList() {

		ArrayList<Employee> list = new ArrayList<>();

		JSONArray array = requestEmployeeArraysFromServer();

		if (array != null) {

			Iterator<JSONObject> iterator = array.iterator();
			JSONObject temp = new JSONObject();

			while (iterator.hasNext()) {

				Employee employee = new Employee();
				temp = iterator.next();
				employee.setId(temp.get(CommonClient.ID).toString());
				employee.setFirstName(temp.get(CommonClient.FIRSTNAME).toString());
				employee.setLastName(temp.get(CommonClient.LASTNAME).toString());
				employee.setWorkId(temp.get(CommonClient.WORK_ID).toString());
				employee.setTelephone(temp.get(CommonClient.TELEPHONE).toString());
				employee.setAccount(temp.get(CommonClient.ACCOUNT).toString());
				employee.setPosition(temp.get(CommonClient.POSITION).toString());

				list.add(employee);
			}
		}

		return list;

	}// getBranchEmployeesList

	// Requesting arrays of employees from server
	private JSONArray requestEmployeeArraysFromServer() {

		JSONArray array = null;

		JSONObject object = new JSONObject();
		object.put(CommonClient.MSG_ROOT, CommonClient.REQUEST_READ);
		object.put(CommonClient.JSON_ROOT_TITLE, CommonClient.EMPLOYEE_ROOT);
		object.put(CommonClient.JSON_BRANCH_TITLE, logic.getBranchName());
		logic.getClient().sendToServer(object);

		String response = (String) logic.getClient().receiveFromServer();

		if (response != null) {

			JSONObject responseObj = getJSONObjectFromResponse(response);

			if (responseObj != null) {

				String message = (String) responseObj.get(CommonClient.MSG_ROOT);

				if (message.equals(CommonClient.RESPONSE_OK)) {

					array = (JSONArray) responseObj.get(logic.getBranchName());
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, CommonClient.CANNOT_LOAD_EMPLOYEES, "Error",
					JOptionPane.WARNING_MESSAGE);
		}

		return array;

	}// requestEmployeeArraysFromServer

	// Getting statistics list from server
	public ArrayList<Statistics> getBranchStatistics() {

		ArrayList<Statistics> list = new ArrayList<>();

		JSONArray array = requestStatisticsFromServer();

		if (array != null) {

			Iterator<JSONObject> iterator = array.iterator();

			while (iterator.hasNext()) {

				JSONObject temp = iterator.next();
				Statistics stat = new Statistics();

				stat.setDate(temp.get(CommonClient.STAT_DATE).toString());
				stat.setTime(temp.get(CommonClient.STAT_TIME).toString());
				stat.setCategory(temp.get(CommonClient.STAT_CATEGORY).toString());
				stat.setItem(temp.get(CommonClient.STAT_ITEM).toString());
				stat.setAmount(temp.get(CommonClient.STAT_AMOUNT).toString());

				list.add(stat);
			}
		}
		
		return list;

	}// getBranchStatistics

	// Requesting arrays of statistics from server
	private JSONArray requestStatisticsFromServer() {

		JSONArray array = null;

		JSONObject object = new JSONObject();
		object.put(CommonClient.MSG_ROOT, CommonClient.REQUEST_READ);
		object.put(CommonClient.JSON_ROOT_TITLE, CommonClient.STAT_ROOT);
		object.put(CommonClient.JSON_BRANCH_TITLE, logic.getBranchName());
		logic.getClient().sendToServer(object);

		String response = (String) logic.getClient().receiveFromServer();

		if (response != null) {

			JSONObject responseObj = getJSONObjectFromResponse(response);

			if (responseObj != null) {

				String message = (String) responseObj.get(CommonClient.MSG_ROOT);

				if (message.equals(CommonClient.RESPONSE_OK)) {

					array = (JSONArray) responseObj.get(logic.getBranchName());
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, CommonClient.CANNOT_LOAD_STAT, "Error", JOptionPane.WARNING_MESSAGE);
		}

		return array;

	}// requestStatisticsFromServer

	// Getting logs list from server
	public ArrayList<Log> getSystemLogs() {

		ArrayList<Log> list = new ArrayList<>();
		JSONArray array = requestLogArrayFromServer();

		if (array != null) {

			Iterator<JSONObject> iterator = array.iterator();
			JSONObject temp = new JSONObject();

			while (iterator.hasNext()) {

				Log log = new Log();
				temp = iterator.next();
				log.setDate(temp.get(CommonClient.LOGS_DATE).toString());
				log.setTime(temp.get(CommonClient.LOGS_TIME).toString());
				log.setPerson(temp.get(CommonClient.LOGS_PERSON).toString());
				log.setAction(temp.get(CommonClient.LOGS_ACTION).toString());
				log.setDescription(temp.get(CommonClient.LOGS_DESCR).toString());

				list.add(log);
			}
		}

		return list;

	}// getSystemLogs

	// Requesting array of logs from server
	private JSONArray requestLogArrayFromServer() {

		JSONArray array = null;

		JSONObject object = new JSONObject();
		object.put(CommonClient.MSG_ROOT, CommonClient.REQUEST_READ);
		object.put(CommonClient.JSON_ROOT_TITLE, CommonClient.LOGS_ROOT);
		object.put(CommonClient.JSON_BRANCH_TITLE, logic.getBranchName());
		logic.getClient().sendToServer(object);

		String response = (String) logic.getClient().receiveFromServer();

		if (response != null) {

			JSONObject responseObj = getJSONObjectFromResponse(response);

			if (responseObj != null) {

				String message = (String) responseObj.get(CommonClient.MSG_ROOT);

				if (message.equals(CommonClient.RESPONSE_OK)) {

					array = (JSONArray) responseObj.get(logic.getBranchName());
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, CommonClient.CANNOT_LOAD_LOGS, "Error", JOptionPane.WARNING_MESSAGE);
		}

		return array;

	}// requestLogArrayFromServer

	// Parsing response message to JSON object
	public JSONObject getJSONObjectFromResponse(String response) {

		JSONParser parser = new JSONParser();
		JSONObject object = null;

		try {
			object = (JSONObject) parser.parse(response);
		} 
		catch (ParseException e) {

			System.out.println("ParseException from GetJSONObjectFromResponse method. Message: " + e.getMessage());
		}

		return object;

	}// getJSONObjectFromResponse
	
	// Creating new log JSON object
	public JSONObject createNewLogJSONObject(String action, String descr) {

		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		
		JSONObject objectToSend = new JSONObject();
		objectToSend.put(CommonClient.MSG_ROOT, CommonClient.REQUEST_ADD);
		objectToSend.put(CommonClient.JSON_ROOT_TITLE, CommonClient.LOGS_ROOT);
		objectToSend.put(CommonClient.JSON_BRANCH_TITLE, logic.getBranchName());
		objectToSend.put(CommonClient.LOGS_DATE, format.format(date));
		objectToSend.put(CommonClient.LOGS_TIME, DateFormat.getTimeInstance().format(date));
		objectToSend.put(CommonClient.LOGS_PERSON, logic.getUserName());
		objectToSend.put(CommonClient.LOGS_ACTION, action);
		objectToSend.put(CommonClient.LOGS_DESCR, descr);
		
		return objectToSend;
		
	}// createNewLogJSONObject

}// class
