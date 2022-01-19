package com.bitpanda.pageobject;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class HomePage {
    protected WebDriver driver;
    private FluentWait wait;


    @FindBy(xpath = "//ul[@id=\"homefeatured\"]//li")
    public List<WebElement> featuredProducts;
    @FindBy(xpath = "//span[@id=\"layer_cart_product_title\"]")
    public WebElement cartOverlayProductTitle;
    @FindBy(xpath = "//span[@id=\"layer_cart_product_quantity\"]")
    public WebElement cartOverlayProductQuantity;
    @FindBy(xpath = "//span[@id=\"layer_cart_product_price\"]")
    public WebElement cartOverlayProductPrice;
    @FindBy(xpath = "//span[@title=\"Close window\"]")
    public WebElement cartOverlayCloseButton;

    private By cartOverlayLocator = By.xpath("//div[@id=\"layer_cart\"]");
    public WebElement cartOverlay;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        wait = new FluentWait(driver);
        wait.withTimeout(Duration.ofMillis(15000));
        wait.pollingEvery(Duration.ofMillis(250));
        wait.ignoring(NoSuchElementException.class);
        PageFactory.initElements(driver, this);
    }


    public HomePage goTo() {
        driver.get("http://automationpractice.com/index.php");
        driver.manage().window().maximize();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("search_query_top"))));
        return this;
    }

    public SearchPage searchForProduct(String searchQuery) {
        driver.findElement(By.id("search_query_top")).click();
        driver.findElement(By.id("search_query_top")).sendKeys(searchQuery);
        driver.findElement(By.name("submit_search")).click();
        return new SearchPage(driver);
    }

    public void addToCartFeaturedProduct(Integer position) {
        WebElement we = featuredProducts.get(position);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", we);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"product-container\"]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//div[@class=\"product-container\"]")));
        new Actions(driver).moveToElement(we).build().perform();
        WebElement button = we.findElements(By.xpath("//a[@title=\"Add to cart\"]")).get(position);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", button);
        button.click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(cartOverlayLocator)));
        cartOverlay = driver.findElement(cartOverlayLocator);
    }

    public String getFeatureProductName(Integer position){
        return driver.findElements(By.xpath("//a[@class=\"product-name\"]")).get(position).getText();
    }

    public String getFeatureProductPrice(Integer position){
        return driver.findElements(By.xpath("//div[@class=\"right-block\"]//span[@class=\"price product-price\"]")).get(position).getText();
    }

    public String getCartProductNumber(){
        return driver.findElement(By.xpath("//span[@class=\"ajax_cart_quantity\"]")).getAttribute("innerHTML");
    }
}