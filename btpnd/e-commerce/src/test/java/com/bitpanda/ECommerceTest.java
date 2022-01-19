package com.bitpanda;

import com.bitpanda.pageobject.HomePage;
import com.bitpanda.pageobject.SearchPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ECommerceTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;


    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "driver/geckodriver");
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        //Declare and initialise a fluent wait

    }

    @After
    public void tearDown() {
        driver.quit();
    }

    /**
     * This test opens e-commerce home page and searches for "short" product and then
     * validates product price, name and availability
     *
     * @testcase
     *
     * @precondition product database is loaded
     *
     * @postcondition
     * @passCriteria the search page gives the correct data for keyword "short"
     *
     */
    @Test
    public void searchForShorts() {
        //given
        HomePage homePage = new HomePage(driver).goTo();
        // when
        SearchPage searchPage = homePage.searchForProduct("short");
        //then
        assertThat(searchPage.productPrice.getText(), is("$16.51"));
        assertThat(searchPage.productDescription.getText(), is("Faded Short Sleeve T-shirts"));
        assertThat(searchPage.availability.getText(), is("In stock"));
    }

    /**
     * This test opens the e-commerce home page and adds the second product from the left
     * in the first row of recommended products to the shopping cart and
     * then validates whether the product is in the cart
     *
     * @testcase
     *
     * @precondition product database is loaded
     *
     * @postcondition
     * @passCriteria product is added to cart
     *
     */
    @Test
    public void addProductToCart() {
        //given
        HomePage homePage = new HomePage(driver).goTo();
        Integer productPosition = 1;
        // when
        String productName = homePage.getFeatureProductName(productPosition);
        String productPrice = homePage.getFeatureProductPrice(productPosition);
        homePage.addToCartFeaturedProduct(productPosition);
        // then
        assertThat(homePage.cartOverlay.isDisplayed(), is(true));
        assertThat(homePage.cartOverlayProductTitle.getText(), is(productName));
        assertThat(homePage.cartOverlayProductPrice.getText(), is(productPrice));
        homePage.cartOverlayCloseButton.click();
        assertThat(homePage.getCartProductNumber(), is("1"));
    }
}
