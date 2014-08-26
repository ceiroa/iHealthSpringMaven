package com.ceiroa.selenium.tests.nonadmin;

import org.junit.Test;

import com.ceiroa.selenium.AbstractBaseTest;

public class NonAdmAccess extends AbstractBaseTest {
	
	@Override
	public void customSetUp() {
		// TODO Auto-generated method stub
	}

	@Override
	public void customTearDown() {
		// TODO Auto-generated method stub	
	}
	
	@Test
	public void testNonAdmAccess() throws Exception {
		loginAsNonAdmin();
		selenium.open("/iHealth/manageClients");
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("Manage Clients"));
	}
}
