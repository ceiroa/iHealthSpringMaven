package com.ceiroa.ihealth.model.daos;

import com.ceiroa.ihealth.model.forms.iTopVisit;
import com.ceiroa.ihealth.model.forms.iVisit;
import com.ceiroa.ihealth.model.forms.daos.SmallVisitDAO;
import com.ceiroa.ihealth.model.forms.daos.VisitHelperDAO;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitsDAO {

	@Autowired
    private VisitHelperDAO visitHelperDAO;
    @Autowired
    private SmallVisitDAO smallVisitDAO;
	
    private final static String csTableName = "cervicalSpineVisits";
    private final static String leTableName = "lowerExtremitiesVisits";
    private final static String lsTableName = "lumbarSpineVisits";
    private final static String ueTableName = "upperExtremitiesVisits";

    public boolean thereAreVisitsForClient(String clientId) {
        boolean csVisits = visitHelperDAO.thereAreVisitsForClient(clientId, csTableName);
        boolean leVisits = visitHelperDAO.thereAreVisitsForClient(clientId, leTableName);
        boolean lsVisits = visitHelperDAO.thereAreVisitsForClient(clientId, lsTableName);
        boolean ueVisits = visitHelperDAO.thereAreVisitsForClient(clientId, ueTableName);
        
        if(csVisits || leVisits || lsVisits || ueVisits)
        	return true;
        else
        	return false;
    }

    public ArrayList<iVisit> getVisitsForClient(String clientId) {
        ArrayList<iVisit> visits = new ArrayList<iVisit>();

        visits.addAll(visitHelperDAO.getVisitsForClient(clientId, csTableName));
        visits.addAll(visitHelperDAO.getVisitsForClient(clientId, leTableName));
        visits.addAll(visitHelperDAO.getVisitsForClient(clientId, lsTableName));
        visits.addAll(visitHelperDAO.getVisitsForClient(clientId, ueTableName));

        return visits;
    }

    public ArrayList<iTopVisit> getAllVisitsMultipleConstraints(Map<String, String> constraints) {
        ArrayList<iTopVisit> visits = new ArrayList<iTopVisit>();

        visits.addAll(smallVisitDAO.getAllSmallVisitsWithConstraints(csTableName, constraints));
        visits.addAll(smallVisitDAO.getAllSmallVisitsWithConstraints(leTableName, constraints));
        visits.addAll(smallVisitDAO.getAllSmallVisitsWithConstraints(lsTableName, constraints));
        visits.addAll(smallVisitDAO.getAllSmallVisitsWithConstraints(ueTableName, constraints));

        return visits;
    }
}
