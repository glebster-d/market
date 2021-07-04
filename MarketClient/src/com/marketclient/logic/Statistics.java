package com.marketclient.logic;

// Statistics POJO class
public class Statistics {

	private String date;
	private String time;
	private String category;
	private String item;
	private String amount;

	public Statistics() {

		setDate("");
		setTime("");
		setCategory("");
		setItem("");
		setAmount("0");

	}// ctor

	public String getDate() {

		return date;

	}// getDate

	public void setDate(String date) {

		if (date != null) {

			this.date = date;
		}

	}// setDate

	public String getTime() {

		return time;

	}// getTime

	public void setTime(String time) {

		if (time != null) {

			this.time = time;
		}

	}// setTime

	public String getCategory() {

		return category;

	}// getCategory

	public void setCategory(String category) {

		if (category != null) {
			
			this.category = category;
		}

	}// setCategory

	public String getItem() {

		return item;
	
	}// getItem

	public void setItem(String item) {

		if (item != null) {
			
			this.item = item;
		}

	}// setItem

	public String getAmount() {

		return amount;

	}// getAmount

	public void setAmount(String amount) {

		if (amount != null) {
			
			this.amount = amount;
		}

	}// setAmount

}// class
