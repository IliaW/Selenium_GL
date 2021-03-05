package com.litecart.homework1;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class SequentiallyClick extends BaseTest {
    private final By MENU_ITEMS = By.cssSelector("#box-apps-menu li.app");
    private final By SUB_MENU_ITEMS = By.cssSelector(".app.selected ul li");
    private final By HEADER = By.cssSelector("div.panel-heading");

    @After
    public void after() {
        quitDriver();
    }

    @Test
    public void sequentiallyClickTest() {
        loginToAdminDashboard("testadmin", "R8MRDAYT_test");
        int menuItemsCount = findAll(MENU_ITEMS).size();
        for (int i = 0; i < menuItemsCount; i++) {
            findAll(MENU_ITEMS).get(i).click();
            assertTrue(find(HEADER).isDisplayed());
            if (isSubMenuItemsPresent()) {
                int subMenuItemsCount = findAll(SUB_MENU_ITEMS).size();
                for (int k = 0; k < subMenuItemsCount; k++) {
                    findAll(SUB_MENU_ITEMS).get(k).click();
                    assertTrue(find(HEADER).isDisplayed());
                }
            }
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
}