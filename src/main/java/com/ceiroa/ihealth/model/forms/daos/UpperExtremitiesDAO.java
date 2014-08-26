/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ceiroa.ihealth.model.forms.daos;

import com.ceiroa.ihealth.model.IntHelper;
import com.ceiroa.ihealth.model.forms.UpperExtremitiesVisit;
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
public class UpperExtremitiesDAO implements iVisitDAO {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private VisitHelperDAO visitHelperDAO;
	
    public int insert(final iVisit visit, final String table) {
    	TransactionTemplate tt = new TransactionTemplate();
    	tt.setTransactionManager(new DataSourceTransactionManager(dataSource));
        try {
	        tt.execute(new TransactionCallbackWithoutResult() {
	            @Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
	                int visitId = visitHelperDAO.insertCommon(visit, table);
	                visitHelperDAO.updateDateCreated(visit, visitId, table);
	                updateUpperExtremities(visit, visitId);
	            }
	        });
	        return 1;
        } catch(Exception e) {
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
			    	updateUpperExtremities(visit, visitId);
	            }
	        });
	        return 1;
        } catch(Exception e) {
        	return 0;
        }
    }
    
    public int updateUpperExtremities(iVisit visit, int visitId) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	
    	StringBuilder query = new StringBuilder();
        query.append("UPDATE upperExtremitiesVisits ")
          .append("SET shoulderFlex = ?, shoulderLr = ?, ")
          .append("shoulderAbd = ?, shoulderExt = ?, shoulderMr = ?, ")
          .append("shoulderAdd = ?, elbowFlex = ?, elbowPro = ?, ")
          .append("elbowExt = ?, elbowSup = ?, wristFlex = ?, ")
          .append("wristAbd = ?, wristExt = ?, wristAdd = ?, ")
          .append("dropArmTest = ?, drawbarnTest = ?, ")
          .append("supraspinatusTest = ?, apleyScratchTest = ?, ")
          .append("postImpingSign = ?, speedTest = ?, ")
          .append("crossOverImpTest = ?, yergasonTest = ?, ")
          .append("apprehensionTest = ?, drawerTest = ?, ")
          .append("varusStressTest = ?, cozensTest = ?, ")
          .append("valgusStressTest = ?, golferElbow = ?, ")
          .append("tinelSign = ?, pinchGripTest = ?, fromentTest = ?, ")
          .append("phalenTest = ?, fingerTapTest = ?, ")
          .append("finkelsteninTest = ?, bunnelLitter = ? ")
          .append("WHERE id = ?");

        Object[] args = getArgsAsArray(visit, visitId);
        int update = jdbcTemplate.update(query.toString(), args);
        return update;
    }

    private Object[] getArgsAsArray(iVisit ivisit, int visitId) {
        ArrayList<String> args = new ArrayList<String>();
        UpperExtremitiesVisit visit = (UpperExtremitiesVisit)ivisit;
        args.add(String.valueOf(visit.getShoulderFlex()));
        args.add(String.valueOf(visit.getShoulderLr()));
        args.add(String.valueOf(visit.getShoulderAbd()));
        args.add(String.valueOf(visit.getShoulderExt()));
        args.add(String.valueOf(visit.getShoulderMr()));
        args.add(String.valueOf(visit.getShoulderAdd()));
        args.add(String.valueOf(visit.getElbowFlex()));
        args.add(String.valueOf(visit.getElbowPro()));
        args.add(String.valueOf(visit.getElbowExt()));
        args.add(String.valueOf(visit.getElbowSup()));
        args.add(String.valueOf(visit.getWristFlex()));
        args.add(String.valueOf(visit.getWristAbd()));
        args.add(String.valueOf(visit.getWristExt()));
        args.add(String.valueOf(visit.getWristAdd()));
        args.add(visit.getDropArmTest());
        args.add(visit.getDrawbarnTest());
        args.add(visit.getSupraspinatusTest());
        args.add(visit.getApleyScratchTest());
        args.add(visit.getPostImpingSign());
        args.add(visit.getSpeedTest());
        args.add(visit.getCrossOverImpTest());
        args.add(visit.getYergasonTest());
        args.add(visit.getApprehensionTest());
        args.add(visit.getDrawerTest());
        args.add(visit.getVarusStressTest());
        args.add(visit.getCozensTest());
        args.add(visit.getValgusStressTest());
        args.add(visit.getGolferElbow());
        args.add(visit.getTinelSign());
        args.add(visit.getPinchGripTest());
        args.add(visit.getFromentTest());
        args.add(visit.getPhalenTest());
        args.add(visit.getFingerTapTest());
        args.add(visit.getFinkelsteninTest());
        args.add(visit.getBunnelLitter());
        args.add(String.valueOf(visitId));
        return args.toArray();
    }

    private void setUpperExtremitiesVisitFields(Map<String, Object> rs, iVisit visit) {
    	visit.setController("upperExtremities");
        ((UpperExtremitiesVisit)visit).setShoulderFlex(IntHelper.parseInt(rs.get("shoulderFlex").toString()));
        ((UpperExtremitiesVisit)visit).setShoulderLr(IntHelper.parseInt(rs.get("shoulderLr").toString()));
        ((UpperExtremitiesVisit)visit).setShoulderAbd(IntHelper.parseInt(rs.get("shoulderAbd").toString()));
        ((UpperExtremitiesVisit)visit).setShoulderExt(IntHelper.parseInt(rs.get("shoulderExt").toString()));
        ((UpperExtremitiesVisit)visit).setShoulderMr(IntHelper.parseInt(rs.get("shoulderMr").toString()));
        ((UpperExtremitiesVisit)visit).setShoulderAdd(IntHelper.parseInt(rs.get("shoulderAdd").toString()));
        ((UpperExtremitiesVisit)visit).setElbowFlex(IntHelper.parseInt(rs.get("elbowFlex").toString()));
        ((UpperExtremitiesVisit)visit).setElbowPro(IntHelper.parseInt(rs.get("elbowPro").toString()));
        ((UpperExtremitiesVisit)visit).setElbowExt(IntHelper.parseInt(rs.get("elbowExt").toString()));
        ((UpperExtremitiesVisit)visit).setElbowSup(IntHelper.parseInt(rs.get("elbowSup").toString()));
        ((UpperExtremitiesVisit)visit).setWristFlex(IntHelper.parseInt(rs.get("wristFlex").toString()));
        ((UpperExtremitiesVisit)visit).setWristAbd(IntHelper.parseInt(rs.get("wristAbd").toString()));
        ((UpperExtremitiesVisit)visit).setWristExt(IntHelper.parseInt(rs.get("wristExt").toString()));
        ((UpperExtremitiesVisit)visit).setWristAdd(IntHelper.parseInt(rs.get("wristAdd").toString()));
        ((UpperExtremitiesVisit)visit).setDropArmTest(rs.get("dropArmTest").toString());
        ((UpperExtremitiesVisit)visit).setDrawbarnTest(rs.get("drawbarnTest").toString());
        ((UpperExtremitiesVisit)visit).setSupraspinatusTest(rs.get("supraspinatusTest").toString());
        ((UpperExtremitiesVisit)visit).setApleyScratchTest(rs.get("apleyScratchTest").toString());
        ((UpperExtremitiesVisit)visit).setPostImpingSign(rs.get("postImpingSign").toString());
        ((UpperExtremitiesVisit)visit).setSpeedTest(rs.get("speedTest").toString());
        ((UpperExtremitiesVisit)visit).setCrossOverImpTest(rs.get("crossOverImpTest").toString());
        ((UpperExtremitiesVisit)visit).setYergasonTest(rs.get("yergasonTest").toString());
        ((UpperExtremitiesVisit)visit).setApprehensionTest(rs.get("apprehensionTest").toString());
        ((UpperExtremitiesVisit)visit).setDrawerTest(rs.get("drawerTest").toString());
        ((UpperExtremitiesVisit)visit).setVarusStressTest(rs.get("varusStressTest").toString());
        ((UpperExtremitiesVisit)visit).setCozensTest(rs.get("cozensTest").toString());
        ((UpperExtremitiesVisit)visit).setValgusStressTest(rs.get("valgusStressTest").toString());
        ((UpperExtremitiesVisit)visit).setGolferElbow(rs.get("golferElbow").toString());
        ((UpperExtremitiesVisit)visit).setTinelSign(rs.get("tinelSign").toString());
        ((UpperExtremitiesVisit)visit).setPinchGripTest(rs.get("pinchGripTest").toString());
        ((UpperExtremitiesVisit)visit).setFromentTest(rs.get("fromentTest").toString());
        ((UpperExtremitiesVisit)visit).setPhalenTest(rs.get("phalenTest").toString());
        ((UpperExtremitiesVisit)visit).setFingerTapTest(rs.get("fingerTapTest").toString());
        ((UpperExtremitiesVisit)visit).setFinkelsteninTest(rs.get("finkelsteninTest").toString());
        ((UpperExtremitiesVisit)visit).setBunnelLitter(rs.get("bunnelLitter").toString());
    }

	public void setVisitProperties(Map<String, Object> result, iVisit visit) {
		visit.setController("upperExtremities");
		visitHelperDAO.setCommonVisitFields(result, visit);
        setUpperExtremitiesVisitFields(result, visit);	
	}
}
