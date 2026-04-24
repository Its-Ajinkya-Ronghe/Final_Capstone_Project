package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.Duration;

public class BaseTest {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initializeDriver(String browser, String gridUrl) throws Exception {

        WebDriver remoteDriver;

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                remoteDriver = new RemoteWebDriver(new URL(gridUrl), chromeOptions);
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                remoteDriver = new RemoteWebDriver(new URL(gridUrl), firefoxOptions);
                break;

            default:
                throw new RuntimeException("Invalid browser: " + browser);
        }

        remoteDriver.manage().window().maximize();
        remoteDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.set(remoteDriver);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}