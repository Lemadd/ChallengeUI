package com.hellofresh.challenge.ui;

import com.hellofresh.challenge.ScreenshotGrabber;
import com.hellofresh.challenge.ui.factory.DriverFactory;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.AfterSuite;
import com.thoughtworks.gauge.BeforeSuite;
import com.thoughtworks.gauge.ExecutionContext;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.RemoteWebDriver;

public class Hooks {
    public static DriverFactory driverFactory = new DriverFactory();
    
    public static RemoteWebDriver getDriver() {
        return driverFactory.getDriver();
    }
    @BeforeSuite
    public void SetUp(){
    	getDriver().manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
    }
    @AfterScenario
    public void DeleteCookies(){
        try {
            driverFactory.getStoredDriver().manage().deleteAllCookies();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterSuite
    public void TearDown(ExecutionContext context){
    	if (context.getCurrentStep().getIsFailing()) {
    		new ScreenshotGrabber().SaveScreenshot();
		} 
        driverFactory.quitDriver();
    }
}

