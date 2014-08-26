/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ceiroa.selenium.tests;

import org.junit.Test;

import com.ceiroa.selenium.AbstractBaseTest;

public class WrongCredentials extends AbstractBaseTest {
	
	@Override
	public void customSetUp() {
		// TODO Auto-generated method stub
	}

	@Override
	public void customTearDown() {
		// TODO Auto-generated method stub	
	}
	
	@Test
    public void testWrongCredentials() throws Exception {
        selenium.type("username", "test");
        selenium.type("password", "wrongpss");
        selenium.click("//input[@value='Submit']");
        selenium.waitForPageToLoad("30000");
        verifyTrue(selenium.isTextPresent("Wrong Username"));
    }	
}
