package steps;

import api.UserApi;
import api.AccountApi;
import api.ApiClient;
import base.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.response.Response;
import pages.LoginPage;
import pages.RegisterPage;
import pages.TransferPage;
import utils.ExcelUtil;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class HybridSteps {

    WebDriver driver;
    String username, password, accountId;
    private Properties config;

    public static volatile String latestUsername;
    public static volatile String latestPassword;
    public static volatile String latestAccountId;

    @Before
    public void setup() throws Exception {
        config = new Properties();
        config.load(new FileInputStream("src/test/resources/config.properties"));

        BaseTest.initializeDriver(
                config.getProperty("browser"),
                config.getProperty("grid.url")
        );
        driver = BaseTest.getDriver();
    }

    @After
    public void tearDown() {
        BaseTest.quitDriver();
    }

    @Given("I create user {string}")
    public void createUserUI(String row) throws InterruptedException {

        int r = Integer.parseInt(row);
        Map<String, String> userData = ExcelUtil.getRowData(r);

        driver.get("https://parabank.parasoft.com/parabank/register.htm");

        this.username = userData.get("username") + System.currentTimeMillis();
        this.password = userData.get("password");

        new RegisterPage(driver).registerNewUser(userData, this.username);

        // Save ONLY latest valid user
        latestUsername = this.username;
        latestPassword = this.password;

        System.out.println("Registration successful: " + this.username);
    }

    @When("I login and transfer {string}")
    public void loginAndTransfer(String amount) throws InterruptedException {

        TransferPage transferPage = new TransferPage(driver);

        String rawId = transferPage.transfer(amount);

        Pattern pattern = Pattern.compile("account #(\\d+)");
        Matcher matcher = pattern.matcher(rawId);

        if (matcher.find()) {
            this.accountId = matcher.group(1);

            // SET AFTER extraction (IMPORTANT)
            latestAccountId = this.accountId;
        } else {
            throw new RuntimeException("Account ID not found in UI response!");
        }

        System.out.println("Account ID captured: " + this.accountId);
    }

    @Then("I verify balance via API")
    public void verifyBalance() {

        Response response =
                given()
                        .auth().preemptive().basic(username, password)
                        .pathParam("accId", accountId)
                        .when()
                        .get("https://parabank.parasoft.com/parabank/services/bank/accounts/{accId}")
                        .then()
                        .statusCode(200)
                        .extract().response();

        System.out.println(response.asPrettyString());
    }
}