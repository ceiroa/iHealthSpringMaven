package com.ceiroa.ihealth.model.daos;

import com.ceiroa.ihealth.model.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDAO {
    
	@Autowired
	private DataSource dataSource;

	ParameterizedRowMapper<Client> mapper = new ParameterizedRowMapper<Client>() {
	    
        public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Client client = new Client();
        	setClientFields(rs, client);
            return client;
        }
    };
    
    public int insert(Client client) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO clients (");
        List<String> clientFieldNames = Client.getFieldNamesExceptId();
        for(int i=0; i<clientFieldNames.size()-1; i++) {
        	query.append(clientFieldNames.get(i)).append(", ");
        }
        query.append(clientFieldNames.get(clientFieldNames.size()-1)).append(")");
        query.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,")
        .append(" ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,")
        .append(" ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        return jdbcTemplate.update(query.toString(), Client.getFieldValuesExceptId(client).toArray());
    }
    
    

    public int update(Client client) throws IllegalArgumentException, IllegalAccessException {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		StringBuilder query = new StringBuilder();
		query.append("UPDATE clients SET ");
		List<String> clientFieldNames = Client.getFieldNamesExceptId();
        for(int i=0; i<clientFieldNames.size()-1; i++) {
        	query.append(clientFieldNames.get(i)).append("=?, ");
        }
        query.append(clientFieldNames.get(clientFieldNames.size()-1)).append("=? ");
        query.append(" WHERE id = ?");
        List<String> clientFields = Client.getFieldValuesExceptId(client);
        clientFields.add(Long.toString(client.getId()));
        return jdbcTemplate.update(query.toString(), clientFields.toArray());
    }
    
    public int deleteById(String id) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String query = "DELETE FROM clients WHERE id = ?";
        Object[] args = {id};
    	return jdbcTemplate.update(query, args);
    }
    
    public int deleteByLastName(String lastName) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	String query = "DELETE FROM clients WHERE lastName = ?";
    	Object[] args = {lastName};
    	return jdbcTemplate.update(query, args);
    }

    public boolean ssnExists(String ssn) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String query = "SELECT ssn FROM clients WHERE ssn = ?";
        String result = jdbcTemplate.queryForObject(query, String.class, ssn);
        if(result==null || result.isEmpty())
        	return false;
        else
        	return true;
    }

    public Client findClientByID(String id) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String query = "SELECT * FROM clients WHERE id = ?";
        return jdbcTemplate.queryForObject(query, mapper, id);
    }

    public List<Client> findClientWithConstraints(Map<String, String> constraints) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	
    	String query = buildQuery(constraints);

        ArrayList<String> args = new ArrayList<String>();
        
        if(constraints.containsKey("firstName")) 
        	args.add(constraints.get("firstName")+"%");

        if(constraints.containsKey("lastName"))
        	args.add(constraints.get("lastName")+"%");
    	
		return jdbcTemplate.query(query, mapper, args.toArray());		
    }
    
    private String buildQuery(Map<String, String> constraints) {
        StringBuilder query = new StringBuilder("SELECT * ")
    		.append(" FROM clients WHERE clients.id<>0");

        if(constraints.containsKey("firstName"))
            query.append(" AND firstName LIKE ?");

        if(constraints.containsKey("lastName"))
            query.append(" AND lastName LIKE ?");

        return query.toString();
    }
    
    public List<Client> getClients(HttpServletRequest request) {
		
		String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        Map<String, String> constraints = new HashMap<String, String>();

        if(firstName!=null && !firstName.equals(""))
            constraints.put("firstName", firstName);
        if(lastName!=null && !lastName.equals(""))
            constraints.put("lastName", lastName);
        
        List<Client> clients = findClientWithConstraints(constraints);
             
        request.setAttribute("clients", clients);
        
        return clients;
	}
    
   /* public void appendFields(StringBuilder builder) {
    	builder.append("(firstname, ")
        .append("middleInitial, ")
        .append("lastname, ")
        .append("gender, ")
        .append("address, ")
        .append("city, ")
        .append("usState, ")
        .append("zipcode, ")
        .append("email, ")
        .append("referrer, ")
        .append("homePhone, ")
        .append("cellPhone, ")
        .append("workPhone, ")
        .append("dob, ")
        .append("ssn, ")
        .append("driverLicense,")
        .append("employer, ")
        .append("occupation, ")
        .append("employerAddress, ")
        .append("employerPhoneNum, ")
        .append("contactName, ")
        .append("contactRelation, ")
        .append("contactPhone, ")
        .append("insurance, ")
        .append("insuranceAddress, ")
        .append("policyHolderName, ")
        .append("policyHolderAddress, ")
        .append("policyHolderDob, ")
        .append("policyHolderSsn, ")
        .append("policyNumber, ")
        .append("groupNumber, ")
        .append("policyHolderRelation, ")
        .append("insurance2, ")
        .append("insuranceAddress2, ")
        .append("policyHolderName2, ")
        .append("policyHolderAddress2, ")
        .append("policyHolderDob2, ")
        .append("policyHolderSsn2, ")
        .append("policyNumber2, ")
        .append("groupNumber2, ")
        .append("policyHolderRelation2, ")
        .append("accidentInfo, ")
        .append("compInfo)");
    }*/
    
