package com.ceiroa.ihealth.model.forms.daos;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

import com.ceiroa.ihealth.model.forms.daos.SmallVisitDAO;

/**
 *
 * @author martca05
 */
public class AbstractVisitDAOTest {

	SmallVisitDAO sdb;
    Map<String, String> constraints;
    Map<String, String> emptyConstraints;

    @Before
    public void init() {
        sdb  = new SmallVisitDAO();
        constraints = new HashMap<String, String>();
        constraints.put("date", "2011");
    }

    @Test
    public void testBuildQuery() {
         assertEquals("SELECT testTable.id as visitId, clientId, " +
         		"dateCreated, dateUpdated, firstName, lastName " +
         		"FROM testTable, clients WHERE " +
         		"clients.id=testTable.clientId AND dateCreated LIKE ?",
                 sdb.buildQuery("testTable", constraints));
    }
}
