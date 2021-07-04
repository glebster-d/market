package com.marketclient.logic;

// Stock POJO class
public class Stock {

	private Pants pantsCategory;
	private Overgarment overgarmentCategory;

	public Stock(Pants pants, Overgarment overgarment) {

		setPantsCategory(pants);
		setOvergarmentCategory(overgarment);
	
	}// ctor

	public Pants getPantsCategory() {
		
		return pantsCategory;
	
	}// getPantsCategory

	public void setPantsCategory(Pants pantsCategory) {
		
		if (pantsCategory != null) {
			
			this.pantsCategory = pantsCategory;
		}
	
	}// setPantsCategory

	public Overgarment getOvergarmentCategory() {
		
		return overgarmentCategory;
	 
	}// getOvergarmentCategory

	public void setOvergarmentCategory(Overgarment overgarmentCategory) {

		if (overgarmentCategory != null) {
			
			this.overgarmentCategory = overgarmentCategory;
		}
	
	}// setOvergarmentCategory

}// class
