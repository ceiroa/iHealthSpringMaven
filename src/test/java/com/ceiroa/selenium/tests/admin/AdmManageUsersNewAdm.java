package com.ceiroa.selenium.tests.admin;

import org.junit.Test;

import com.ceiroa.selenium.AbstractBaseTest;
import com.ceiroa.selenium.Const;
import com.ceiroa.selenium.DBHelper;

public class AdmManageUsersNewAdm extends AbstractBaseTest {
	
	@Override
	public void customSetUp() {
		DBHelper.deleteAllUsersExceptTestAndTest2();
	}
	
	@Override
	public void customTearDown() {
		DBHelper.deleteAllUsersExceptTestAndTest2();
	}
	
	@Test
	public void testAdmManageUsersNewAdm() throws Exception {
		loginAsAdmin();
		
		//Create new admin user
		selenium.click("link=Manage Users");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Create New User");
		selenium.waitForPageToLoad("30000");
		selenium.type("firstName", "admin");
		selenium.type("lastName", "testuser");
		selenium.type("username", Const.DELETEME_ADMIN_USER);
		selenium.type("password1", Const.DELETEME_ADMIN_PASSW);
		selenium.type("password2", Const.DELETEME_ADMIN_PASSW);
		selenium.type("email", "crme1980@gmail.com");
		selenium.click("//input[@value='Submit']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("User successfully created"));
		selenium.click("link=Log Out");
		selenium.waitForPageToLoad("30000");
		
		//Login with new admin user
		selenium.type("username", Const.DELETEME_ADMIN_USER);
		selenium.type("password", Const.DELETEME_ADMIN_PASSW);
		selenium.click("//input[@value='Submit']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Welcome"));
		selenium.click("link=Manage Users");
		selenium.waitForPageToLoad("30000");
		
		//Deactivate new admin user
		selenium.click("//div[@id='content']/table/tbody/tr[4]/td[5]/form/input[3]");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Log Out");
		selenium.waitForPageToLoad("30000");
		
		//Login again with new admin user
		selenium.type("username", Const.DELETEME_ADMIN_USER);
		selenium.type("password", Const.DELETEME_ADMIN_PASSW);
		selenium.click("//input[@value='Submit']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Wrong Username"));
	}
}
