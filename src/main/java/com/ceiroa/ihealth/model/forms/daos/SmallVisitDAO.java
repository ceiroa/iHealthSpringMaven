package com.ceiroa.ihealth.model.forms.daos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ceiroa.ihealth.model.forms.SmallVisit;
import com.ceiroa.ihealth.model.forms.iTopVisit;

@Repository
public class SmallVisitDAO {

	@Autowired
	private DataSource dataSource;
	
	protected void setVisitProperties(Map<String, Object> rs, iTopVisit visit, String table) {
		String controller = table.replace("Visits", "");
        visit.setController(controller);
        visit.setId(Long.parseLong(rs.get("visitId").toString()));
        visit.setClientId(Long.parseLong(rs.get("clientId").toString()));
        visit.setFirstName(rs.get("firstName").toString());
        visit.setLastName(rs.get("lastName").toString());
        visit.setDateCreated(rs.get("dateCreated").toString());
        visit.setDateUpdated(rs.get("dateUpdated").toString());	
	}
	
	public ArrayList<iTopVisit> getAllSmallVisitsWithConstraints(String table, Map<String, String> constraints) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
        String query = buildQuery(table, constraints);

        ArrayList<String> args = new ArrayList<String>();

        if(constraints.containsKey("date"))
            args.add(constraints.get("date")+"%");

        if(constraints.containsKey("firstName")) 
        	args.add(constraints.get("firstName")+"%");

        if(constraints.containsKey("lastName"))
        	args.add(constraints.get("lastName")+"%");
    	
		List<Map<String, Object>> result = jdbcTemplate.queryForList(query, args.toArray());
		
		Iterator<Map<String, Object>> iter = result.iterator();
		
		ArrayList<iTopVisit> visits = new ArrayList<iTopVisit>();
		
        while (iter.hasNext()) {
            iTopVisit visit = new SmallVisit();
            setVisitProperties(iter.next(), visit, table);
            visits.add(visit);
        }
        return visits;
    }

    String buildQuery(String table, Map<String, String> constraints) {
        StringBuilder query = new StringBuilder("SELECT ")
        	.append(table).append(".id as visitId")
    		.append(", clientId, dateCreated, dateUpdated, firstName, lastName")
    		.append(" FROM ")
    		.append(table).append(", clients")
    		.append(" WHERE clients.id=")
            .append(table).append(".clientId");

        if(constraints.containsKey("date"))
            query.append(" AND dateCreated LIKE ?");

        if(constraints.containsKey("firstName"))
            query.append(" AND firstName LIKE ?");

        if(constraints.containsKey("lastName"))
            query.append(" AND lastName LIKE ?");

        return query.toString();
    }
}
