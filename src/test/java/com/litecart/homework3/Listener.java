package com.litecart.homework3;

import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import java.io.File;
import java.io.IOException;

public class Listener extends AbstractWebDriverEventListener {

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        System.out.println("-> Searching [" + by + "] element.");
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        System.out.println("-> Element [" + by + "] was found.");
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        System.out.println("-> ERROR: " + throwable.getMessage());
        File tmpFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(tmpFile, new File(System.currentTimeMillis() + "screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}