package com.enel.qr.dbservice.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.enel.qr.utils.Table;
import com.ennel.qr.model.CAT_MCREDENCIAL;

@Repository
public class CustomRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void listDataTables(Table table) {
		String qarg0 = " SELECT ";
		String qarg2 = " FROM ";
		String query = qarg0 + table.getTableFields() + qarg2 + table.getTableName();
		List<Map<String, Object>> list = jdbcTemplate.queryForList(query);
	    for (Map<String, Object> row : list) {
	        System.out.println(row.values());
	    }  
	}
	

	
	 public JdbcTemplate getJdbcTemplate() {
	        return jdbcTemplate;
	    }
	public List<CAT_MCREDENCIAL> listDataTablesBK() {
		  List<CAT_MCREDENCIAL> result = jdbcTemplate.query("SELECT mcred_id, mcred_fecha_emision , mcred_estadocred, mcred_tipocred from PGC.CAT_MCREDENCIAL",
	                (rs, rowNum) -> new CAT_MCREDENCIAL(rs.getString("mcred_id"),
	                        rs.getString("mcred_fecha_emision"), rs.getString("mcred_estadocred"),rs.getString("mcred_tipocred"))
	        );
		  
//		  List<CAT_MCREDENCIAL> result = jdbcTemplate.query("SELECT mcred_id, mcred_fecha_emision , mcred_estadocred, mcred_tipocred from PGC.CAT_MCREDENCIAL",
//	                (rs, rowNum) -> new CAT_MCREDENCIAL(rs.getString("mcred_id"),
//	                        rs.getString("mcred_fecha_emision"), rs.getString("mcred_estadocred"),rs.getString("mcred_tipocred"))
//	        );
	    return result;

	}
	
	public static String buildTableFields(List<String> ... listArray) {
		StringBuilder fields = new StringBuilder();
		if (listArray != null) {
			for (List<String> field : listArray) {
				fields.append(field);
			}
		}
		return fields.toString();
	}
	
	public static boolean validarListaNotEmpty(List<?> ... listArray) {
		boolean flagListaNotEmpty = false;
		if (listArray != null) {
			for (List<?> list : listArray) {
				if (list != null && !list.isEmpty()) {
					flagListaNotEmpty = true;
				}
				else {
					flagListaNotEmpty = false;
					break;
				}
			}
		}
		return flagListaNotEmpty;
	}
	
	
}
