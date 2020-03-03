package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverSetUp {
    private static WebDriver driver;
    public DriverSetUp(){
    }
    public static WebDriver getDriver(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options=new ChromeOptions();
        options.setHeadless(true);
        driver=new ChromeDriver(options);
        return driver;
    }

}
