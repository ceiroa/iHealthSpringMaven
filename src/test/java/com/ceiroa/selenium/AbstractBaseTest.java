package com.ceiroa.selenium;

import org.junit.After;
import org.junit.Before;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestCase;

public abstract class AbstractBaseTest extends SeleneseTestCase {
	
	@Override
	@Before
    public void setUp() throws Exception {
        selenium = new DefaultSelenium("localhost", 4444, "*googlechrome", "http://localhost:8080/");
        //selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://localhost:8080/");
        //selenium.setSpeed("200");
        selenium.start();
        selenium.open("iHealth/logout.htm");
        customSetUp();
    }
	
	public abstract void customSetUp();
	public abstract void customTearDown();
	
	@Override
	@After
    public void tearDown() throws Exception {
		customTearDown();
		selenium.stop();
    }
	
	protected void loginAsAdmin() {
		selenium.type("username", Const.TEST_ADMIN_USER);
		selenium.type("password", Const.TEST_ADMIN_PSSW);
		selenium.click("//input[@value='Submit']");
		selenium.waitForPageToLoad("30000");
	}
	
	protected void loginAsNonAdmin() {
		selenium.type("username", Const.TEST_NON_ADMIN_USER);
		selenium.type("password", Const.TEST_NON_ADMIN_PSSW);
		selenium.click("//input[@value='Submit']");
		selenium.waitForPageToLoad("30000");
	}
}
