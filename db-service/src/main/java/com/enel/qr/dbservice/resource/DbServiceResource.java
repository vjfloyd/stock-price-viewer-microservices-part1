package com.enel.qr.dbservice.resource;

import com.enel.qr.dbservice.repository.CustomRepository;
import com.enel.qr.utils.Table;
import com.ennel.qr.model.CAT_MCREDENCIAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/db")
public class DbServiceResource {

	@Autowired
	private CustomRepository customRepository;
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void listDataTables(Table table) {
		String qarg0 = " SELECT ";
		String qarg2 = " FROM ";
		String query = qarg0 + table.getTableFields() + qarg2 + table.getTableName();
		List<Map<String, Object>> list = jdbcTemplate.queryForList(query);
	    for (Map<String, Object> row : list) {
	       // String values = row.values();
	    	String value = row.values().toString();
	    	value = value.replace("[", "(");
	    	value = value.replace("]", ")");
	    	System.out.println("val="+value);
	        
	    }  
	}
	
    @GetMapping("/test2")
    public String readTablesfromBD_test() {
        System.out.println("Display .");
//        List<CAT_MCREDENCIAL> list = customRepository.listDataTablesTest();
//        list.forEach(x -> System.out.println("ID" +x.mcred_id + "FECHA" + x.mcred_id + " ESTADO " + x.mcred_estadocred));
        String tableModel_file = "tableModel.txt";
        System.out.println("TABLES=>"+tableModel_file);
        List<Table> listTables = getTableNames("tableModel.txt");
        String sentenceQ1 = "insert into ";
        String sentenceQ3 = " value(";
        String sentenceQ5 = ");";
        String sentenceInsert = "";
        for (int i = 0; i < listTables.size(); i++) {
        	sentenceInsert = sentenceQ1 + listTables.get(i).getTableName() + sentenceQ3+ listTables.get(i).getTableFields() + sentenceQ5;
        	listDataTables(listTables.get(i));
        	System.out.println("insert -> " + sentenceInsert);
        }
        return "PROCESANDO...";
    }
    
    /*
     * 1. read table names from tablaModel.txt
     * 2. get data from these tables with select sentence
     * 3. build the insert with the data from point 2
     * 3. put this data into insert sentences then save in dataDB.txt 
     * 4. merge dataBD.txt and dataModel.txt files 
     * 5. make this file readable for ionic app
     * 
     * */
    
    
 
    @GetMapping("/datasync")
    public String dataSync() {
        String tb_cat_credencia = "PGC.CAT_MCREDENCIAL";
        String tb_cat_cargo = "PGC.CAT_MCARGO";
        
        
        return "";
    }
    
   
    private List<Table> getTableNames(String fileName) {
    	ClassLoader classLoader = getClass().getClassLoader();
    	File file = new File(classLoader.getResource(fileName).getFile());
    	List<Table> tables = new ArrayList<>();
    	try (Scanner scanner = new Scanner(file)) {
    		while (scanner.hasNextLine()) {
    			String line = scanner.nextLine();
    			String[] arguments = line.split(";");
    			Table table = new Table();
    			table.setTableName(arguments[0]);
    			table.setTableFields(arguments[1]);
    			tables.add(table);
    		}
    		scanner.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return tables;
   }
    


}
