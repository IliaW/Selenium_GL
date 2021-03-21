package com.litecart.admin.homework4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CartPage extends Page {
    private final String CART_URL = "http://158.101.173.161/checkout";
    private final By LIST_OF_ADDED_PRODUCTS = By.cssSelector(".item > .row");
    private final By REMOVE_BUTTON = By.cssSelector("[name='remove_cart_item']");
    private final By EMPTY_CART_TEXT = By.xpath("//*[text() = 'There are no items in your cart.']");

    public CartPage(WebDriver wd) {
        super.wd = wd;
    }

    @Override
    public Page open() {
        wd.get(CART_URL);
        isLoaded();
        return this;
    }

    @Override
    public boolean isPresentUniqueElements() {
        return wd.getCurrentUrl().equals(CART_URL);
    }

    public List<WebElement> getListOfAddedProduct() {
        return wd.findElements(LIST_OF_ADDED_PRODUCTS);
    }

    public void removeAllProductsFromCart() {
        while (wd.findElements(LIST_OF_ADDED_PRODUCTS).size() > 0) {
            int numberOfRowsBeforeDeleting = wd.findElements(LIST_OF_ADDED_PRODUCTS).size();
            wd.findElement(LIST_OF_ADDED_PRODUCTS).findElement(REMOVE_BUTTON).click();
            new WebDriverWait(wd, 4).until((ExpectedCondition<Boolean>) driver -> {
                int numberOfRowsAfterDeleting = driver.findElements(LIST_OF_ADDED_PRODUCTS).size();
                return numberOfRowsBeforeDeleting == ++numberOfRowsAfterDeleting;
            });
        }
    }

    public boolean isCartEmpty() {
        return wd.findElement(EMPTY_CART_TEXT).isDisplayed();
    }
}