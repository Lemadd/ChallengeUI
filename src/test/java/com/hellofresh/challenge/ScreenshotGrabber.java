package com.hellofresh.challenge;

import com.hellofresh.challenge.ui.Hooks;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.screenshot.ICustomScreenshotGrabber;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotGrabber implements ICustomScreenshotGrabber {
    private final WebDriver driver = Hooks.getDriver();

    public void SaveScreenshot() {
        byte[] encoded = Base64.encodeBase64(takeScreenshot());
        Gauge.writeMessage("<img src='data:image/png;base64," + new String(encoded) + "'>");
    }

    public byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
