package com.ceiroa.ihealth.model.daos;

import com.ceiroa.ihealth.model.User;
import com.ceiroa.ihealth.model.enums.Status;
import com.ceiroa.ihealth.model.enums.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {
    
	@Autowired
	private DataSource dataSource;
	
	ParameterizedRowMapper<User> mapper = new ParameterizedRowMapper<User>() {
	    
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        	User user = new User();
            user.setId(rs.getLong("id"));
            user.setEmail(rs.getString("email"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setStatus(Status.valueOf(rs.getString("status")));
            user.setUserType(UserType.valueOf(rs.getString("userType")));
            return user;
        }
    };
    
    public int insert(String firstName, String lastName,
            String username, String encPassword, String userType, 
            String email, String status) {      
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO users(firstName, lastName, ")
        		.append("username, userType, email, password, status, ")
        		.append("dateCreated, dateUpdated)")
        		.append(" VALUES (?, ?, ?, ?, ?, ?, ?, NULL, NULL)");
        
       return jdbcTemplate.update(query.toString(), firstName, lastName, 
        		username, userType, email, encPassword, status);
    }

    public int update(User user) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    	StringBuilder query = new StringBuilder();
        query.append("UPDATE users SET username = ?, userType = ?, ")
             .append("password = ? WHERE username = ?");
        
        return jdbcTemplate.update(query.toString(), user.getUsername(), 
        		user.getUserType().name(), user.getPassword(), user.getUsername());  
    }

    public int deactivate(long id) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	String query = "UPDATE users SET status = 'inactive' WHERE id = ?";
    	return jdbcTemplate.update(query, id);
    }

    public int activate(long id) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String query = "UPDATE users SET status = 'active' WHERE id = ?";
        return jdbcTemplate.update(query, id);
    }

    public int updatePassword(long id, String encPassword) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String query = "UPDATE users SET password = ? WHERE id = ?";
        return jdbcTemplate.update(query, encPassword, id);
    }

    public boolean usernameExists(String username) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String query = "SELECT username FROM users WHERE username = ?";
        List<Map<String, Object>> dbUsernames = jdbcTemplate.queryForList(query, username);
        if(dbUsernames==null || dbUsernames.isEmpty())
        	return false;
        else
        	return true;
    }

    public User findUserByUsername(String username) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String query = "SELECT * FROM users WHERE username = ?";
        //there should be a db constraint for unique usernames
        List<User> result = jdbcTemplate.query(query, mapper, username);
        if(result != null && result.size()>0)
        	return	result.get(0);
        else
        	return null;
    }

    public User findUserByID(String id) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String query = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(query, mapper, id);
    }

    public List<User> getUsers() {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String query = "SELECT * FROM users";
        return jdbcTemplate.query(query, mapper);
    }
}
