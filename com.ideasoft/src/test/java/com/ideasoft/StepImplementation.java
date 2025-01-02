package com.ideasoft;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.thoughtworks.gauge.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.ideasoft.BaseTest.driver;

public class StepImplementation {
    private static final Logger logger = LogManager.getLogger(StepImplementation.class);
    private static final int DEFAULT_WAIT_TIME = 10;

    // Dinamik bekleme - Elementin tıklanabilir olmasını bekler
    public void waitForElementToBeClickable(By by) {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME))
                .until(ExpectedConditions.elementToBeClickable(by));
    }
    public void scrollToElement(By by) {
        WebElement element = driver.findElement(by);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }


    // Dinamik bekleme - Elementin görünür olmasını bekler
    public void waitForElementToBeVisible(By by) {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME))
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    @Step("<xpath> Ödeme sayfasında gerekli alanlar doldurulmadığında hata kodu al")
    public void verifyElementPresencePassTest(String xpath) {
        try {
            // Elementin sayfada görünür olup olmadığını kontrol ediyoruz
            WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

            if (element.isDisplayed()) {
                logger.info("Element bulundu, test başarıyla geçti.");
            } else {
                logger.error("Element bulundu ancak görünür değil.");
                Assert.fail("Element bulundu ancak görünür değil.");
            }
        } catch (Exception e) {
            logger.error("Element bulunamadı: " + xpath);
            Assert.fail("Test başarısız oldu, element bulunamadı: " + xpath);
        }
    }

    @Step("<second> saniye bekle")
    public void waitForsecond(int second) throws InterruptedException {
        Thread.sleep(1000 * second);
        logger.info(second + "kadar statik bekleme");
    }

    @Step("<Key> İd'li elemente tıkla")
    public void clickElementByid(String Key) {
        By by = By.id(Key);
        waitForElementToBeClickable(by);
        driver.findElement(by).click();
        logger.info("Elemente tıklandı: " + Key);
    }
    @Step("Enter tusuna bas")
    public void pressEnter(){
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);
    }

    @Step("<Key> İd'li elemente <keywordc> değerini yaz")
    public void SendkeyElementByid(String Key, String keywordc) {
        By by = By.id(Key);
        waitForElementToBeVisible(by);
        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(keywordc);
        logger.info("Elemente değer yazıldı: " + keywordc + " - Element ID: " + Key);
    }

    @Step("<Key> xpath'li elemente tıkla")
    public void clickElementByxpath(String Key) {
        By by = By.xpath(Key);
        waitForElementToBeClickable(by);
        driver.findElement(by).click();
        logger.info("Elemente tıklandı: " + Key);
    }

    @Step("<Key> xpath'li elemente <keywordc> değerini yaz")
    public void SendkeyElementByxpath(String Key, String keywordc) {
        By by = By.xpath(Key);
        waitForElementToBeVisible(by);
        driver.findElement(by).sendKeys(keywordc);
        logger.info("Elemente değer yazıldı: " + keywordc + " - Element Xpath: " + Key);
    }

    @Step("Elementi <xpath> bul ve <keyword> değerini kontrol et")
    public void textControl(String xpath, String keyword) {
        By by = By.xpath(xpath);
        waitForElementToBeVisible(by);
        String text = driver.findElement(by).getText();
        Assert.assertTrue("Text değeri bulunamadı ", text.equals(keyword));
        logger.info("Text kontrol edildi. Beklenen: " + keyword + ", Bulunan: " + text);
    }
    @Step("Elementi <xpath> bul ve <value> degerini kontrol et")
    public void valueControl(String xpath, int expectedValue) {
        try {
            // Elementi XPath ile bul
            By by = By.xpath(xpath);
            waitForElementToBeVisible(by);

            // Elementin "value" atributunu al
            WebElement element = driver.findElement(by);
            String valueText = element.getAttribute("value");

            // "value" atributunu sayısal değere dönüştür
            int actualValue = Integer.parseInt(valueText.trim());

            // Beklenen değerle karşılaştır
            Assert.assertEquals("Değerler eşleşmiyor!", expectedValue, actualValue);
            logger.info("Değer kontrol edildi. Beklenen: " + expectedValue + ", Bulunan: " + actualValue);
        } catch (NoSuchElementException e) {
            logger.error("Element bulunamadı: " + xpath, e);
            Assert.fail("Element bulunamadı: " + xpath);
        } catch (NumberFormatException e) {
            logger.error("Değer sayısal bir formata dönüştürülemedi: " + xpath, e);
            Assert.fail("Değer sayısal bir formata dönüştürülemedi: " + xpath);
        } catch (Exception e) {
            logger.error("Değer kontrolü sırasında bir hata oluştu.", e);
            Assert.fail("Değer kontrolü sırasında bir hata oluştu: " + e.getMessage());
        }
    }

    public List<WebElement> listElements(String xpath) {
        By by = By.xpath(xpath);
        waitForElementToBeVisible(by);
        return driver.findElements(by);
    }

    @Step("Rastgele uçuş seç <xpath> ifadesine göre ve <wait> saniye bekle")
    public void randomClick(String xpath, int wait) throws InterruptedException {
        List<WebElement> elements = listElements(xpath);
        if (!elements.isEmpty()) {
            elements.get(new Random().nextInt(elements.size())).click();
            logger.info("Rastgele elemente tıklandı.");
        } else {
            logger.warn("Hiçbir element bulunamadı. XPath: " + xpath);
        }
        waitForsecond(wait);
    }

    @Step("<Key> xpath'li elemente kalkış bilgisi icin <keywordc> değerini yaz")
    public void FirstFlight(String Key, String keywordc) {
        By by = By.xpath(Key);
        waitForElementToBeVisible(by);
        driver.findElement(by).sendKeys(keywordc);
        logger.info("Elemente değer yazıldı: " + keywordc + " - Element Xpath: " + Key);
    }

    @Step("<Key> xpath'li elemente inis bilgisi icin <keywordc> değerini yaz")
    public void SecondFlight(String Key, String keywordc) {
        By by = By.xpath(Key);
        waitForElementToBeVisible(by);
        driver.findElement(by).sendKeys(keywordc);
        logger.info("Elemente değer yazıldı: " + keywordc + " - Element Xpath: " + Key);
    }

    // Slider'ı belirlenen aralıkta ayarlamak için bir metot
    @Step("Sol Slider'ı ayarla ve başlangıç <startValue> ve bitiş <endValue> olarak")
    // Slider'ı hareket ettirme
    public void moveSliderLeft(int startValue, int endValue) {
        WebElement sliderLeft = driver.findElement(By.xpath("//div[@data-testid='departureDepartureTimeSlider']//div[@class='rc-slider-handle rc-slider-handle-1']"));
        Actions move = new Actions(driver);

        int currentAriaValue = Integer.parseInt(sliderLeft.getAttribute("aria-valuenow"));
        logger.info(currentAriaValue);

        move.clickAndHold(sliderLeft).moveByOffset(endValue, 0).release().perform();
        logger.info("çalışıyor mu");


        try {
            Thread.sleep(2000); // 2 saniye bekleme
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Step("Sağ Slider'ı ayarla ve başlangıç <startValue> ve bitiş <endValue> olarak")
    // Slider'ı hareket ettirme
    public void moveSliderRight(int startValue, int endValue) {
        WebElement sliderRight = driver.findElement(By.xpath("//div[@data-testid='departureDepartureTimeSlider']//div[@class='rc-slider-handle rc-slider-handle-2']"));
        Actions move = new Actions(driver);

        int currentAriaValue = Integer.parseInt(sliderRight.getAttribute("aria-valuenow"));
        logger.info(currentAriaValue);

        move.clickAndHold(sliderRight).moveByOffset(endValue, 0).release().perform();
        logger.info("çalışıyor mu");


        try {
            Thread.sleep(2000); // 2 saniye bekleme
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Step("Listelenen uçuşlarda AnadoluJet uçuşlarının fiyatlarının artan şekilde sıralandığını kontrol et")
    public void verifyAnadoluJetFlightsPriceOrder() {
        // AnadoluJet uçuşlarını temsil eden elementlerin XPath'ini belirleyin
        String anadoluJetFlightXpath = "//div[@data-testid='flightInfoPrice']";

        // Fiyat attribute'u
        String priceAttribute = "data-price";

        // AnadoluJet uçuşlarını bul
        List<WebElement> anadoluJetFlights = driver.findElements(By.xpath(anadoluJetFlightXpath));

        // Uçuşları bir ArrayList'e ekleyin
        ArrayList<WebElement> anadoluJetFlightList = new ArrayList<>(anadoluJetFlights);

        if (anadoluJetFlightList.isEmpty()) {
            logger.info("Hic AnadoluJet ucusu bulunamadi.");
            return; // Eğer AnadoluJet uçuşu yoksa kontrol burada biter.
        }

        // Fiyatları bir ArrayList'e ekle
        ArrayList<Double> anadoluJetFlightPrices = new ArrayList<>();

        for (WebElement anadoluJetFlight : anadoluJetFlightList) {
            // Fiyat attribute'undan değeri al
            String priceText = anadoluJetFlight.getAttribute(priceAttribute);

            if (priceText == null) {
                logger.warn("Fiyat bilgisi bulunamadi, ucuş elementini atla: " + anadoluJetFlight.toString());
                continue; // Eğer fiyat bilgisi yoksa, bu uçuşu atla
            }

            // Fiyatların double a cevrilmesi
            try {
                double price = Double.parseDouble(priceText);
                anadoluJetFlightPrices.add(price);
            } catch (NumberFormatException e) {
                logger.error("Fiyat değeri hatalı: " + priceText, e);
            }
        }

        // Fiyatların sıralanması
        ArrayList<Double> sortedPrices = new ArrayList<>(anadoluJetFlightPrices);
        Collections.sort(sortedPrices);

        // Orijinal fiyatların sıralı olup olmadığınının kontrolu
        if (anadoluJetFlightPrices.equals(sortedPrices)) {
            logger.info("AnadoluJet uçuşlarının fiyatları artan şekilde sıralı.");
        } else {
            logger.error("AnadoluJet uçuşlarının fiyatları artan şekilde sıralı değil!");
            Assert.fail("AnadoluJet uçuşlarının fiyatları artan şekilde sıralı değil!");
        }
    }
    @Step("Sepete ekle mesajının text'i kontrol edilir.")
    public void clickAddToCartAndCheckMessage() {
        // "SEPETİNİZE EKLENMİŞTİR" mesajını bekle
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'SEPETİNİZE EKLENMİŞTİR')]")));

        // Mesajın text'ini al ve kontrol et
        String messageText = confirmationMessage.getText();
        logger.info("Bulunan mesaj: " + messageText);

        // Beklenen mesajla karşılaştır
        if (!messageText.equals("SEPETİNİZE EKLENMİŞTİR")) {
            logger.error("Mesaj beklenen gibi değil! Bulunan: " + messageText);
        }

        }
    }








