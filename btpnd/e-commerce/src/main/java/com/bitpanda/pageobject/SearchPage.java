package com.bitpanda.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.NoSuchElementException;

public class SearchPage {
    private WebDriver driver;
    private FluentWait wait;

    @FindBy(xpath = "//div[@class=\"product-container\"]")
    public WebElement productContainer;

    public By searchResultNumberLocator = By.xpath("//h1[@class=\"page-heading  product-listing\"]//span[@class=\"heading-counter\"]");
    @FindBy(xpath = "//h1[@class=\"page-heading  product-listing\"]//span[@class=\"heading-counter\"]")
    public WebElement searchResultNumber;

    @FindBy(css = ".ajax_block_product:nth-child(1) .right-block .price")
    public WebElement productPrice;

    @FindBy(css = ".ajax_block_product:nth-child(1) .product-name")
    public WebElement productDescription;

    @FindBy(css = ".ajax_block_product:nth-child(1) .available-now")
    public WebElement availability;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        wait = new FluentWait(driver);
        wait.withTimeout(Duration.ofMillis(15000));
        wait.pollingEvery(Duration.ofMillis(250));
        wait.ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchResultNumberLocator));
        PageFactory.initElements(driver,this);
    }

    public String getProductPrice() {
        return productPrice.getText();
    }
}
