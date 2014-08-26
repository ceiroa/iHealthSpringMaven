package com.ceiroa.ihealth.model.daos;

import java.io.File;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FileDAO {
	
	@Autowired
	private DataSource dataSource;

    public int insert(String clientId, File file) {

    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	
        StringBuilder query = new StringBuilder();
        		
        query.append("INSERT INTO uploads(clientId, dateCreated, dateUpdated")
        			.append(", filename, filePath) VALUES (?, NULL, NULL, ?, ?)");
        
        return jdbcTemplate.update(query.toString(), clientId, 
        		file.getName(),  file.getPath());
    }
}
