//package com.enel.qr.utils;
//import java.io.File;
//import java.util.List;
//import java.util.Scanner;
//
//public class ReadFile {
//	
//	  private List<Table> getTableNames(String fileName) {
//	    	ClassLoader classLoader = getClass().getClassLoader();
//	    	File file = new File(classLoader.getResource(fileName).getFile());
//	    	List<Table> tables = new ArrayList<>();
//	    	try (Scanner scanner = new Scanner(file)) {
//	    		while (scanner.hasNextLine()) {
//	    			String line = scanner.nextLine();
//	    			String[] arguments = line.split(";");
//	    			Table table = new Table();
//	    			table.setTableName(arguments[0]);
//	    			table.setTableFields(arguments[1]);
//	    			tables.add(table);
//	    		}
//	    		scanner.close();
//	    	} catch (IOException e) {
//	    		e.printStackTrace();
//	    	}
//	    	return tables;
//	   }
//}
