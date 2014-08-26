package com.ceiroa.ihealth.model.forms.daos;

import com.ceiroa.ihealth.model.IntHelper;
import com.ceiroa.ihealth.model.forms.CervicalSpineVisit;
import com.ceiroa.ihealth.model.forms.iVisit;
import java.util.ArrayList;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Repository
public class CervicalSpineDAO implements iVisitDAO {
    
	@Autowired
	private DataSource dataSource;
	@Autowired
	private VisitHelperDAO visitHelperDAO;
	
	//Example from http://ayushsuman.blogspot.com/2010/05/spring-transaction-using-jdbctemplate.html
	public int insert(final iVisit visit, final String table) {
		TransactionTemplate tt = new TransactionTemplate();
		tt.setTransactionManager(new DataSourceTransactionManager(dataSource));
		try {
			tt.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(
						TransactionStatus status) {
					int visitId = visitHelperDAO.insertCommon(visit, table);
					visitHelperDAO.updateDateCreated(visit, visitId, table);
					updateCervicalSpine(visit, visitId);
				}
			});
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	public int update(final iVisit visit, final int visitId, final String table) {
    	TransactionTemplate tt = new TransactionTemplate();
		tt.setTransactionManager(new DataSourceTransactionManager(dataSource));
    	try {
	        tt.execute(new TransactionCallbackWithoutResult() {
	            @Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
	            	visitHelperDAO.updateCommon(visit, visitId, table);
			        updateCervicalSpine(visit, visitId);
	            }
	        });
	        return 1;
        } catch(Exception e) {
        	return 0;
        }
    }

    //Example from http://www.vaannila.com/spring/spring-jdbc-tutorial-1.html
    public int updateCervicalSpine(iVisit visit, int visitId) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        
    	StringBuilder query = new StringBuilder();
        query.append("UPDATE cervicalSpineVisits ")
          .append("SET flex = ?, llf = ?, llr = ?, ext = ?,")
          .append("rlf = ?, rlr = ?, jacksonComp = ?, ")
          .append("maxComp = ?, shoulderDep = ?, sotoHall = ?, ")
          .append("spurlings = ?, csDistraction = ?, ")
          .append("valsavas = ?, baccody = ?, latArm = ?, ")
          .append("latForearm = ?, middleFinger = ?, ")
          .append("medForearm = ?, medArm = ?, biceps = ?, ")
          .append("brachiorad = ?, triceps = ?, shoulderAbd = ?, ")
          .append("wristExt = ?, wristFlex = ?, fingerExt = ?, ")
          .append("fingerFlex = ?, fingerAbd = ? ")
          .append("WHERE id = ?");

        Object[] args = getArgsAsArray(visit, visitId);
        return jdbcTemplate.update(query.toString(), args);
    }

    private Object[] getArgsAsArray(iVisit ivisit, int visitId) {
        ArrayList<String> args = new ArrayList<String>();
        CervicalSpineVisit visit = (CervicalSpineVisit) ivisit;
        args.add(String.valueOf(visit.getFlex()));
        args.add(String.valueOf(visit.getLlf()));
        args.add(String.valueOf(visit.getLlr()));
        args.add(String.valueOf(visit.getExt()));
        args.add(String.valueOf(visit.getRlf()));
        args.add(String.valueOf(visit.getRlr()));
        args.add(visit.getJacksonComp());
        args.add(visit.getMaxComp());
        args.add(visit.getShoulderDep());
        args.add(visit.getSotoHall());
        args.add(visit.getSpurlings());
        args.add(visit.getCsDistraction());
        args.add(visit.getValsavas());
        args.add(visit.getBaccody());
        args.add(visit.getLatArm());
        args.add(visit.getLatForearm());
        args.add(visit.getMiddleFinger());
        args.add(visit.getMedForearm());
        args.add(visit.getMedArm());
        args.add(visit.getBiceps());
        args.add(visit.getBrachiorad());
        args.add(visit.getTriceps());
        args.add(visit.getShoulderAbd());
        args.add(visit.getWristExt());
        args.add(visit.getWristFlex());
        args.add(visit.getFingerExt());
        args.add(visit.getFingerFlex());
        args.add(visit.getFingerAbd());
        args.add(String.valueOf(visitId));
        return args.toArray();
    }

    private void setCervicalSpineVisitFields(Map<String, Object> rs, iVisit visit) {
    	visit.setController("cervicalSpine");
        ((CervicalSpineVisit)visit).setFlex(IntHelper.parseInt(rs.get("flex").toString()));
        ((CervicalSpineVisit)visit).setLlf(IntHelper.parseInt(rs.get("llf").toString()));
        ((CervicalSpineVisit)visit).setLlr(IntHelper.parseInt(rs.get("llr").toString()));
        ((CervicalSpineVisit)visit).setExt(IntHelper.parseInt(rs.get("ext").toString()));
        ((CervicalSpineVisit)visit).setRlf(IntHelper.parseInt(rs.get("rlf").toString()));
        ((CervicalSpineVisit)visit).setRlr(IntHelper.parseInt(rs.get("rlr").toString()));
        ((CervicalSpineVisit)visit).setJacksonComp(rs.get("jacksonComp").toString());
        ((CervicalSpineVisit)visit).setMaxComp(rs.get("maxComp").toString());
        ((CervicalSpineVisit)visit).setShoulderDep(rs.get("shoulderDep").toString());
        ((CervicalSpineVisit)visit).setSotoHall(rs.get("sotoHall").toString());
        ((CervicalSpineVisit)visit).setSpurlings(rs.get("spurlings").toString());
        ((CervicalSpineVisit)visit).setCsDistraction(rs.get("csDistraction").toString());
        ((CervicalSpineVisit)visit).setValsavas(rs.get("valsavas").toString());
        ((CervicalSpineVisit)visit).setBaccody(rs.get("baccody").toString());
        ((CervicalSpineVisit)visit).setLatArm(rs.get("latArm").toString());
        ((CervicalSpineVisit)visit).setLatForearm(rs.get("latForearm").toString());
        ((CervicalSpineVisit)visit).setMiddleFinger(rs.get("middleFinger").toString());
        ((CervicalSpineVisit)visit).setMedForearm(rs.get("medForearm").toString());
        ((CervicalSpineVisit)visit).setMedArm(rs.get("medArm").toString());
        ((CervicalSpineVisit)visit).setBiceps(rs.get("Biceps").toString());
        ((CervicalSpineVisit)visit).setBrachiorad(rs.get("brachiorad").toString());
        ((CervicalSpineVisit)visit).setTriceps(rs.get("triceps").toString());
        ((CervicalSpineVisit)visit).setShoulderAbd(rs.get("shoulderAbd").toString());
        ((CervicalSpineVisit)visit).setWristExt(rs.get("wristExt").toString());
        ((CervicalSpineVisit)visit).setWristFlex(rs.get("wristFlex").toString());
        ((CervicalSpineVisit)visit).setFingerExt(rs.get("fingerExt").toString());
        ((CervicalSpineVisit)visit).setFingerFlex(rs.get("fingerFlex").toString());
        ((CervicalSpineVisit)visit).setFingerAbd(rs.get("fingerAbd").toString());
    }

	public void setVisitProperties(Map<String, Object> result, iVisit visit) {
		visit.setController("cervicalSpine");
		visitHelperDAO.setCommonVisitFields(result, visit);
        setCervicalSpineVisitFields(result, visit);	
	}
}
