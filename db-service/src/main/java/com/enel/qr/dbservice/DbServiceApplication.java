package com.enel.qr.dbservice;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.w3c.dom.css.CSSUnknownRule;
import com.enel.qr.dbservice.repository.CustomRepository;
import com.ennel.qr.model.CAT_MCREDENCIAL;

import static java.lang.System.exit;

@SpringBootApplication
public class DbServiceApplication {

	@Autowired
	private CustomRepository customRepository;
	
	@Autowired
	DataSource dataSource;
	
	public static void main(String[] args) {
		SpringApplication.run(DbServiceApplication.class, args);
	}
	
	public void run(String... args) throws Exception {

	        System.out.println("DATASOURCE = " + dataSource);

	        /// Get dbcp2 datasource settings
	        // BasicDataSource newds = (BasicDataSource) dataSource;
	        // System.out.println("BasicDataSource = " + newds.getInitialSize());

	        System.out.println("Display ..");
	        List<CAT_MCREDENCIAL> list = customRepository.listDataTablesBK();
	        list.forEach(x -> System.out.println(x));

	        System.out.println("Done!");

	        exit(0);
	    }
}
