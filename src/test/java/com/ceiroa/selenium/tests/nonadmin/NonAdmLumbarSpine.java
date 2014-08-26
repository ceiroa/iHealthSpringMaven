package com.ceiroa.selenium.tests.nonadmin;

import org.junit.Test;

import com.ceiroa.selenium.AbstractVisit;

public class NonAdmLumbarSpine extends AbstractVisit {

	@Test
	public void testNonAdmUpperExtrem() throws Exception {
		String today = today();
		loginAsNonAdmin();
		
		//Create new visit
		this.createNewLumbarSpineVisit(today);
		enterCommonValues(today);
		
		selenium.click("//input[@value='Submit']");
		selenium.waitForPageToLoad("30000");
		
		assertTrue(selenium.isTextPresent("Visit successfully created"));
		
		assertTrue(selenium.isTextPresent("New Visit"));
		
		selenium.click("link=Log Out");
		selenium.waitForPageToLoad("30000");
		
		//Verify admin user can find the visit we just created
		startManageVisits(today, "lumbarSpine");
		assertFormSpecificValues();
		assertCommonValues(today);
		
		submitVerifyLogOut();
	}
	
	public void createNewLumbarSpineVisit(String today) {
		startNewVisit();

		selenium.click("//input[@value='Lumbar Spine']");
		selenium.waitForPageToLoad("30000");
		
		//Form-specific data entry
		selenium.type("flex", "1");
		selenium.type("llf", "2");
		selenium.type("llr", "3");
		selenium.type("ext", "4");
		selenium.type("rlf", "5");
		selenium.type("rlr", "6");
		selenium.select("valsavas", "label=+");
		selenium.select("straightLeg", "label=-");
		selenium.select("browstringTest", "label=+");
		selenium.select("lasegueTest", "label=-");
		selenium.select("elyTest", "label=+");
		selenium.select("thomasTest", "label=-");
		selenium.select("springTest", "label=+");
		selenium.select("trenderlenburgTest", "label=-");
		selenium.select("bilateralLegRaise", "label=+");
		selenium.select("pelvicRock", "label=-");
		selenium.select("patrickFabere", "label=-");
		selenium.select("patrickFabere", "label=+");
		selenium.select("milgram", "label=-");
		selenium.select("medLegFoot", "label=Hyper");
		selenium.select("latLeg", "label=Normal");
		selenium.select("latFoot", "label=Hypo");
		selenium.type("patellar", "1");
		selenium.type("hamstring", "2");
		selenium.type("achilles", "3");
		selenium.type("antTibialis", "4");
		selenium.type("extHallucis", "5");
		selenium.type("peroneus", "6");
	}
	
	@Override
	protected void assertFormSpecificValues() {
		assertEquals("1", selenium.getValue("flex"));
		assertEquals("2", selenium.getValue("llf"));
		assertEquals("3", selenium.getValue("llr"));
		assertEquals("4", selenium.getValue("ext"));
		assertEquals("5", selenium.getValue("rlf"));
		assertEquals("6", selenium.getValue("rlr"));
		assertEquals("plus", selenium.getValue("valsavas"));
		assertEquals("plus", selenium.getValue("elyTest"));
		assertEquals("plus", selenium.getValue("bilateralLegRaise"));
		assertEquals("minus", selenium.getValue("pelvicRock"));
		assertEquals("hyper", selenium.getValue("medLegFoot"));
		assertEquals("normal", selenium.getValue("latLeg"));
		assertEquals("hypo", selenium.getValue("latFoot"));
		assertEquals("3", selenium.getValue("achilles"));
		assertEquals("4", selenium.getValue("antTibialis"));
		assertEquals("5", selenium.getValue("extHallucis"));
		assertEquals("6", selenium.getValue("peroneus"));
	}
}
