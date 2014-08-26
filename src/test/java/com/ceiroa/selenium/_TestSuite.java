package com.ceiroa.selenium;

import com.ceiroa.selenium.tests.WrongCredentials;
import com.ceiroa.selenium.tests.admin.AdmManageClients;
import com.ceiroa.selenium.tests.admin.AdmManageUsersNewAdm;
import com.ceiroa.selenium.tests.admin.AdmManageUsersNewNonAdm;
import com.ceiroa.selenium.tests.admin.AdmManageVisits;
import com.ceiroa.selenium.tests.nonadmin.NonAdmAccess;
import com.ceiroa.selenium.tests.nonadmin.NonAdmChangePssw;
import com.ceiroa.selenium.tests.nonadmin.NonAdmNewClient;
import com.ceiroa.selenium.tests.nonadmin.NonAdmUpload;
import com.ceiroa.selenium.tests.nonadmin.NonAdmCervicalSpine;
import com.ceiroa.selenium.tests.nonadmin.NonAdmLumbarSpine;
import com.ceiroa.selenium.tests.nonadmin.NonAdmLowerExtrem;
import com.ceiroa.selenium.tests.nonadmin.NonAdmUpperExtrem;

import junit.framework.Test;
import junit.framework.TestSuite;

public class _TestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite();
		
		suite.addTestSuite(WrongCredentials.class);
		suite.addTestSuite(AdmManageClients.class);
		suite.addTestSuite(NonAdmNewClient.class);
		suite.addTestSuite(NonAdmAccess.class);
		suite.addTestSuite(NonAdmChangePssw.class);
		
		//We need to have two files named deleteme and deleteme2 in 
		//Tomcat\testingResources
		suite.addTestSuite(NonAdmUpload.class);
		
		suite.addTestSuite(NonAdmCervicalSpine.class);
		suite.addTestSuite(NonAdmLumbarSpine.class);
		suite.addTestSuite(NonAdmLowerExtrem.class);
		suite.addTestSuite(NonAdmUpperExtrem.class);
		
		suite.addTestSuite(AdmManageVisits.class); 
		
		//Cleaning up
		DBHelper.deleteAllTestVisits();
		
		suite.addTestSuite(AdmManageUsersNewAdm.class);
		suite.addTestSuite(AdmManageUsersNewNonAdm.class);
		
		return suite;
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}
}