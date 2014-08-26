package com.ceiroa.selenium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SimpleConnection {

	public static int update(String query, Object[] args) {
		String DB_CONN_STRING = "jdbc:mysql://localhost:3306/ihealth";
		String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	    String USER_NAME = "root";
	    String PASSWORD = "xxxx";
	    
	    Connection connection = null;
	    PreparedStatement ps = null;
	    int result = -1;
	    
	    try {
	       Class.forName(DRIVER_CLASS_NAME).newInstance();
	       connection = DriverManager.getConnection(DB_CONN_STRING, USER_NAME, PASSWORD);
	       ps = connection.prepareStatement(query);
	       for(int i=1; i<=args.length; i++) {
	    	   ps.setString(i, (String) args[i-1]);
	       }
	       result = ps.executeUpdate();
	       connection.close();
	    }catch (SQLException e){
			e.printStackTrace();
	    } catch (Exception ex){
	       System.out.println("Check classpath. Cannot load db driver: " + DRIVER_CLASS_NAME);
	    } 
		return result;
	}
	
	public static ResultSet query(String query, Object[] args) throws SQLException {
		String DB_CONN_STRING = "jdbc:mysql://localhost:3306/ihealth";
		String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	    String USER_NAME = "root";
	    String PASSWORD = "xxxx";
	    
	    Connection connection = null;
	    PreparedStatement ps = null;
	    ResultSet resultset = null;
	    
	    try {
			Class.forName(DRIVER_CLASS_NAME).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    connection = DriverManager.getConnection(DB_CONN_STRING, USER_NAME, PASSWORD);
	    ps = connection.prepareStatement(query);
	    for(int i=1; i<=args.length; i++) {
	    	ps.setString(i, (String) args[i-1]);
	    }
	    resultset = ps.executeQuery();
	  
		return resultset;
	}
}
