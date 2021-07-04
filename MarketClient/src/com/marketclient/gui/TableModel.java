package com.marketclient.gui;

import javax.swing.table.DefaultTableModel;

// Custom table model
public class TableModel extends DefaultTableModel {

	private static final long serialVersionUID = -7197574033983934249L;

	public TableModel(String[] columnNames) {
		
		super(columnNames, 0);
	
	}// ctor

	@Override
	public boolean isCellEditable(int row, int column) {
		
		return false;
	
	}// isCellEditable	
	
}// class