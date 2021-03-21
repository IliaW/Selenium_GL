package com.litecart.homework4;

import com.litecart.admin.homework4.CartPage;
import com.litecart.admin.homework4.HomePage;
import com.litecart.admin.homework4.ProductPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CartTest {
    private static WebDriver wd;
    private static HomePage homePage;
    private static ProductPage productPage;
    private static CartPage cartPage;

    @BeforeClass
    public static void beforeClass() {
        wd = new ChromeDriver();
        wd.manage().window().setSize(new Dimension(1920, 1080));
        wd.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        homePage = new HomePage(wd);
        productPage = new ProductPage(wd);
        cartPage = new CartPage(wd);
    }

    @AfterClass
    public static void afterClass() {
        wd.quit();
    }

    @Test
    public void addItemsToCartTest() {
        for (int i = 0; i < 3; i++) {
            homePage.open();
            homePage.openPageWithRandomPopularProduct();
            productPage.addProductToCart();
        }
        cartPage.open();
        assertNotEquals(0, cartPage.getListOfAddedProduct().size());
    }

    @Test
    public void removeItemsFromCartTest() {
        cartPage.open();
        cartPage.removeAllProductsFromCart();
        assertTrue(cartPage.isCartEmpty());
    }
}