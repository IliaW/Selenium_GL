package com.litecart.homework2;

import org.assertj.core.api.SoftAssertions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CartTest {
    private static WebDriver wd;
    private boolean cookiesAccepted = false;
    private final String MAIN_URL = "http://158.101.173.161";
    private final By LIST_OF_POPULAR_PRODUCTS = By.xpath("//*[text() = 'Popular Products']/..//*[@class = 'product-column']");
    private final By LIST_OF_ADDED_PRODUCTS = By.cssSelector(".item > .row");
    private final By ITEMS_QUANTITY = By.cssSelector(".badge.quantity");
    private final By ADD_TO_CART_BUTTON = By.cssSelector("[name='add_cart_product']");
    private final By CART_BUTTON = By.cssSelector("#cart");
    private final By REMOVE_BUTTON = By.cssSelector("[name='remove_cart_item']");
    private final By EMPTY_CART_TEXT = By.xpath("//*[text() = 'There are no items in your cart.']");

    @BeforeClass
    public static void beforeClass() {
        wd = new ChromeDriver();
        wd.manage().window().setSize(new Dimension(1920, 1080));
        wd.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void afterClass() {
        wd.quit();
    }

    @Test
    public void addItemsToCartTest() {
        SoftAssertions soft = new SoftAssertions();
        for (int i = 0; i < 3; i++) {
            try {
                wd.get(MAIN_URL);
                if (!cookiesAccepted) acceptCookies();
                List<WebElement> listOfPopularProducts = wd.findElements(LIST_OF_POPULAR_PRODUCTS);
                listOfPopularProducts.get((int) (Math.random() * listOfPopularProducts.size())).click();
                WebElement numberOfItemsInCart = wd.findElement(ITEMS_QUANTITY);
                int numOfItemsBeforeAdding = numberOfItemsInCart.getText().equals("")
                        ? 0 : Integer.parseInt(numberOfItemsInCart.getText());
                wd.findElement(ADD_TO_CART_BUTTON).click();
                sleep(1, "Wait until product added to cart.");
                soft.assertThat(wd.findElement(ITEMS_QUANTITY).getText()).isEqualTo(++numOfItemsBeforeAdding + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        wd.findElement(CART_BUTTON).click();
        soft.assertThat(wd.findElements(By.cssSelector(".item > .row")).size()).isNotEqualTo(0);
        soft.assertAll();
    }

    @Test
    public void removeItemsFromCartTest() {
        wd.get(MAIN_URL);
        wd.findElement(CART_BUTTON).click();
        while (wd.findElements(LIST_OF_ADDED_PRODUCTS).size() > 0) {
            int numberOfRowsBeforeDeleting = wd.findElements(LIST_OF_ADDED_PRODUCTS).size();
            wd.findElement(LIST_OF_ADDED_PRODUCTS).findElement(REMOVE_BUTTON).click();
            new WebDriverWait(wd, 4).until((ExpectedCondition<Boolean>) driver -> {
                int numberOfRowsAfterDeleting = driver.findElements(LIST_OF_ADDED_PRODUCTS).size();
                return numberOfRowsBeforeDeleting == ++numberOfRowsAfterDeleting;
            });
        }
        assertTrue(wd.findElement(EMPTY_CART_TEXT).isDisplayed());
    }

    private void acceptCookies() {
        WebElement element = new WebDriverWait(wd, 4).until(presenceOfElementLocated(
                By.xpath("//button[text() = 'I accept']")));
        if (element.isDisplayed()) element.click();
        cookiesAccepted = true;
    }

    private void sleep(long seconds, String reason) {
        try {
            System.out.println("Thread sleep: " + seconds + " sec. " + reason);
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}