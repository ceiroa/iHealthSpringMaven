package com.ceiroa.selenium.tests.admin;

import org.junit.Test;

import com.ceiroa.selenium.AbstractBaseTest;
import com.ceiroa.selenium.Const;
import com.ceiroa.selenium.DBHelper;

public class AdmManageClients extends AbstractBaseTest {

	@Override
	public void customSetUp() {
		DBHelper.deleteTestClient();
		DBHelper.insertTestClientIfNotExists();
	}
	
	@Override
	public void customTearDown() {
		DBHelper.deleteTestClient();
	}
	
	@Test
	public void testAdmManageClients() throws Exception {
		loginAsAdmin();
		selenium.click("link=Manage Clients");
		selenium.waitForPageToLoad("60000");
		
		//Find Client
		selenium.type("lastName", Const.LAST_NAME);
		selenium.click("//input[@value='Search']");
		selenium.waitForPageToLoad("30000");
		
		//Edit Client
		selenium.click("//input[@value='View/Edit']");
		selenium.waitForPageToLoad("30000");
		selenium.type("middleInitial", "NA");
		selenium.click("//input[@value='Save']");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Manage Clients");
		selenium.waitForPageToLoad("30000");
		selenium.type("lastName", Const.LAST_NAME);
		selenium.click("//input[@value='Search']");
		selenium.waitForPageToLoad("30000");
		selenium.click("//input[@value='View/Edit']");
		selenium.waitForPageToLoad("30000");
		verifyEquals("NA", selenium.getValue("middleInitial"));
		selenium.click("link=Log Out");
		selenium.waitForPageToLoad("30000");
	}
}
