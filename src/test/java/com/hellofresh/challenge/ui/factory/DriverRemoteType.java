package com.hellofresh.challenge.ui.factory;

import io.github.bonigarcia.wdm.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public enum DriverRemoteType implements DriverRemoteSetup {
    CHROME {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            ChromeDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.merge(capabilities);
            chromeOptions.setHeadless(HEADLESS);
            chromeOptions.addArguments("window-size=1280x800");
            chromeOptions.addArguments("--no-default-browser-check");
            return new ChromeDriver(chromeOptions);
        }
    },
    EDGE {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            EdgeDriverManager.edgedriver().setup();
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.merge(capabilities);

            return new EdgeDriver(edgeOptions);
        }
    },
    FIREFOX {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            FirefoxDriverManager.firefoxdriver().setup();
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.merge(capabilities);
            firefoxOptions.setHeadless(HEADLESS);

            return new FirefoxDriver(firefoxOptions);
        }
    },
    IE {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            InternetExplorerDriverManager.iedriver().setup();
            InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
            internetExplorerOptions.merge(capabilities);
            internetExplorerOptions.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
            internetExplorerOptions.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
            internetExplorerOptions.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);

            return new InternetExplorerDriver(internetExplorerOptions);
        }

        @Override
        public String toString() {
            return "internet explorer";
        }
    };

    public final static boolean HEADLESS = true;//Boolean.getBoolean("headless");

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
