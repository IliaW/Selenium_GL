package com.litecart.homework1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    private WebDriver wd;
    protected final int DEFAULT_WAIT = 4;

    public BaseTest() {
        wd = new ChromeDriver();
        wd.manage().window().maximize();
        setImplicitlyWait(DEFAULT_WAIT, TimeUnit.SECONDS);
    }

    public void quitDriver() {
        wd.quit();
    }

    public void open(String url) {
        wd.get(url);
    }

    public WebElement find(By locator) {
        return wd.findElement(locator);
    }

    public WebElement find(String locator) {
        return wd.findElement(By.cssSelector(locator));
    }

    public List<WebElement> findAll(By locator) {
        return wd.findElements(locator);
    }

    public List<WebElement> findAll(String locator) {
        return wd.findElements(By.cssSelector(locator));
    }

    public void setImplicitlyWait(long time, TimeUnit unit) {
        wd.manage().timeouts().implicitlyWait(time, unit);
    }
}