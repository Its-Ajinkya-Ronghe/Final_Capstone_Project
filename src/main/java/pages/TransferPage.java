package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;

public class TransferPage {
    WebDriver driver;

    public TransferPage(WebDriver driver) {
        this.driver = driver;
    }

    public String transfer(String amt) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // 1. Click the 'Transfer Funds' link in the left sidebar
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Transfer Funds")));
        link.click();

        // 2. Wait for the Transfer page to actually load its form
        WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("amount")));
        amountField.clear();
        amountField.sendKeys(amt);

        // 3. Click the Transfer button
        Thread.sleep(5000);
        WebElement transferBtn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@value='Transfer']")
                )
        );

        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", transferBtn);

        // 4. Capture result
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("showResult")));
        return result.getText();
    }
}