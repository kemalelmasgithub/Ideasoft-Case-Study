package com.ideasoft;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    public static WebDriver driver;

    @BeforeScenario
    public void setUp() {
        String baseUrl = "https://qatestcase.myideasoft.com";
        ChromeOptions options = new ChromeOptions();

        // ChromeDriver'ı güncel sürümüyle ayarlamak için
        WebDriverManager.chromedriver().driverVersion("131.0.6778.205").setup();
        driver = new ChromeDriver(options);

        // Tarayıcı ayarları
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(baseUrl);


    }

    @AfterScenario
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Tarayıcıyı kapat
        }
    }
}

