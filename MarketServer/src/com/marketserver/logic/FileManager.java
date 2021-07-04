package com.marketserver.logic;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// Singleton class for file operations
public class FileManager {

	private final static FileManager instance = new FileManager();
	
	private FileManager() {

	}// ctor

	public static FileManager getInstance() {
		
		return instance;
	
	}// getInstance
	
	// Write to JSON file
	public synchronized void writeToJsonFile(String filePath, JSONObject object) {
		
		try (FileWriter file = new FileWriter(filePath)) {

			file.write(object.toJSONString());
			file.flush();
		} 
		catch (IOException e) {

			System.out.println("IOException from WriteToJsonFile method. Message: " + e.getMessage());
		}
		
	}// writeToJsonFile

	// Read from JSON file
	public synchronized Object readFromJsonFile(String filePath) {

		Object object = null;
		JSONParser parser = new JSONParser();

		try {
			object = parser.parse(new FileReader(filePath));
		} 
		catch (IOException | ParseException e) {

			System.out.println("Exception from ReadFromJsonFile method. Message: " + e.getMessage());	
		} 

		return object;

	}// readFromJsonFile

}// class