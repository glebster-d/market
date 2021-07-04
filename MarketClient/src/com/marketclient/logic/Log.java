package com.marketclient.logic;

// Log POJO class
public class Log {

	private String date;
	private String time;
	private String action;
	private String person;
	private String description;

	public Log() {

		setDate("");
		setTime("");
		setAction("");
		setPerson("");
		setDescription("");

	}// ctor

	// Getting date
	public String getDate() {

		return date;

	}// getDate

	// Setting date
	public void setDate(String date) {

		if (date != null) {

			this.date = date;
		}

	}// setDate

	// Getting time
	public String getTime() {

		return time;

	}// getTime

	// Setting time
	public void setTime(String time) {

		if (time != null) {
			
			this.time = time;
		}

	}// setTime

	// Getting action
	public String getAction() {

		return action;

	}// getAction

	// Setting action
	public void setAction(String action) {

		if (action != null) {

			this.action = action;
		}

	}// setAction

	// Getting person
	public String getPerson() {

		return person;

	}// getPerson

	// Setting person
	public void setPerson(String person) {

		if (person != null) {
			
			this.person = person;
		}

	}// setPerson

	// Getting description
	public String getDescription() {

		return description;

	}// getDescription

	// Setting description
	public void setDescription(String description) {

		if (description != null) {
			
			this.description = description;
		}

	}// setDescription

}// class
