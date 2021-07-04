package com.marketclient.logic;

import java.text.DecimalFormat;

// Base buyer class
public class Buyer {

	private String id;
	private String firstName;
	private String lastName;
	private String telephone;
	private String discount;	

	public Buyer() {

		setId("");
		setFirstName("");
		setLastName("");
		setTelephone("");
		setDiscount("");

	}// ctor

	public String getId() {
		
		return id;
		
	}// getId

	public void setId(String id) {
		
		if (id != null) {
			this.id = id;
		}
		
	}// setId

	public String getFirstName() {
		
		return firstName;
	
	}// getFirstName

	public void setFirstName(String firstName) {

		if (firstName != null) {
			this.firstName = firstName;
		}
		
	}// setFirstName

	public String getLastName() {
		
		return lastName;
	
	}// getLastName

	public void setLastName(String lastName) {

		if (lastName != null) {
			this.lastName = lastName;
		}
	
	}// setLastName

	public String getTelephone() {
		
		return telephone;
	
	}// getTelephone

	public void setTelephone(String telephone) {

		if (telephone != null) {
			this.telephone = telephone;
		}
	
	}// setTelephone

	public void setDiscount(String discount) {

		if (discount != null) {

			this.discount = discount;
		}

	}// setDiscount

	public String getDiscount() {

		return discount;

	}// getDiscount

	// Calculating price according to discount of buyer
	public String calculatePrice(String value) {

		String finalPrice = null;
		
		try {
			DecimalFormat decFormat = new DecimalFormat("0.00");
			decFormat.setMaximumFractionDigits(2);
			
			float discount = Float.parseFloat(this.discount);
			float price = Float.parseFloat(value);			
			finalPrice = decFormat.format(price - (price * discount));			
		} 
		catch (NumberFormatException e) {

			System.out.println("NumberFormatException from CalculatePrice method. Message: " + e.getMessage());
		}

		return finalPrice;

	}// calculatePrice

}// class
