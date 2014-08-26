package com.ceiroa.ihealth.model.forms.daos;

import com.ceiroa.ihealth.model.IntHelper;
import com.ceiroa.ihealth.model.forms.LumbarSpineVisit;
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
public class LumbarSpineDAO implements iVisitDAO {

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
	                updateLumbarSpine(visit, visitId);
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
			    	updateLumbarSpine(visit, visitId);
	            }
	        });
	        return 1;
        } catch(Exception e) {
        	return 0;
        }
    }
    
    public int updateLumbarSpine(iVisit visit, int visitId) {
    	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    	
    	StringBuilder query = new StringBuilder();
        query.append("UPDATE lumbarSpineVisits ")
          .append("SET flex = ?, ext = ?, llf = ?, rlf = ?, llr = ?, ")
          .append("rlr = ?, valsavas = ?, straightLeg = ?, ")
          .append("browstringTest = ?, lasegueTest = ?, elyTest = ?, ")
          .append("thomasTest = ?, springTest = ?, trenderlenburgTest = ?, ")
          .append("bilateralLegRaise = ?, pelvicRock = ?, ")
          .append("patrickFabere = ?, milgram = ?, medLegFoot = ?, ")
          .append("latLeg = ?, latFoot = ?, patellar = ?, hamstring = ?, ")
          .append("achilles = ?, antTibialis = ?, extHallucis = ?, ")
          .append("peroneus = ? WHERE id = ?");

        Object[] args = getArgsAsArray(visit, visitId);
        int update = jdbcTemplate.update(query.toString(), args);
        return update;
    }

    private Object[] getArgsAsArray(iVisit ivisit, int visitId) {
        ArrayList<String> args = new ArrayList<String>();
        LumbarSpineVisit visit = (LumbarSpineVisit)ivisit;
        args.add(String.valueOf(visit.getFlex()));
        args.add(String.valueOf(visit.getExt()));
        args.add(String.valueOf(visit.getLlf()));
        args.add(String.valueOf(visit.getRlf()));
        args.add(String.valueOf(visit.getLlr()));
        args.add(String.valueOf(visit.getRlr()));
        args.add(visit.getValsavas());
        args.add(visit.getStraightLeg());
        args.add(visit.getBrowstringTest());
        args.add(visit.getLasegueTest());
        args.add(visit.getElyTest());
        args.add(visit.getThomasTest());
        args.add(visit.getSpringTest());
        args.add(visit.getTrenderlenburgTest());
        args.add(visit.getBilateralLegRaise());
        args.add(visit.getPelvicRock());
        args.add(visit.getPatrickFabere());
        args.add(visit.getMilgram());
        args.add(visit.getMedLegFoot());
        args.add(visit.getLatLeg());
        args.add(visit.getLatFoot());
        args.add(visit.getPatellar());
        args.add(visit.getHamstring());
        args.add(visit.getAchilles());
        args.add(visit.getAntTibialis());
        args.add(visit.getExtHallucis());
        args.add(visit.getPeroneus());
        args.add(String.valueOf(visitId));
        return args.toArray();
    }

    private void setLumbarSpineVisitFields(Map<String, Object> rs, iVisit visit) {
    	visit.setController("lumbarSpine");
        ((LumbarSpineVisit)visit).setFlex(IntHelper.parseInt(rs.get("flex").toString()));
        ((LumbarSpineVisit)visit).setExt(IntHelper.parseInt(rs.get("ext").toString()));
        ((LumbarSpineVisit)visit).setLlf(IntHelper.parseInt(rs.get("llf").toString()));
        ((LumbarSpineVisit)visit).setRlf(IntHelper.parseInt(rs.get("rlf").toString()));
        ((LumbarSpineVisit)visit).setLlr(IntHelper.parseInt(rs.get("llr").toString()));
        ((LumbarSpineVisit)visit).setRlr(IntHelper.parseInt(rs.get("rlr").toString()));
        ((LumbarSpineVisit)visit).setValsavas(rs.get("valsavas").toString());
        ((LumbarSpineVisit)visit).setStraightLeg(rs.get("straightLeg").toString());
        ((LumbarSpineVisit)visit).setBrowstringTest(rs.get("browstringTest").toString());
        ((LumbarSpineVisit)visit).setLasegueTest(rs.get("lasegueTest").toString());
        ((LumbarSpineVisit)visit).setElyTest(rs.get("elyTest").toString());
        ((LumbarSpineVisit)visit).setThomasTest(rs.get("thomasTest").toString());
        ((LumbarSpineVisit)visit).setSpringTest(rs.get("springTest").toString());
        ((LumbarSpineVisit)visit).setTrenderlenburgTest(rs.get("trenderlenburgTest").toString());
        ((LumbarSpineVisit)visit).setBilateralLegRaise(rs.get("bilateralLegRaise").toString());
        ((LumbarSpineVisit)visit).setPelvicRock(rs.get("pelvicRock").toString());
        ((LumbarSpineVisit)visit).setPatrickFabere(rs.get("patrickFabere").toString());
        ((LumbarSpineVisit)visit).setMilgram(rs.get("milgram").toString());
        ((LumbarSpineVisit)visit).setMedLegFoot(rs.get("medLegFoot").toString());
        ((LumbarSpineVisit)visit).setLatLeg(rs.get("latLeg").toString());
        ((LumbarSpineVisit)visit).setLatFoot(rs.get("latFoot").toString());
        ((LumbarSpineVisit)visit).setPatellar(rs.get("patellar").toString());
        ((LumbarSpineVisit)visit).setHamstring(rs.get("hamstring").toString());
        ((LumbarSpineVisit)visit).setAchilles(rs.get("achilles").toString());
        ((LumbarSpineVisit)visit).setAntTibialis(rs.get("antTibialis").toString());
        ((LumbarSpineVisit)visit).setExtHallucis(rs.get("extHallucis").toString());
        ((LumbarSpineVisit)visit).setPeroneus(rs.get("peroneus").toString());
    }

	public void setVisitProperties(Map<String, Object> result, iVisit visit) {
		visit.setController("lumbarSpine");
		visitHelperDAO.setCommonVisitFields(result, visit);
        setLumbarSpineVisitFields(result, visit);
	}
}
