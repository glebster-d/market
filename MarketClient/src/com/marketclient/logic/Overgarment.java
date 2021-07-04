package com.marketclient.logic;

// Overgarment POJO class
public class Overgarment {

	private String coatsAmount;
	private String sweatersAmount;
	private String tShirtsAmount;
	private String buttonedShirtsAmount;
	
	public Overgarment() {
		
		setButtonedShirtsAmount("");
		setCoatsAmount("");
		setSweatersAmount("");
		setTShirtsAmount("");
		
	}// ctor

	public String getCoatsAmount() {
		
		return coatsAmount;
	
	}// getCoatsAmount

	public void setCoatsAmount(String coatsAmount) {

		if (coatsAmount != null) {
			
			this.coatsAmount = coatsAmount;
		}
	
	}// setCoatsAmount

	public String getSweatersAmount() {
		
		return sweatersAmount;
	
	}// getSweatersAmount

	public void setSweatersAmount(String sweatersAmount) {

		if (sweatersAmount != null) {
			
			this.sweatersAmount = sweatersAmount;
		}
	
	}// setSweatersAmount

	public String getTShirtsAmount() {
		
		return tShirtsAmount;
	
	}// getTShirtsAmount

	public void setTShirtsAmount(String tShirtsAmount) {

		if (tShirtsAmount != null) {
			
			this.tShirtsAmount = tShirtsAmount;
		}
	
	}// setTShirtsAmount

	public String getButtonedShirtsAmount() {
		
		return buttonedShirtsAmount;
	
	}// getButtonedShirtsAmount

	public void setButtonedShirtsAmount(String buttonedShirtsAmount) {

		if (buttonedShirtsAmount != null) {
			
			this.buttonedShirtsAmount = buttonedShirtsAmount;
		}
	
	}// setButtonedShirtsAmount

}// class
