package com.litecart.admin.homework4;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public abstract class Page {
    protected WebDriver wd;
    protected static boolean cookiesAccepted = false;

    public abstract Page open();

    public abstract boolean isPresentUniqueElements();

    protected boolean isLoaded() {
        if (!cookiesAccepted) acceptCookies();
        if (isPageCompleteState() && isPresentUniqueElements()) return true;
        else throw new NoSuchElementException("Not on the right page");
    }

    private boolean isPageCompleteState() {
        JavascriptExecutor jse = (JavascriptExecutor) wd;
        return jse.executeScript("return document.readyState").equals("complete");
    }

    protected void acceptCookies() {
        WebElement element = new WebDriverWait(wd, 4).until(presenceOfElementLocated(
                By.xpath("//button[text() = 'I accept']")));
        if (element.isDisplayed()) element.click();
        cookiesAccepted = true;
    }
}