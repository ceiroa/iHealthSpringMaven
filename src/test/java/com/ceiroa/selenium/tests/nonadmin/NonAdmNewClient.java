package com.ceiroa.selenium.tests.nonadmin;

import org.junit.Test;

import com.ceiroa.selenium.AbstractBaseTest;
import com.ceiroa.selenium.Const;
import com.ceiroa.selenium.DBHelper;

public class NonAdmNewClient extends AbstractBaseTest {
	
	@Override
	public void customSetUp() {
		DBHelper.deleteTestClient();
	}
	
	@Override
	public void customTearDown() {
		DBHelper.deleteTestClient();	
	}
	
	@Test
	public void testNonAdmNewClient() throws Exception {
		loginAsNonAdmin();
		
		//Create new client
		selenium.click("link=New Client");
		selenium.waitForPageToLoad("30000");
		selenium.type("firstName", Const.FIRST_NAME);
		selenium.type("middleInitial", "test");
		selenium.type("lastName", Const.LAST_NAME);
		selenium.type("gender", "m");
		selenium.type("address", "215 main st");
		selenium.type("city", "L.A.");
		selenium.type("state", "CA");
		selenium.type("zipcode", "555555");
		selenium.type("email", Const.EMAIL);
		selenium.type("referrer", "john o'brian");
		selenium.type("homePhone", "666 444 444");
		selenium.type("cellPhone", Const.CELLPHONE);
		selenium.type("dob", Const.DOB);
		selenium.type("ssn", Const.SSN);
		selenium.type("driverLicense", "IL6054948343");
		selenium.type("employer", "Comp AF");
		selenium.type("occupation", "boss");
		selenium.type("employerAddress", "444 fake st.");
		selenium.type("employerPhoneNum", "3333 3434432");
		selenium.type("contactName", "tom");
		selenium.type("contactName", "tom johnson");
		selenium.type("contactRelation", "spouse");
		selenium.type("contactPhone", "22222122312412");
		selenium.type("insurance", "bcbs");
		selenium.type("insuranceAddress", "444 main lane");
		selenium.type("policyHolderName", "tom johnson");
		selenium.type("policyHolderAddress", "215 main st");
		selenium.type("policyHolderDob", "02/22/1977");
		selenium.type("policyHolderSsn", "333483334");
		selenium.type("policyNumber", "ASD234234");
		selenium.type("groupNumber", "3WERF34523");
		selenium.type("policyHolderRelation", "spouse");
		selenium.type("accidentInfo", "Direct crash - don't do that");
		selenium.type("compInfo", "He is getting $60/day (lucky's)");
		selenium.click("//input[@value='Save']");
		selenium.waitForPageToLoad("30000");
		
		//Confirm success
		verifyTrue(selenium.isTextPresent("Client successfully created"));
		
		//Log out and log in as admin
		//selenium.click("link=Log Out");
		selenium.open("iHealth/logout.htm");
		selenium.waitForPageToLoad("30000");
		loginAsAdmin();
		
		selenium.click("link=Manage Clients");
		selenium.waitForPageToLoad("30000");
		
		//Find client we just created
		selenium.type("lastName", Const.LAST_NAME);
		selenium.click("//input[@value='Search']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent(Const.LAST_NAME));
		verifyEquals("View/Edit", selenium.getValue("//input[@value='View/Edit']"));
		
		//Verify client has values we entered
		selenium.click("//input[@value='View/Edit']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Patient Information"));
		verifyEquals(Const.LAST_NAME, selenium.getValue("lastName"));
		verifyEquals(Const.FIRST_NAME, selenium.getValue("firstName"));
		verifyEquals(Const.SSN, selenium.getValue("ssn"));
		selenium.click("//input[@value='Save']");
		selenium.waitForPageToLoad("30000");
		
		//Find client we just updated
		selenium.click("link=Manage Clients");
		selenium.waitForPageToLoad("30000");
		selenium.type("lastName", Const.LAST_NAME);
		selenium.click("//input[@value='Search']");
		selenium.waitForPageToLoad("30000");
		
		//Delete client
		selenium.click("//input[@value='Delete']");
		assertTrue(selenium.getConfirmation().matches("^Are you sure you want to delete it[\\s\\S]$"));
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Client successfully deleted"));
		selenium.click("link=Log Out");
		selenium.waitForPageToLoad("30000");
	}
}
