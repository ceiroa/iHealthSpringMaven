package com.ceiroa.selenium.tests.nonadmin;

import org.junit.Test;

import com.ceiroa.selenium.AbstractBaseTest;
import com.ceiroa.selenium.Const;
import com.ceiroa.selenium.DBHelper;

public class NonAdmUpload extends AbstractBaseTest {

	@Override
	public void customSetUp() {
		DBHelper.deleteTestClient();
		DBHelper.deleteTestUpload();
		DBHelper.insertTestClientIfNotExists();
	}

	@Override
	public void customTearDown() {
		DBHelper.deleteTestUpload();
		DBHelper.deleteTestClient();
	}
	
	@Test
	public void testNonAdmUpload() throws Exception {
		loginAsNonAdmin();
		selenium.click("link=Upload Documents");
		selenium.waitForPageToLoad("30000");
		selenium.type("lastName", Const.LAST_NAME);
		selenium.click("//input[@value='Search']");
		selenium.waitForPageToLoad("30000");
		verifyEquals("Go", selenium.getValue("//input[@value='Go']"));
		selenium.type("numberOfFiles", "2");
		selenium.click("//input[@value='Go']");
		selenium.waitForPageToLoad("30000");
		
		String os = System.getProperty("os.name");
		String file1Path; 
		String file2Path;
		
        //Windows server
        if(os.startsWith("Win")) {
        	file1Path = "C:\\Program Files\\Tomcat_7\\testingResources\\deleteme";
        	file2Path = "C:\\Program Files\\Tomcat_7\\testingResources\\deleteme2";
        } else {
        //Mac or server
        	file1Path = "/Applications/Tomcat/testingResources/";
        	file2Path = "/Applications/Tomcat/testingResources/";
        }
        
		selenium.type("file1", file1Path);
		selenium.type("//div[@id='content']/form/table/tbody/tr[3]/td/input", file2Path);
		selenium.click("//input[@value='Upload Files']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("File deleteme successfully uploaded."));
		verifyTrue(selenium.isTextPresent("File deleteme2 successfully uploaded."));
		selenium.click("link=Log Out");
		selenium.waitForPageToLoad("30000");
	}

}
