package com.hellofresh.challenge.ui.factory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hellofresh.challenge.GaugeLog;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static com.hellofresh.challenge.ui.factory.DriverRemoteType.CHROME;
import static com.hellofresh.challenge.ui.factory.DriverRemoteType.valueOf;

public class DriverFactory {
    private RemoteWebDriver driver;
    private DriverRemoteType selectedDriverType;

    private final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private final String systemArchitecture = System.getProperty("os.arch");
    private final boolean useRemoteWebDriver = Boolean.getBoolean("remoteDriver");

    public DriverFactory() {
        DriverRemoteType driverType = CHROME;
        String browser = System.getenv("browser").toUpperCase();
        try {
            driverType = valueOf(browser);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        selectedDriverType = driverType;
    }
    public RemoteWebDriver getDriver(){
        if (null == driver) {
            instantiateWebDriver(selectedDriverType);
        }

        return driver;
    }
    public RemoteWebDriver getStoredDriver() {
        return driver;
    }
    
    public void quitDriver() {
        if (null != driver) {
            driver.quit();
            driver = null;
        }
    }
    private void instantiateWebDriver(DriverRemoteType driverType) {
        GaugeLog.Add("Local Operating System: " + operatingSystem);
        GaugeLog.Add("Local Architecture: " + systemArchitecture);
        GaugeLog.Add("Selected Browser: " + selectedDriverType);
        GaugeLog.Add("Connecting to Selenium Grid: " + useRemoteWebDriver);
        GaugeLog.Add("Local Operating System: " + operatingSystem);

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        if (useRemoteWebDriver) {
            URL seleniumGridURL = getHubUrl();
            desiredCapabilities = SetAdditionalCapabilities();
            String desiredBrowserVersion = System.getProperty("desiredBrowserVersion");
            String desiredPlatform = System.getProperty("desiredPlatform");

            if (null != desiredPlatform && !desiredPlatform.isEmpty()) {
                desiredCapabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));
            }

            if (null != desiredBrowserVersion && !desiredBrowserVersion.isEmpty()) {
                desiredCapabilities.setVersion(desiredBrowserVersion);
            }

            desiredCapabilities.setBrowserName(selectedDriverType.toString());

            driver = new RemoteWebDriver(seleniumGridURL, desiredCapabilities);
        } else {
            driver = driverType.getWebDriverObject(desiredCapabilities);
        }
    }
    private static DesiredCapabilities SetAdditionalCapabilities(){
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        HashMap<String, String> map = new Gson().fromJson(System.getenv("hub_capabilities"), new TypeToken<HashMap<String, Integer>>() {}.getType());
        for (HashMap.Entry<String, String> entry : map.entrySet()) {
            desiredCapabilities.setCapability(entry.getKey(),entry.getValue());
        }
        return desiredCapabilities;
    }
    private static URL getHubUrl(){
        URL hubUrl = null;
        try {
            if (System.getenv("cloud_hub_username").isEmpty() || System.getenv("cloud_hub_auth_key").isEmpty()){
                hubUrl = new URL(System.getenv("hub_url"));
            }else{
                String hubUserName = System.getenv("hub_username");
                if (hubUserName.contains("@")) {
                    hubUserName = hubUserName.replace("@", "%40");
                }
                hubUrl = new URL("http://" + hubUserName + ":" + System.getenv("hub_auth_key") + "@" + System.getenv("hub_url"));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return hubUrl;
    }
}
