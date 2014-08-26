package com.ceiroa.selenium.tests.nonadmin;

import org.junit.Test;

import com.ceiroa.selenium.AbstractBaseTest;
import com.ceiroa.selenium.Const;

public class NonAdmChangePssw extends AbstractBaseTest {
	
	@Override
	public void customSetUp() {
		// TODO Auto-generated method stub
	}

	@Override
	public void customTearDown() {
		// TODO Auto-generated method stub	
	}
	
	@Test
	public void testNonAdmChangePssw() throws Exception {
		loginAsNonAdmin();
		selenium.click("link=Change Password");
		selenium.waitForPageToLoad("30000");
		selenium.type("password1", Const.TEST_NON_ADMIN_PSSW);
		selenium.type("password2", Const.TEST_NON_ADMIN_PSSW);
		selenium.click("//input[@value='Submit']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Password successfully updated"));
		selenium.click("link=Log Out");
		selenium.waitForPageToLoad("30000");
		loginAsNonAdmin();
		assertTrue(selenium.isTextPresent("Welcome"));
	}
}
