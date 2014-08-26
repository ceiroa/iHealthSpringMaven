package com.ceiroa.selenium;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class AbstractVisit extends AbstractBaseTest {

	protected Calendar cal = Calendar.getInstance();
	protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
	protected abstract void assertFormSpecificValues();
	
	@Override
	public void customSetUp() {
		DBHelper.deleteAllTestVisits();
		DBHelper.deleteTestClient();
		DBHelper.insertTestClientIfNotExists();
	}
	
	@Override
	public void customTearDown() {
		DBHelper.deleteAllTestVisits();
	}
	
	protected String today() {
		return sdf.format(cal.getTime());
	}
	
	public void enterCommonValues(String today) {
		assertEquals(today, selenium.isTextPresent("dateCreated"));
		selenium.select("sameComplaint", "label=No");
		selenium.select("painChange", "label=Better");
		
		selenium.select("achingDullSore", "label=Yes");
		selenium.select("numbnessTingling", "label=Yes");
		selenium.select("sharpStabbing", "label=Yes");
		selenium.select("swelling", "label=Yes");
		selenium.select("snapPopGrind", "label=Yes");
		selenium.select("painLevel", "label=6");
		
		selenium.type("complaint", "My back hurts.");
		selenium.type("frequency", "All the time.");

		selenium.type("inspection", "The patient's back looks awful.");
		selenium.type("palpation", "The patient's back feels tight.");
		selenium.type("dxAction", Const.DX_ACTION);
	}
	
	public void assertCommonValues(String today) {
		verifyTrue(selenium.isTextPresent(today));
		assertEquals("no", selenium.getValue("sameComplaint"));
		assertEquals("Better", selenium.isTextPresent("painChange"));
		
		assertEquals("yes", selenium.getValue("achingDullSore"));
		assertEquals("yes", selenium.getValue("numbnessTingling"));
		assertEquals("yes", selenium.getValue("sharpStabbing"));
		assertEquals("yes", selenium.getValue("swelling"));
		assertEquals("yes", selenium.getValue("snapPopGrind"));
		assertEquals("6", selenium.getValue("painLevel"));
		
		assertEquals("My back hurts.", selenium.getValue("complaint"));
		assertEquals("All the time.", selenium.getValue("frequency"));

		assertEquals("The patient's back looks awful.", selenium.getValue("inspection"));
		assertEquals("The patient's back feels tight.", selenium.getValue("palpation"));
		assertEquals("This visit should have been deleted.", selenium.getValue("dxAction"));
	}
	
	protected void startManageVisits(String today, String visitType) {
		loginAsAdmin();
		selenium.click("link=Manage Visits");
		selenium.waitForPageToLoad("30000");
		selenium.type("firstName", Const.FIRST_NAME);
		selenium.type("lastName", Const.LAST_NAME);
		selenium.type("date", today);
		selenium.click("//input[@value='Search']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(visitType));
		assertEquals("View/Edit", selenium.getValue("//input[@value='View/Edit']"));
		selenium.click("//input[@value='View/Edit']");
		selenium.waitForPageToLoad("30000");
	}
	
	protected void startNewVisit() {
		selenium.click("link=New Visit");
		selenium.waitForPageToLoad("30000");
		selenium.type("lastName", Const.LAST_NAME);
		selenium.click("//input[@value='Search']");
		selenium.waitForPageToLoad("30000");
	}
	
	protected void submitVerifyLogOut() {
		selenium.click("//input[@value='Submit']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Visit successfully updated"));
		//selenium.click("link=Log Out");
		selenium.open("iHealth/logout.htm");
		selenium.waitForPageToLoad("30000");
	}
}
