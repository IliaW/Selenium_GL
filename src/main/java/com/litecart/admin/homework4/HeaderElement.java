package com.litecart.admin.homework4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HeaderElement {
    private WebDriver wd;
    private final By ITEMS_QUANTITY = By.cssSelector(".badge.quantity");
    private final By CART_BUTTON = By.cssSelector("#cart");

    public HeaderElement(WebDriver wd) {
        this.wd = wd;
    }

    public CartPage openCart() {
        wd.findElement(CART_BUTTON).click();
        return new CartPage(wd);
    }

    public int getItemsQuantity() {
        WebElement quantity = wd.findElement(ITEMS_QUANTITY);
        return quantity.getText().equals("") ? 0 : Integer.parseInt(quantity.getText());
    }

    public By getItemsQuantityLocator() {
        return ITEMS_QUANTITY;
    }
}