/*    private Object[] setPsStrings(Client client) throws SQLException {
    	List<Object> clientParams = new ArrayList<Object>();
    	clientParams.add(client.getFirstName());
    	clientParams.add(client.getMiddleInitial());
        clientParams.add(client.getLastName());
        clientParams.add(client.getGender());
        clientParams.add(client.getAddress());
        clientParams.add(client.getCity());
        clientParams.add(client.getState());
        clientParams.add(client.getZipcode());
        clientParams.add(client.getEmail());
        ps.setString(10, client.getReferrer());
        ps.setString(11, client.getHomePhone());
        ps.setString(12, client.getCellPhone());
        ps.setString(13, client.getWorkPhone());
        ps.setString(14, client.getDob());
        ps.setString(15, client.getSsn());
        ps.setString(16, client.getDriverLicense());
        ps.setString(17, client.getEmployer());
        ps.setString(18, client.getOccupation());
        ps.setString(19, client.getEmployerAddress());
        ps.setString(20, client.getEmployerPhoneNum());
        ps.setString(21, client.getContactName());
        ps.setString(22, client.getContactRelation());
        ps.setString(23, client.getContactPhone());
        ps.setString(24, client.getInsurance());
        ps.setString(25, client.getInsuranceAddress());
        ps.setString(26, client.getPolicyHolderName());
        ps.setString(27, client.getPolicyHolderAddress());
        ps.setString(28, client.getPolicyHolderDob());
        ps.setString(29, client.getPolicyHolderSsn());
        ps.setString(30, client.getPolicyNumber());
        ps.setString(31, client.getGroupNumber());
        ps.setString(32, client.getPolicyHolderRelation());
        ps.setString(33, client.getInsurance2());
        ps.setString(34, client.getInsuranceAddress2());
        ps.setString(35, client.getPolicyHolderName2());
        ps.setString(36, client.getPolicyHolderAddress2());
        ps.setString(37, client.getPolicyHolderDob2());
        ps.setString(38, client.getPolicyHolderSsn2());
        ps.setString(39, client.getPolicyNumber2());
        ps.setString(40, client.getGroupNumber2());
        ps.setString(41, client.getPolicyHolderRelation2());
        ps.setString(42, client.getAccidentInfo());
        ps.setString(43, client.getCompInfo());
    }*/
    
    private void setClientFields(ResultSet rs, Client client) throws SQLException {
        //TODO: Instead of using the ternary operator we could set the default value of the fields to ""
    	client.setId(rs.getLong("id"));
        client.setFirstName(rs.getString("firstName")==null? "": rs.getString("firstName"));
        client.setMiddleInitial(rs.getString("middleInitial")==null? "": rs.getString("middleInitial"));
        client.setLastName(rs.getString("lastName")==null? "": rs.getString("lastName"));
        client.setGender(rs.getString("gender")==null? "": rs.getString("gender"));
        client.setAddress(rs.getString("address")==null? "": rs.getString("address"));
        client.setCity(rs.getString("city")==null? "": rs.getString("city"));
        client.setState(rs.getString("usState")==null? "": rs.getString("usState"));
        client.setZipcode(rs.getString("zipcode")==null? "": rs.getString("zipcode"));
        client.setEmail(rs.getString("email")==null? "": rs.getString("email"));
        client.setReferrer(rs.getString("referrer")==null? "": rs.getString("referrer"));
        client.setHomePhone(rs.getString("homePhone")==null? "": rs.getString("homePhone"));
        client.setCellPhone(rs.getString("cellPhone")==null? "": rs.getString("cellPhone"));
        client.setWorkPhone(rs.getString("workPhone")==null? "": rs.getString("workPhone"));
        client.setDob(rs.getString("dob")==null? "": rs.getString("dob"));
        client.setSsn(rs.getString("ssn")==null? "": rs.getString("ssn"));
        client.setDriverLicense(rs.getString("driverLicense")==null? "": rs.getString("driverLicense"));
        client.setEmployer(rs.getString("employer")==null? "": rs.getString("employer"));
        client.setOccupation(rs.getString("occupation")==null? "": rs.getString("occupation"));
        client.setEmployerAddress(rs.getString("employerAddress")==null? "": rs.getString("employerAddress"));
        client.setEmployerPhoneNum(rs.getString("employerPhoneNum")==null? "": rs.getString("employerPhoneNum"));
        client.setContactName(rs.getString("contactName")==null? "": rs.getString("contactName"));
        client.setContactRelation(rs.getString("contactRelation")==null? "": rs.getString("contactRelation"));
        client.setContactPhone(rs.getString("contactPhone")==null? "": rs.getString("contactPhone"));
        client.setInsurance(rs.getString("insurance")==null? "": rs.getString("insurance"));
        client.setInsuranceAddress(rs.getString("insuranceAddress")==null? "": rs.getString("insuranceAddress"));
        client.setPolicyHolderName(rs.getString("policyHolderName")==null? "": rs.getString("policyHolderName"));
        client.setPolicyHolderAddress(rs.getString("policyHolderAddress")==null? "": rs.getString("policyHolderAddress"));
        client.setPolicyHolderDob(rs.getString("policyHolderDob")==null? "": rs.getString("policyHolderDob"));
        client.setPolicyHolderSsn(rs.getString("policyHolderSsn")==null? "": rs.getString("policyHolderSsn"));
        client.setPolicyNumber(rs.getString("policyNumber")==null? "": rs.getString("policyNumber"));
        client.setGroupNumber(rs.getString("groupNumber")==null? "": rs.getString("groupNumber"));
        client.setPolicyHolderRelation(rs.getString("policyHolderRelation")==null? "": rs.getString("policyHolderRelation"));
        client.setInsurance2(rs.getString("insurance2")==null? "": rs.getString("insurance2"));
        client.setInsuranceAddress2(rs.getString("insuranceAddress2")==null? "": rs.getString("insuranceAddress2"));
        client.setPolicyHolderName2(rs.getString("policyHolderName2")==null? "": rs.getString("policyHolderName2"));
        client.setPolicyHolderAddress2(rs.getString("policyHolderAddress2")==null? "": rs.getString("policyHolderAddress2"));
        client.setPolicyHolderDob2(rs.getString("policyHolderDob2")==null? "": rs.getString("policyHolderDob2"));
        client.setPolicyHolderSsn2(rs.getString("policyHolderSsn2")==null? "": rs.getString("policyHolderSsn2"));
        client.setPolicyNumber2(rs.getString("policyNumber2")==null? "": rs.getString("policyNumber2"));
        client.setGroupNumber2(rs.getString("groupNumber2")==null? "": rs.getString("groupNumber2"));
        client.setPolicyHolderRelation2(rs.getString("policyHolderRelation2")==null? "": rs.getString("policyHolderRelation2"));
        client.setAccidentInfo(rs.getString("accidentInfo")==null? "": rs.getString("accidentInfo"));
        client.setCompInfo(rs.getString("compInfo")==null? "": rs.getString("compInfo"));
    }
}
