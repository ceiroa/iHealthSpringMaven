package com.ceiroa.selenium.tests.admin;

import org.junit.Test;

import com.ceiroa.selenium.AbstractBaseTest;
import com.ceiroa.selenium.Const;
import com.ceiroa.selenium.DBHelper;

public class AdmManageUsersNewNonAdm extends AbstractBaseTest {

	@Override
	public void customSetUp() {
		DBHelper.deleteAllUsersExceptTestAndTest2();
	}
	
	@Override
	public void customTearDown() {
		DBHelper.deleteAllUsersExceptTestAndTest2();
	}
	
	@Test
	public void testAdmManageUsersNewNonAdm() throws Exception {
		loginAsAdmin();
		
		//Create new non-admin user
		selenium.click("link=Manage Users");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Create New User");
		selenium.waitForPageToLoad("30000");
		selenium.type("firstName", "admin");
		selenium.type("lastName", "testuser");
		selenium.type("username", Const.DELETEME_NON_ADMIN_USER);
		selenium.type("password1", Const.DELETEME_NON_ADMIN_PASSW);
		selenium.type("password2", Const.DELETEME_NON_ADMIN_PASSW);
		selenium.select("userType", "label=Non Admin");
		selenium.type("email", "crme1980@gmail.com");
		selenium.click("//input[@value='Submit']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("User successfully created"));
		selenium.click("link=Log Out");
		selenium.waitForPageToLoad("30000");
		
		//Login with new non-admin user
		selenium.type("username", Const.DELETEME_NON_ADMIN_USER);
		selenium.type("password", Const.DELETEME_NON_ADMIN_PASSW);
		selenium.click("//input[@value='Submit']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Welcome"));
		assertFalse(selenium.isTextPresent("Manage Users"));
		selenium.click("link=Log Out");
		selenium.waitForPageToLoad("30000");
		
		//Delete new non-admin user
		DBHelper.deleteAllUsersExceptTestAndTest2();
		
		//Login again with new non-admin user
		selenium.type("username", Const.DELETEME_NON_ADMIN_USER);
		selenium.type("password", Const.DELETEME_NON_ADMIN_PASSW);
		selenium.click("//input[@value='Submit']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Wrong Username"));
	}
}
