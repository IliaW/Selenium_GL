package com.litecart.admin.homework4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage extends Page {
    private final By ADD_TO_CART_BUTTON = By.cssSelector("[name='add_cart_product']");
    private HeaderElement header;

    public ProductPage(WebDriver wd) {
        super.wd = wd;
        header = new HeaderElement(wd);
    }

    @Override
    public Page open() {
        return null;
    }

    @Override
    public boolean isPresentUniqueElements() {
        return wd.findElement(ADD_TO_CART_BUTTON).isDisplayed();
    }

    public ProductPage addProductToCart() {
        int numOfItemsBeforeAdding = header.getItemsQuantity();
        wd.findElement(ADD_TO_CART_BUTTON).click();
        new WebDriverWait(wd, 2).until(ExpectedConditions.textToBe(header.getItemsQuantityLocator(), Integer.toString(++numOfItemsBeforeAdding)));
        return this;
    }
}