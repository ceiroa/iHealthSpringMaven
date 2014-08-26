package com.ceiroa.selenium.tests.nonadmin;

import org.junit.Test;

import com.ceiroa.selenium.AbstractVisit;

public class NonAdmLowerExtrem extends AbstractVisit {

	@Test
	public void testNonAdmUpperExtrem() throws Exception {
		String today = today();
		loginAsNonAdmin();
		
		//Create new visit
		this.createNewLowerExtremVisit(today);
		enterCommonValues(today);	
		selenium.click("//input[@value='Submit']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Visit successfully created"));
		assertTrue(selenium.isTextPresent("New Visit"));
		
		selenium.click("link=Log Out");
		selenium.waitForPageToLoad("30000");
		
		//Verify admin user can find the visit we just created
		startManageVisits(today, "lowerExtremities");	
		assertFormSpecificValues();
		assertCommonValues(today);
		
		submitVerifyLogOut();	
	}
	
	public void createNewLowerExtremVisit(String today) {
		startNewVisit();
		
		selenium.click("//input[@value='Lower Extremities']");
		selenium.waitForPageToLoad("30000");
		
		//Form-specific data entry
		selenium.type("kneeFlex", "1");
		selenium.type("kneeExt", "2");
		selenium.type("pf", "3");
		selenium.type("df", "4");
		selenium.type("inv", "5");
		selenium.type("ev", "6");
		selenium.type("hipFlex", "7");
		selenium.type("hipExt", "8");
		selenium.type("hipAbd", "9");
		selenium.type("hipAdd", "10");
		selenium.type("hipLr", "11");
		selenium.type("hipMr", "12");
		selenium.type("hipHyp", "13");
		
		selenium.select("trendelenbarg", "label=+");
		selenium.select("legLength", "label=-");
		selenium.select("thomasTest", "label=-");
		selenium.select("oberTest", "label=+");
		selenium.select("mcMurray", "label=+");
		selenium.select("apleyTest", "label=-");
		selenium.select("bounceHome", "label=+");
		selenium.select("patellaGrinding", "label=-");
		selenium.select("apprehensionPatella", "label=+");
		selenium.select("tinelSign", "label=-");
		selenium.select("effusionTest", "label=+");
		selenium.select("rigidFlatFeet", "label=-");
		selenium.select("tibialTorsion", "label=+");
		selenium.select("homansSign", "label=-");
		selenium.select("forefootTest", "label=+");
		selenium.select("ankleDorsiflexion", "label=-");
	}
	
	protected void assertFormSpecificValues() {
		assertEquals("1", selenium.getValue("kneeFlex"));
		assertEquals("2", selenium.getValue("kneeExt"));
		assertEquals("3", selenium.getValue("pf"));
		assertEquals("4", selenium.getValue("df"));
		assertEquals("5", selenium.getValue("inv"));
		assertEquals("6", selenium.getValue("ev"));
		assertEquals("7", selenium.getValue("hipFlex"));
		assertEquals("8", selenium.getValue("hipExt"));
		assertEquals("9", selenium.getValue("hipAbd"));
		assertEquals("10", selenium.getValue("hipAdd"));
		assertEquals("11", selenium.getValue("hipLr"));
		assertEquals("12", selenium.getValue("hipMr"));
		assertEquals("13", selenium.getValue("hipHyp"));
		
		assertEquals("plus", selenium.getValue("trendelenbarg"));
		assertEquals("minus", selenium.getValue("legLength"));
		assertEquals("minus", selenium.getValue("thomasTest"));
		assertEquals("plus", selenium.getValue("oberTest"));
		assertEquals("plus", selenium.getValue("mcMurray"));
		assertEquals("minus", selenium.getValue("apleyTest"));
		assertEquals("plus", selenium.getValue("bounceHome"));
		assertEquals("minus", selenium.getValue("patellaGrinding"));
		assertEquals("plus", selenium.getValue("apprehensionPatella"));
		assertEquals("minus", selenium.getValue("tinelSign"));
		assertEquals("minus", selenium.getValue("rigidFlatFeet"));
		assertEquals("plus", selenium.getValue("tibialTorsion"));
		assertEquals("minus", selenium.getValue("homansSign"));
		assertEquals("plus", selenium.getValue("forefootTest"));
		assertEquals("minus", selenium.getValue("ankleDorsiflexion"));
	}
}
