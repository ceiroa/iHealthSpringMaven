package com.ceiroa.selenium;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHelper {

	public static void insertTestClientIfNotExists() {
		String query1 = "SELECT id FROM clients WHERE lastname=?"; 
		Object[] args1 = {Const.LAST_NAME};
		
		try {
			ResultSet rs = SimpleConnection.query(query1, args1);
			if(!rs.next()) {
				String query2 = "INSERT INTO clients"
			          + "(firstname, lastname, email, cellPhone, dob, ssn)"
			          + " VALUES (?, ?, ?, ?, ?, ?)";
				Object[] args2 = {Const.FIRST_NAME,Const.LAST_NAME, Const.EMAIL, 
						Const.CELLPHONE, Const.DOB, Const.SSN};
			    SimpleConnection.update(query2, args2);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//FYI, the system will let us delete it only if there are no visits for this client
	public static void deleteTestClient() {
		deleteAllTestVisits();
		deleteTestUpload();
		String query = "DELETE FROM clients WHERE lastName = ?";
	    Object[] args = {Const.LAST_NAME};
	    SimpleConnection.update(query, args);
	}
	
	public static void deleteAllUsersExceptTestAndTest2() {
		String query = "DELETE FROM users WHERE username<>'" 
			+ Const.TEST_ADMIN_USER + "' AND username<>'" 
			+ Const.TEST_NON_ADMIN_USER + "'";
	    Object[] args = {};
	    SimpleConnection.update(query, args);
	}
	
	public static void deleteAllTestVisits() {
		deleteTestCervicalSpineVisits();
		deleteTestLumbarSpineVisits();
		deleteTestUpperExtremVisits();
		deleteTestLowerExtremVisits();
	}
	
	public static void deleteTestCervicalSpineVisits() {
		String query = "DELETE FROM cervicalspinevisits WHERE " +
				"dxAction = ?";
	    Object[] args = {Const.DX_ACTION};
	    SimpleConnection.update(query, args);
	}
	
	public static void deleteTestLumbarSpineVisits() {
		String query = "DELETE FROM lumbarspinevisits WHERE " +
				"dxAction = ?";
	    Object[] args = {Const.DX_ACTION};
	    SimpleConnection.update(query, args);
	}
	
	public static void deleteTestUpperExtremVisits() {
		String query = "DELETE FROM upperextremitiesvisits WHERE " +
				"dxAction = ?";
	    Object[] args = {Const.DX_ACTION};
	    SimpleConnection.update(query, args);
	}
	
	public static void deleteTestLowerExtremVisits() {
		String query = "DELETE FROM lowerextremitiesvisits WHERE " +
				"dxAction = ?";
	    Object[] args = {Const.DX_ACTION};
	    SimpleConnection.update(query, args);
	}

	public static void deleteTestUpload() {
		String query = "DELETE FROM uploads WHERE " +
		"filename like ?";
		Object[] args = {"deleteme%"};
		SimpleConnection.update(query, args);	
	}
}
