package com.enel.qr.dbservice.resource;

import com.enel.qr.dbservice.repository.CustomRepository;
import com.enel.qr.utils.Table;
import com.ennel.qr.model.CAT_MCREDENCIAL;

import ch.qos.logback.core.net.SyslogOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
	
	public List<String> listDataTables(Table table) {
		String qarg0 = " SELECT ";
		String qarg2 = " FROM ";
		//PGC.CAT_MCARGO;MCARG_ID-int,MCARG_NOMBRE-string,MCARG_ESTADO-int
		//PGC.CAT_MCARGO;MCARG_ID,MCARG_NOMBRE,MCARG_ESTADO;int,string,int
		String query = qarg0 + table.getTableFields() + qarg2 + table.getEsquema().concat(".") + table.getTableName();
		List<Map<String, Object>> list = jdbcTemplate.queryForList(query);
		List<String> dataInserts = new ArrayList<>();  
	    for (Map<String, Object> row : list) {
	        String value = row.values().toString();
	    	value = value.replace("[", "");
	    	value = value.replace("]", "");
	    	String[] dataValue = value.split(",");
	        value = putFieldFormat(table,dataValue);
	        System.out.println("insert==>"+ value);
	    	dataInserts.add(value);
	    } 
	    return dataInserts;
	}
	
	public String putFieldFormat(Table table, String[] dataValue) {
		String[] dataTypes = table.getDataType().split(",");
		StringBuilder insert = new StringBuilder();
		for (int i = 0; i < dataTypes.length  ; i++) {
			if(dataTypes[i].equalsIgnoreCase("string")) {
				dataValue[i] = "'" + dataValue[i].trim() + "'";
				insert.append(dataValue[i]).append(",");
				System.out.println("valor = "+dataValue[i]);
			}else {
				dataValue[i]  = dataValue[i].trim();
				insert.append(dataValue[i]).append(",");
			}
		}
		String value = "("+insert.toString().substring(0,insert.length()-1) + ")";
		return value;
	}

	
	
    @GetMapping("/test2")
    public String readTablesfromBD_test() {
        System.out.println("Display .");
        String tableModel_file = "tableModel.txt";
        System.out.println("TABLES=>"+tableModel_file);
        List<Table> listTables = getTableAndColumns("tableModel.txt");
        createSQLITEDatabase();
        List<Table> listInsertTables = getTables("dataSchema.txt");
        for (int i = 0; i < listInsertTables.size(); i++) {
        	executeSQL(listInsertTables.get(i).getTableName());
        }
        
        System.out.println("Tablas creadas!");
        String sentenceQ1 = "INSERT INTO ";
        String sentenceQ3 = " VALUES ";
        String sentenceQ5 = ";";
        String sentenceInsert = "";
        StringBuilder queryBD = new StringBuilder(); 
      
        int tablesNumber = listTables.size();
        for (int i = 0; i < tablesNumber; i++) {
        	System.out.println("---------------------------->");
        	for (int j = 0; j < listDataTables(listTables.get(i)).size() ; j++) {
        		sentenceInsert = sentenceQ1 + listTables.get(i).getTableName() + 
        				sentenceQ3+ listDataTables(listTables.get(i)).get(j) + sentenceQ5;
        		executeSQL(sentenceInsert);
        		//queryBD = queryBD.append(sentenceInsert).append("\n");
        		System.out.println("==>" + sentenceInsert);
            }
        }
        //writeDataQuery(queryBD.toString());
        return "PROCESANDO...";
    }
    
    public static void createSQLITEDatabase() {
    	String url = "jdbc:sqlite:D:/ENNEL/DB/SQLITE/test.db";
    	try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Drive name " + meta.getDriverName());
                System.out.println("DB created");
            }
    	} catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private Connection connect() {
        String url = "jdbc:sqlite:D://ENNEL/DB/SQLITE/test.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public void executeSQL(String sql) {
        try (Connection conn = this.connect();
        	PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void writeDataQuery(String content) {
    	String FILENAME = "D:\\ENNEL\\DB\\enelDB.txt";
    	BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(FILENAME);
			bw = new BufferedWriter(fw);
			bw.write(content);
			System.out.println("Fin");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
    }
    
    private List<Table> getTables(String fileName){
    	ClassLoader classLoader = getClass().getClassLoader();
    	File file = new File(classLoader.getResource(fileName).getFile());
    	List<Table> tables = new ArrayList<>();
    	try (Scanner scanner = new Scanner(file)) {
    		while (scanner.hasNextLine()) {
    			String line = scanner.nextLine();
    			Table table = new Table();
    			table.setTableName(line);
    			tables.add(table);
    		}
    		scanner.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return tables;
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
  
    private List<Table> getTableAndColumns(String fileName) {
    	ClassLoader classLoader = getClass().getClassLoader();
    	File file = new File(classLoader.getResource(fileName).getFile());
    	List<Table> tables = new ArrayList<>();
    	try (Scanner scanner = new Scanner(file)) {
    		while (scanner.hasNextLine()) {
    			String line = scanner.nextLine();
    			String[] arguments = line.split(";");
    			Table table = new Table();
    			table.setEsquema(arguments[0]);
    			table.setTableName(arguments[1]);
    			table.setTableFields(arguments[2]);
    			table.setDataType(arguments[3]);
    			tables.add(table);
    		}
    		scanner.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return tables;
   }
    


}
