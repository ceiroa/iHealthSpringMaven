package com.ceiroa.selenium.tests.nonadmin;

import org.junit.Test;

import com.ceiroa.selenium.AbstractVisit;

public class NonAdmUpperExtrem extends AbstractVisit {

	@Test
	public void testNonAdmUpperExtrem() throws Exception {
		String today = today();
		loginAsNonAdmin();
		
		//Create new visit
		this.createNewUpperExtremVisit(today);
		enterCommonValues(today);
		
		selenium.click("//input[@value='Submit']");
		selenium.waitForPageToLoad("30000");
	
		assertTrue(selenium.isTextPresent("Visit successfully created"));
		assertTrue(selenium.isTextPresent("New Visit"));
		
		selenium.click("link=Log Out");
		selenium.waitForPageToLoad("30000");
		
		//Verify admin user can find the visit we just created
		startManageVisits(today, "upperExtremities");
		assertFormSpecificValues();
		assertCommonValues(today);
		
		submitVerifyLogOut();
	}
	
	public void createNewUpperExtremVisit(String today) {
		startNewVisit();
		
		selenium.click("//input[@value='Upper Extremities']");
		selenium.waitForPageToLoad("30000");
		
		//Form-specific data entry
		selenium.type("shoulderFlex", "1");
		selenium.type("shoulderLr", "2");
		selenium.type("shoulderAbd", "3");
		selenium.type("shoulderExt", "4");
		selenium.type("shoulderMr", "5");
		selenium.type("shoulderAdd", "6");
		selenium.type("elbowFlex", "7");
		selenium.type("elbowPro", "8");
		selenium.type("elbowExt", "9");
		selenium.type("elbowSup", "10");
		selenium.type("wristFlex", "11");
		selenium.type("wristAbd", "12");
		selenium.type("wristExt", "13");
		selenium.type("wristAdd", "14");
		
		selenium.select("dropArmTest", "label=+");
		selenium.select("drawbarnTest", "label=-");
		selenium.select("supraspinatusTest", "label=+");
		selenium.select("apleyScratchTest", "label=-");
		selenium.select("postImpingSign", "label=+");
		selenium.select("speedTest", "label=-");
		selenium.select("crossOverImpTest", "label=+");
		selenium.select("yergasonTest", "label=-");
		selenium.select("apprehensionTest", "label=+");
		selenium.select("drawerTest", "label=-");
		selenium.select("varusStressTest", "label=+");
		selenium.select("cozensTest", "label=-");
		selenium.select("valgusStressTest", "label=+");
		selenium.select("golferElbow", "label=-");
		selenium.select("tinelSign", "label=+");
		selenium.select("pinchGripTest", "label=-");
		selenium.select("fromentTest", "label=+");
		selenium.select("phalenTest", "label=-");
		selenium.select("fingerTapTest", "label=+");
		selenium.select("finkelsteninTest", "label=-");
		selenium.select("bunnelLitter", "label=+");
	}
	
	protected void assertFormSpecificValues() {
		assertEquals("1", selenium.getValue("shoulderFlex"));
		assertEquals("2", selenium.getValue("shoulderLr"));
		assertEquals("3", selenium.getValue("shoulderAbd"));
		assertEquals("4", selenium.getValue("shoulderExt"));
		assertEquals("5", selenium.getValue("shoulderMr"));
		assertEquals("6", selenium.getValue("shoulderAdd"));
		assertEquals("7", selenium.getValue("elbowFlex"));
		assertEquals("8", selenium.getValue("elbowPro"));
		assertEquals("9", selenium.getValue("elbowExt"));
		assertEquals("10", selenium.getValue("elbowSup"));
		assertEquals("11", selenium.getValue("wristFlex"));
		assertEquals("12", selenium.getValue("wristAbd"));
		assertEquals("13", selenium.getValue("wristExt"));
		assertEquals("14", selenium.getValue("wristAdd"));
		
		assertEquals("plus", selenium.getValue("dropArmTest"));
		assertEquals("minus", selenium.getValue("drawbarnTest"));
		assertEquals("plus", selenium.getValue("supraspinatusTest"));
		assertEquals("minus", selenium.getValue("apleyScratchTest"));
		assertEquals("plus", selenium.getValue("postImpingSign"));
		assertEquals("minus", selenium.getValue("speedTest"));
		assertEquals("plus", selenium.getValue("crossOverImpTest"));
		assertEquals("minus", selenium.getValue("yergasonTest"));
		assertEquals("plus", selenium.getValue("apprehensionTest"));
		assertEquals("minus", selenium.getValue("drawerTest"));
		assertEquals("plus", selenium.getValue("varusStressTest"));
		assertEquals("minus", selenium.getValue("cozensTest"));
		assertEquals("plus", selenium.getValue("valgusStressTest"));
		assertEquals("minus", selenium.getValue("golferElbow"));
		assertEquals("plus", selenium.getValue("tinelSign"));
		assertEquals("minus", selenium.getValue("pinchGripTest"));
		assertEquals("plus", selenium.getValue("fromentTest"));
		assertEquals("minus", selenium.getValue("phalenTest"));
		assertEquals("plus", selenium.getValue("fingerTapTest"));
		assertEquals("minus", selenium.getValue("finkelsteninTest"));
		assertEquals("plus", selenium.getValue("bunnelLitter"));
	}
}
