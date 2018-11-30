package com.hellofresh.challenge.ui.factory;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public interface DriverRemoteSetup {
    RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities);
}
