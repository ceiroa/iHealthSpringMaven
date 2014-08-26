package com.ceiroa.ihealth.model.forms.daos;

import com.ceiroa.ihealth.model.IntHelper;
import com.ceiroa.ihealth.model.forms.LowerExtremitiesVisit;
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
public class LowerExtremitiesDAO implements iVisitDAO {

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
	                updateLowerExtremities(visit, visitId);
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
			    	updateLowerExtremities(visit, visitId);
	            }
	        });
	        return 1;
        } catch(Exception e) {
        	return 0;
        }
    }
    
    public int updateLowerExtremities(iVisit visit, int visitId) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	
    	StringBuilder query = new StringBuilder();
        query.append("UPDATE lowerExtremitiesVisits ")
          .append("SET kneeFlex = ?, kneeExt = ?, pf = ?, df = ?, ")
          .append("inv = ?, ev = ?, hipFlex = ?, hipExt = ?, ")
          .append("hipAbd = ?, hipAdd = ?, hipLr = ?, hipMr = ?, ")
          .append("hipHyp = ?, trendelenbarg = ?, legLength = ?, ")
          .append("thomasTest = ?, oberTest = ?, mcMurray = ?, ")
          .append("apleyTest = ?, bounceHome = ?, patellaGrinding = ?, ")
          .append("apprehensionPatella = ?, tinelSign = ?, effusionTest = ?, ")
          .append("rigidFlatFeet = ?, tibialTorsion = ?, homansSign  = ?, ")
          .append("forefootTest = ?, ankleDorsiflexion  = ?")
          .append(" WHERE id = ?");

        Object[] args = getArgsAsArray(visit, visitId);
        return jdbcTemplate.update(query.toString(), args);
    }

    private Object[] getArgsAsArray(iVisit ivisit, int visitId) {
        ArrayList<String> args = new ArrayList<String>();
        LowerExtremitiesVisit visit = (LowerExtremitiesVisit)ivisit;
        args.add(String.valueOf(visit.getKneeFlex()));
        args.add(String.valueOf(visit.getKneeExt()));
        args.add(String.valueOf(visit.getPf()));
        args.add(String.valueOf(visit.getDf()));
        args.add(String.valueOf(visit.getInv()));
        args.add(String.valueOf(visit.getEv()));
        args.add(String.valueOf(visit.getHipFlex()));
        args.add(String.valueOf(visit.getHipExt()));
        args.add(String.valueOf(visit.getHipAbd()));
        args.add(String.valueOf(visit.getHipAdd()));
        args.add(String.valueOf(visit.getHipLr()));
        args.add(String.valueOf(visit.getHipMr()));
        args.add(String.valueOf(visit.getHipHyp()));
        args.add(String.valueOf(visit.getTrendelenbarg()));
        args.add(String.valueOf(visit.getLegLength()));
        args.add(String.valueOf(visit.getThomasTest()));
        args.add(String.valueOf(visit.getOberTest()));
        args.add(String.valueOf(visit.getMcMurray()));
        args.add(String.valueOf(visit.getApleyTest()));
        args.add(String.valueOf(visit.getBounceHome()));
        args.add(String.valueOf(visit.getPatellaGrinding()));
        args.add(String.valueOf(visit.getApprehensionPatella()));
        args.add(String.valueOf(visit.getTinelSign()));
        args.add(String.valueOf(visit.getEffusionTest()));
        args.add(String.valueOf(visit.getRigidFlatFeet()));
        args.add(String.valueOf(visit.getTibialTorsion()));
        args.add(String.valueOf(visit.getHomansSign()));
        args.add(String.valueOf(visit.getForefootTest()));
        args.add(String.valueOf(visit.getAnkleDorsiflexion()));
        args.add(String.valueOf(visitId));
        return args.toArray();
    }

    private void setLowerExtremitiesVisitFields(Map<String, Object> rs, iVisit visit) {
    	visit.setController("lowerExtremities");
        ((LowerExtremitiesVisit)visit).setKneeFlex(IntHelper.parseInt(rs.get("kneeFlex").toString()));
        ((LowerExtremitiesVisit)visit).setKneeExt(IntHelper.parseInt(rs.get("kneeExt").toString()));
        ((LowerExtremitiesVisit)visit).setPf(IntHelper.parseInt(rs.get("pf").toString()));
        ((LowerExtremitiesVisit)visit).setDf(IntHelper.parseInt(rs.get("df").toString()));
        ((LowerExtremitiesVisit)visit).setInv(IntHelper.parseInt(rs.get("inv").toString()));
        ((LowerExtremitiesVisit)visit).setEv(IntHelper.parseInt(rs.get("ev").toString()));
        ((LowerExtremitiesVisit)visit).setHipFlex(IntHelper.parseInt(rs.get("hipFlex").toString()));
        ((LowerExtremitiesVisit)visit).setHipExt(IntHelper.parseInt(rs.get("hipExt").toString()));
        ((LowerExtremitiesVisit)visit).setHipAbd(IntHelper.parseInt(rs.get("hipAbd").toString()));
        ((LowerExtremitiesVisit)visit).setHipAdd(IntHelper.parseInt(rs.get("hipAdd").toString()));
        ((LowerExtremitiesVisit)visit).setHipLr(IntHelper.parseInt(rs.get("hipLr").toString()));
        ((LowerExtremitiesVisit)visit).setHipMr(IntHelper.parseInt(rs.get("hipMr").toString()));
        ((LowerExtremitiesVisit)visit).setHipHyp(IntHelper.parseInt(rs.get("hipHyp").toString()));
        ((LowerExtremitiesVisit)visit).setTrendelenbarg(rs.get("trendelenbarg").toString());
        ((LowerExtremitiesVisit)visit).setLegLength(rs.get("legLength").toString());
        ((LowerExtremitiesVisit)visit).setThomasTest(rs.get("thomasTest").toString());
        ((LowerExtremitiesVisit)visit).setOberTest(rs.get("oberTest").toString());
        ((LowerExtremitiesVisit)visit).setMcMurray(rs.get("mcMurray").toString());
        ((LowerExtremitiesVisit)visit).setApleyTest(rs.get("apleyTest").toString());
        ((LowerExtremitiesVisit)visit).setBounceHome(rs.get("bounceHome").toString());
        ((LowerExtremitiesVisit)visit).setPatellaGrinding(rs.get("patellaGrinding").toString());
        ((LowerExtremitiesVisit)visit).setApprehensionPatella(rs.get("apprehensionPatella").toString());
        ((LowerExtremitiesVisit)visit).setTinelSign(rs.get("tinelSign").toString());
        ((LowerExtremitiesVisit)visit).setEffusionTest(rs.get("effusionTest").toString());
        ((LowerExtremitiesVisit)visit).setRigidFlatFeet(rs.get("rigidFlatFeet").toString());
        ((LowerExtremitiesVisit)visit).setTibialTorsion(rs.get("tibialTorsion").toString());
        ((LowerExtremitiesVisit)visit).setHomansSign(rs.get("homansSign").toString());
        ((LowerExtremitiesVisit)visit).setForefootTest(rs.get("forefootTest").toString());
        ((LowerExtremitiesVisit)visit).setAnkleDorsiflexion(rs.get("ankleDorsiflexion").toString());
    }

	public void setVisitProperties(Map<String, Object> result, iVisit visit) {
		visit.setController("lowerExtremities");
		visitHelperDAO.setCommonVisitFields(result, visit);
        setLowerExtremitiesVisitFields(result, visit);	
	}
}
