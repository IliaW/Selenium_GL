package com.litecart.homework1;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class AuthorizationTest extends BaseTest {
    private final By MENU_ITEMS = By.cssSelector("#box-apps-menu li.app");
    private final By SUB_MENU_ITEMS = By.cssSelector(".app.selected ul li");
    private final By HEADER = By.cssSelector("div.panel-heading");

    @Test
    public void sequentiallyClickTest() {
        try {
            loginToAdminDashboard("testadmin", "R8MRDAYT_test");
            for (int i = 0; i < findAll(MENU_ITEMS).size(); i++) {
                findAll(MENU_ITEMS).get(i).click();
                wait(4).until(presenceOfElementLocated(HEADER));
                if (isSubMenuItemsPresent()) {
                    for (int k = 0; k < findAll(SUB_MENU_ITEMS).size(); k++) {
                        findAll(SUB_MENU_ITEMS).get(k).click();
                        wait(4).until(presenceOfElementLocated(HEADER));
                    }
                }
            }
        } finally {
            quitDriver();
        }
    }

    private void loginToAdminDashboard(String login, String password) {
        open("http://158.101.173.161/admin/");
        find("[name='username']").sendKeys(login);
        find("[name='password']").sendKeys(password);
        find("button[name='login']").click();
    }

    private boolean isSubMenuItemsPresent() {
        setImplicitlyWait(0, TimeUnit.SECONDS);
        boolean isItemsPresent = findAll(".app.selected ul").size() > 0;
        setImplicitlyWait(DEFAULT_WAIT, TimeUnit.SECONDS);
        return isItemsPresent;
    }

    public void waitFor(long timeInSeconds) {
        try {
            Thread.sleep(timeInSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}