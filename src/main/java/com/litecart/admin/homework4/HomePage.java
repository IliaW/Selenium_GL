package com.litecart.admin.homework4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage extends Page {
    private final String HOME_PAGE_URL = "http://158.101.173.161";
    private final By LIST_OF_POPULAR_PRODUCTS = By.xpath("//*[text() = 'Popular Products']/..//*[@class = 'product-column']");

    public HomePage(WebDriver wd) {
        super.wd = wd;
    }

    @Override
    public HomePage open() {
        wd.get(HOME_PAGE_URL);
        isLoaded();
        return this;
    }

    @Override
    public boolean isPresentUniqueElements() {
        return wd.findElement(LIST_OF_POPULAR_PRODUCTS).isDisplayed();
    }

    public ProductPage openPageWithRandomPopularProduct() {
        List<WebElement> listOfPopularProducts = wd.findElements(LIST_OF_POPULAR_PRODUCTS);
        listOfPopularProducts.get((int) (Math.random() * wd.findElements(LIST_OF_POPULAR_PRODUCTS).size())).click();
        return new ProductPage(wd);
    }
}