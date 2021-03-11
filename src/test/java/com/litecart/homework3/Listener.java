package com.litecart.homework3;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

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
    }
}