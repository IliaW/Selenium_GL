package com.litecart.homework3;

import org.assertj.core.api.SoftAssertions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class WindowsTest {
    private static EventFiringWebDriver ewd;
    private final String COUNTRIES_PAGE = "http://158.101.173.161/admin/?app=countries&doc=countries";
    private final By ADD_NEW_COUNTRY_BUTTON = By.xpath("//*[contains(text(), 'Add New Country')]");
    private final By LINKS_LIST = By.cssSelector(".fa-external-link");
    private final By LOGO = By.cssSelector("#logotype");


    @BeforeClass
    public static void beforeClass() {
        ewd = new EventFiringWebDriver(new ChromeDriver());
        ewd.register(new Listener());
        ewd.manage().window().setSize(new Dimension(1920, 1080));
        ewd.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

    }

    @AfterClass
    public static void afterClass() {
        ewd.quit();
    }

    @Test
    public void externalLinksTest() {
        SoftAssertions soft = new SoftAssertions();
        loginToAdminDashboard("testadmin", "R8MRDAYT_test");
        ewd.findElement(LOGO).isDisplayed();
        ewd.get(COUNTRIES_PAGE);
        ewd.findElement(ADD_NEW_COUNTRY_BUTTON).click();
        List<WebElement> links = ewd.findElements(LINKS_LIST);
        for (WebElement link : links) {
            String mainWindow = ewd.getWindowHandle();
            Set<String> allWindows = ewd.getWindowHandles();
            try {
                link.click();
                String newWindows = new WebDriverWait(ewd, 4).until(anyWindowOtherThan(allWindows));
                ewd.switchTo().window(newWindows);
                soft.assertThat(isPageCompleteState()).isTrue();
                ewd.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ewd.switchTo().window(mainWindow);
            }
        }
        soft.assertAll();
    }

    private ExpectedCondition<String> anyWindowOtherThan(Set<String> windows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver input) {
                Set<String> handles = ewd.getWindowHandles();
                handles.removeAll(windows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }

    private boolean isPageCompleteState() {
        JavascriptExecutor jse = (JavascriptExecutor) ewd;
        return jse.executeScript("return document.readyState").equals("complete");
    }

    private void loginToAdminDashboard(String login, String password) {
        ewd.get("http://158.101.173.161/admin/");
        ewd.findElement(By.cssSelector("[name='username']")).sendKeys(login);
        ewd.findElement(By.cssSelector("[name='password']")).sendKeys(password);
        ewd.findElement(By.cssSelector("button[name='login']")).click();
    }
}