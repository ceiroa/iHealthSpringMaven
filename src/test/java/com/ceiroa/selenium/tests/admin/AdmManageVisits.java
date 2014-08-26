package com.ceiroa.selenium.tests.admin;

import org.junit.Test;

import com.ceiroa.selenium.AbstractBaseTest;
import com.ceiroa.selenium.Const;
import com.ceiroa.selenium.DBHelper;

public class AdmManageVisits extends AbstractBaseTest {

	private final String NEW_DATE = "2017-12-31";
	
	@Override
	public void customSetUp() {
		DBHelper.deleteTestClient();
		DBHelper.insertTestClientIfNotExists();
		DBHelper.deleteAllTestVisits();
		
	}
	
	@Override
	public void customTearDown() {
		DBHelper.deleteTestClient();
		DBHelper.deleteTestCervicalSpineVisits();
	}
	
	@Test
	public void testAdmManageVisits() throws Exception {
		loginAsAdmin();
		
		//Create cervicalspine visit
		createNewCervicalSpineVisit();		
		selenium.click("//input[@value='Submit']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Visit successfully created"));
		
		//Find visit
		selenium.click("link=Manage Visits");
		selenium.waitForPageToLoad("30000");
		selenium.type("firstName", Const.FIRST_NAME);
		selenium.type("lastName", Const.LAST_NAME);
		selenium.click("//input[@value='Search']");
		selenium.waitForPageToLoad("30000");
		
		//View and edit visit
		selenium.click("//input[@value='View/Edit']");
		selenium.waitForPageToLoad("30000");
		selenium.type("dateCreated", NEW_DATE);
		selenium.select("painChange", Const.PAIN_CHANGE);
		selenium.select("painLevel", Const.PAIN_LEVEL);
		selenium.click("//input[@value='Submit']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Visit successfully updated"));
		
		//Find Visit we just edited (the change is the new year we entered)
		selenium.type("firstName", Const.FIRST_NAME);
		selenium.type("lastName", Const.LAST_NAME);
		selenium.type("date", NEW_DATE);
		selenium.click("//input[@value='Search']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(Const.FIRST_NAME));
		assertTrue(selenium.isTextPresent(Const.LAST_NAME));
		assertTrue(selenium.isTextPresent(NEW_DATE));
		
		//Log out
		selenium.click("link=Log Out");
		selenium.waitForPageToLoad("30000");
	}
	
	private void createNewCervicalSpineVisit() {
		selenium.click("link=New Visit");
		selenium.waitForPageToLoad("30000");
		selenium.type("lastName", Const.LAST_NAME);
		selenium.click("//input[@value='Search']");
		selenium.waitForPageToLoad("30000");
		
		selenium.click("//input[@value='Cervical Spine']");
		selenium.waitForPageToLoad("30000");
		
		selenium.select("sameComplaint", "label=No");
		selenium.select("painChange", "label=Better");
		
		selenium.type("flex", "1");
		selenium.type("rlf", "5");
		selenium.type("rlr", "6");
		
		selenium.select("jacksonComp", "label=+");
		selenium.select("shoulderDep", "label=+");
		selenium.select("csDistraction", "label=-");
		selenium.select("valsavas", "label=+");
		
		selenium.select("latArm", "label=Hyper");
		selenium.select("middleFinger", "label=Hypo");
		selenium.select("medArm", "label=Hyper");
		
		selenium.type("biceps", "7");
		selenium.type("shoulderAbd", "10");
		selenium.type("wristExt", "11");
		selenium.type("fingerAbd", "15");
		
		selenium.type("complaint", "My back hurts.");
		selenium.type("frequency", "All the time.");

		selenium.type("inspection", "The patient's back looks awful.");
		selenium.type("palpation", "The patient's back feels tight.");
		selenium.type("dxAction", Const.DX_ACTION);
	}
}
