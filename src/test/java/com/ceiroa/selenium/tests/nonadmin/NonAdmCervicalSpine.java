package com.ceiroa.selenium.tests.nonadmin;

import org.junit.Test;

import com.ceiroa.selenium.AbstractVisit;

public class NonAdmCervicalSpine extends AbstractVisit {
	
	@Test
	public void testNonAdmCervicalSpine() throws Exception {
		String today = today();
		loginAsNonAdmin();
		
		//Create new visit
		createNewCervicalSpineVisit(today);
		enterCommonValues(today);	
		selenium.click("//input[@value='Submit']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Visit successfully created"));		
		assertTrue(selenium.isTextPresent("New Visit"));
		
		selenium.click("link=Log Out");
		selenium.waitForPageToLoad("30000");
		
		//Verify admin user can find the visit we just created
		startManageVisits(today, "cervicalSpine");
		assertFormSpecificValues();
		assertCommonValues(today);
		
		submitVerifyLogOut();
	}
	
	public void createNewCervicalSpineVisit(String today) {
		startNewVisit();
		
		selenium.click("//input[@value='Cervical Spine']");
		selenium.waitForPageToLoad("30000");
		
		//Form-specific data entry
		selenium.type("flex", "1");
		selenium.type("llf", "2");
		selenium.type("llr", "3");
		selenium.type("ext", "4");
		selenium.type("rlf", "5");
		selenium.type("rlr", "6");
		
		selenium.select("jacksonComp", "label=+");
		selenium.select("maxComp", "label=-");
		selenium.select("shoulderDep", "label=+");
		selenium.select("sotoHall", "label=-");
		selenium.select("spurlings", "label=+");
		selenium.select("csDistraction", "label=-");
		selenium.select("valsavas", "label=+");
		selenium.select("baccody", "label=-");
		
		selenium.select("latArm", "label=Hyper");
		selenium.select("latForearm", "label=Normal");
		selenium.select("middleFinger", "label=Hypo");
		selenium.select("medForearm", "label=Hypo");
		selenium.select("medArm", "label=Hyper");
		
		selenium.type("biceps", "7");
		selenium.type("brachiorad", "8");
		selenium.type("triceps", "9");
		selenium.type("shoulderAbd", "10");
		selenium.type("wristExt", "11");
		selenium.type("wristFlex", "12");
		selenium.type("fingerExt", "13");
		selenium.type("fingerFlex", "14");
		selenium.type("fingerAbd", "15");
	}
	
	protected void assertFormSpecificValues() {
		assertEquals("1", selenium.getValue("flex"));
		assertEquals("2", selenium.getValue("llf"));
		assertEquals("3", selenium.getValue("llr"));
		assertEquals("4", selenium.getValue("ext"));
		assertEquals("5", selenium.getValue("rlf"));
		assertEquals("6", selenium.getValue("rlr"));
		
		assertEquals("plus", selenium.getValue("jacksonComp"));
		assertEquals("minus", selenium.getValue("maxComp"));
		assertEquals("plus", selenium.getValue("shoulderDep"));
		assertEquals("minus", selenium.getValue("sotoHall"));
		assertEquals("plus", selenium.getValue("spurlings"));
		assertEquals("minus", selenium.isTextPresent("csDistraction"));
		assertEquals("plus", selenium.isTextPresent("valsavas"));
		assertEquals("minus", selenium.isTextPresent("baccody"));
		
		assertEquals("hyper", selenium.getValue("latArm"));
		assertEquals("normal", selenium.getValue("latForearm"));
		assertEquals("hypo", selenium.getValue("middleFinger"));
		assertEquals("hypo", selenium.getValue("medForearm"));
		assertEquals("hyper", selenium.getValue("medArm"));
		
		assertEquals("7", selenium.getValue("biceps"));
		assertEquals("8", selenium.getValue("brachiorad"));
		assertEquals("9", selenium.getValue("triceps"));
		assertEquals("10", selenium.getValue("shoulderAbd"));
		assertEquals("11", selenium.getValue("wristExt"));
		assertEquals("12", selenium.getValue("wristFlex"));
		assertEquals("13", selenium.getValue("fingerExt"));
		assertEquals("14", selenium.getValue("fingerFlex"));
		assertEquals("15", selenium.getValue("fingerAbd"));
	}
}
