package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    By usernameField = By.name("username");
    By passwordField = By.name("password");
    By loginBtn = By.xpath("//input[@value='Log In']");

    public void login(String user, String pass) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        userField.clear();
        userField.sendKeys(user);

        WebElement passField = driver.findElement(By.name("password"));
        passField.clear();
        passField.sendKeys(pass);

        // ParaBank home page login button
        driver.findElement(By.xpath("//input[@value='Log In']")).click();
    }
}