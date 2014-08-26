package com.ceiroa.ihealth.model.forms.daos;

import com.ceiroa.ihealth.model.forms.iVisit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class VisitHelperDAO {

	@Autowired
	private DataSource dataSource;
    
    protected void setCommonPsStrings(PreparedStatement ps, iVisit visit) throws SQLException {
        ps.setString(1, String.valueOf(visit.getClientId()));
        ps.setString(2, String.valueOf(visit.getDateCreated()));
        ps.setString(3, visit.getSameComplaint());
        ps.setString(4, visit.getPainChange());
        ps.setString(5, visit.getAchingDullSore());
        ps.setString(6, visit.getBurning());
        ps.setString(7, visit.getNumbnessTingling());
        ps.setString(8, visit.getSharpShooting());
        ps.setString(9, visit.getSharpStabbing());
        ps.setString(10, visit.getStiffnessTightness());
        ps.setString(11, visit.getSwelling());
        ps.setString(12, visit.getThrobbing());
        ps.setString(13, visit.getSnapPopGrind());
        ps.setString(14, String.valueOf(visit.getPainLevel()));
        ps.setString(15, visit.getComplaint());
        ps.setString(16, visit.getFrequency());
        ps.setString(17, visit.getInspection());
        ps.setString(18, visit.getPalpation());
        ps.setString(19, visit.getDxAction());
    }

    protected void setCommonVisitFields(Map<String, Object> rs, iVisit visit) {
        visit.setId(Long.parseLong(rs.get("id").toString()));
        visit.setClientId(Long.parseLong(rs.get("clientId").toString()));
        visit.setFirstName(rs.get("firstName").toString());
        visit.setLastName(rs.get("lastName").toString());
        visit.setDateCreated(rs.get("dateCreated").toString());
        visit.setDateUpdated(rs.get("dateUpdated").toString());
        visit.setSameComplaint(rs.get("sameComplaint").toString());
        visit.setPainChange(rs.get("painChange").toString());
        visit.setAchingDullSore(rs.get("achingDullSore").toString());
        visit.setBurning(rs.get("burning").toString());
        visit.setNumbnessTingling(rs.get("numbnessTingling").toString());
        visit.setSharpShooting(rs.get("sharpShooting").toString());
        visit.setSharpStabbing(rs.get("sharpStabbing").toString());
        visit.setStiffnessTightness(rs.get("stiffnessTightness").toString());
        visit.setSwelling(rs.get("swelling").toString());
        visit.setThrobbing(rs.get("throbbing").toString());
        visit.setSnapPopGrind(rs.get("snapPopGrind").toString());
        visit.setPainLevel((Integer) rs.get("painLevel"));
        visit.setComplaint(rs.get("complaint").toString());
        visit.setFrequency(rs.get("frequency").toString());
        visit.setDxAction(rs.get("dxAction").toString());
        visit.setInspection(rs.get("inspection").toString());
        visit.setPalpation(rs.get("palpation").toString());
    }

    public int insertCommon(final iVisit visit, String table) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	
    	final StringBuilder query = new StringBuilder();
        query.append("INSERT INTO "+ table + "(")
          .append("clientId, dateCreated, dateUpdated, sameComplaint, ")
          .append("painChange, achingDullSore, burning, numbnessTingling, ")
          .append("sharpShooting, sharpStabbing, stiffnessTightness, swelling, ")
          .append("throbbing, snapPopGrind, painLevel, complaint, frequency, ")
          .append("inspection, palpation, dxAction) ")
          .append("VALUES (")
          .append("?, ?, NULL, ?, ")
          .append("?, ?, ?, ?, ")
          .append("?, ?, ?, ?, ")
          .append("?, ?, ?, ?, ?, ")
          .append("?, ?, ?)");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
            new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement pst =
                        connection.prepareStatement(query.toString(), new String[] {"id"});
                    setCommonPsStrings(pst, visit);
                    return pst;
                }
            },
            keyHolder);

        int insertedKey = keyHolder.getKey().intValue();
        return insertedKey;
    }

    public int updateCommon(iVisit visit, int visitId, String table) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	
    	final StringBuilder query = new StringBuilder();
    	query.append("UPDATE "+ table + " SET ")
          .append("clientId=?, ")
          .append("dateCreated=?, ")
          .append("dateUpdated=NULL, ")
          .append("sameComplaint=?, ")
          .append("painChange=?, ")
          .append("achingDullSore=?, ")
          .append("burning=?, ")
          .append("numbnessTingling=?, ")
          .append("sharpShooting=?, ")
          .append("sharpStabbing=?, ")
          .append("stiffnessTightness=?, ")
          .append("swelling=?, ")
          .append("throbbing=?, ")
          .append("snapPopGrind=?, ")
          .append("painLevel=?, ")
          .append("complaint=?, ")
          .append("frequency=?, ")
          .append("inspection=?, ")
          .append("palpation=?, ")
          .append("dxAction=? ")
          .append("WHERE id=?");

        Object[] args = getCommonArgsAsArray(visit, visitId);
        return jdbcTemplate.update(query.toString(), args);
    }

    protected Object[] getCommonArgsAsArray(iVisit visit, int visitId) {
        ArrayList<String> args = new ArrayList<String>();
        args.add(String.valueOf(visit.getClientId()));
        args.add(String.valueOf(visit.getDateCreated()));
        args.add(visit.getSameComplaint());
        args.add(visit.getPainChange());
        args.add(visit.getAchingDullSore());
        args.add(visit.getBurning());
        args.add(visit.getNumbnessTingling());
        args.add(visit.getSharpShooting());
        args.add(visit.getSharpStabbing());
        args.add(visit.getStiffnessTightness());
        args.add(visit.getSwelling());
        args.add(visit.getThrobbing());
        args.add(visit.getSnapPopGrind());
        args.add(String.valueOf(visit.getPainLevel()));
        args.add(visit.getComplaint());
        args.add(visit.getFrequency());
        args.add(visit.getInspection());
        args.add(visit.getPalpation());
        args.add(visit.getDxAction());
        args.add(String.valueOf(visitId));
        return args.toArray();
    }

    public int updateDateCreated(iVisit visit, int visitId, String table) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	
        String query = "UPDATE " + table
          + " SET dateCreated=? WHERE id = " + visitId;
        
        return jdbcTemplate.update(query, new Object[] {
        		String.valueOf(visit.getDateCreated())
        });
    }

    public int deleteVisit(String visitId, String table) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	
        String query = "DELETE FROM " + table
          + " WHERE id = " + visitId;

        return jdbcTemplate.update(query);
    }

    public List<iVisit> getVisitsForClient(String clientId, String table) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	
        String query = "SELECT * FROM " + table
                + " WHERE clientId=" + clientId;

        return jdbcTemplate.queryForList(query, iVisit.class);
    }

    public iVisit getVisitByID(iVisitDAO visitDAO, String visitId, String table, iVisit visitType) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	
        String query = "SELECT * FROM " + table + ", clients "
                + "WHERE " + table + ".id=? AND "
                + "clients.id=" + table + ".clientId";

        ColumnMapRowMapper mapper = new ColumnMapRowMapper();
        Map<String, Object> result = jdbcTemplate.queryForObject(query, mapper, visitId);
        //We are re-setting the "id" because we want the id from the visits table,
        //not from the client table, in the id field
        result.put("id", visitId);
        
		try {
			Class<? extends iVisit> c = visitType.getClass();
	        iVisit visit = c.newInstance();
			visitDAO.setVisitProperties(result, visit);
			return visit;
		} catch (Exception e) {
			// Could not create instance of Type visitType
			e.printStackTrace();
			return null;
		} 
    }

    public iVisit getVisitByClientID(iVisitDAO visitDAO, String clientId, String table, iVisit visitType) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	
        String query = "SELECT * FROM " + table + ", clients "
                + "WHERE " + table + ".clientId=" + clientId + " AND "
                + "clients.id=" + table + ".clientId "
                + "ORDER BY " + table + ".id DESC LIMIT 1";

		try {
			ColumnMapRowMapper mapper = new ColumnMapRowMapper();
	        Map<String, Object> result = jdbcTemplate.queryForObject(query, mapper);
	        
			Class<? extends iVisit> c = visitType.getClass();
	        iVisit visit = c.newInstance();
	        visitDAO.setVisitProperties(result, visit);
			return visit;
		}catch(EmptyResultDataAccessException e){
			//System.out.println("No visit of this type exists.");
			return null;
		} catch (Exception e) {
			// Could not create instance of Type visitType
			e.printStackTrace();
			return null;
		}  
    }
    
    public boolean thereAreVisitsForClient(String clientId,
			String table) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	
        String query = "SELECT COUNT(*) FROM " + table + ", clients "
                + "WHERE " + table + ".clientId=" + clientId + " AND "
                + "clients.id=" + table + ".clientId ";

		int count = jdbcTemplate.queryForInt(query);
		
		if (count>0)
			return true;
		else
			return false;
	}
}
