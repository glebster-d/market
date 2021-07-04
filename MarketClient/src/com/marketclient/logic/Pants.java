package com.marketclient.logic;

// Pants category POJO class
public class Pants {

	private String sportPantsAmount;
	private String jeansAmount;
	private String customPantsAmount;
	
	public Pants() {
		
		setSportPantsAmount("");
		setCustomPantsAmount("");
		setJeansAmount("");
	
	}// ctor

	public String getSportPantsAmount() {
		
		return sportPantsAmount;
	
	}// getSportPantsAmount

	public void setSportPantsAmount(String sportPantsAmount) {

		if (sportPantsAmount != null) {
			
			this.sportPantsAmount = sportPantsAmount;
		}
	
	}// setSportPantsAmount

	public String getJeansAmount() {
		
		return jeansAmount;
	
	}// getJeansAmount

	public void setJeansAmount(String jeansAmount) {

		if (jeansAmount != null) {
			
			this.jeansAmount = jeansAmount;
		}
	
	}// setJeansAmount

	public String getCustomPantsAmount() {
		
		return customPantsAmount;
	
	}// getCustomPantsAmount

	public void setCustomPantsAmount(String customPantsAmount) {

		if (customPantsAmount != null) {
			
			this.customPantsAmount = customPantsAmount;
		}
	
	}// setCustomPantsAmount

}// class
