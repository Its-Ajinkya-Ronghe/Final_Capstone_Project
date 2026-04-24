package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Map;

public class RegisterPage {
    WebDriver driver;
    WebDriverWait wait;

    // Locators based on ParaBank IDs
    @FindBy(id = "customer.firstName") private WebElement firstNameField;
    @FindBy(id = "customer.lastName") private WebElement lastNameField;
    @FindBy(id = "customer.address.street") private WebElement streetField;
    @FindBy(id = "customer.address.city") private WebElement cityField;
    @FindBy(id = "customer.address.state") private WebElement stateField;
    @FindBy(id = "customer.address.zipCode") private WebElement zipCodeField;
    @FindBy(id = "customer.phoneNumber") private WebElement phoneField;
    @FindBy(id = "customer.ssn") private WebElement ssnField;
    @FindBy(id = "customer.username") private WebElement usernameField;
    @FindBy(id = "customer.password") private WebElement passwordField;
    @FindBy(id = "repeatedPassword") private WebElement confirmPasswordField;
    @FindBy(xpath = "//input[@value='Register']") private WebElement registerButton;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void registerNewUser(Map<String, String> data, String uniqueUsername) {
        // Wait for the first element to ensure page is loaded
        wait.until(ExpectedConditions.visibilityOf(firstNameField));

        firstNameField.sendKeys(data.get("firstName"));
        lastNameField.sendKeys(data.get("lastName"));

        // These use defaults since they weren't in your Excel snippet
        streetField.sendKeys("YCCE Road");
        cityField.sendKeys("Nagpur");
        stateField.sendKeys("Maharashtra");
        zipCodeField.sendKeys("441110");
        phoneField.sendKeys("9876543210");
        ssnField.sendKeys("123-45-6789");

        usernameField.sendKeys(uniqueUsername);
        passwordField.sendKeys(data.get("password"));
        confirmPasswordField.sendKeys(data.get("password"));

        registerButton.click();
    }
    // Add this method to your RegisterPage class
    public void logout() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.linkText("Log Out"))).click();
    }
}