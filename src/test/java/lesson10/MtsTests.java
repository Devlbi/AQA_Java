package lesson10;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Collections;

public class MtsTests {
    private WebDriver driver;
    private MainPage mainPage;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.mts.by");

        mainPage = new MainPage(driver);
        // Закрываем куки
        try {
            driver.findElement(org.openqa.selenium.By.id("cookie-agree")).click();
        } catch (Exception ignore) {}
    }

    @Test
    public void testPlaceholders() {
        MainPage mainPage = new MainPage(driver);
        Assert.assertEquals(mainPage.getPlaceholderByServiceName("Услуги связи"),
                "Номер телефона", "Плейсхолдер для связи не совпадает");
        Assert.assertEquals(mainPage.getPlaceholderByServiceName("Домашний интернет"),
                "Номер абонента", "Плейсхолдер для интернета не совпадает");
        Assert.assertEquals(mainPage.getPlaceholderByServiceName("Рассрочка"),
                "Номер счета на 44", "Плейсхолдер для рассрочки не совпадает");
        Assert.assertEquals(mainPage.getPlaceholderByServiceName("Задолженность"),
                "Номер счета на 2073", "Плейсхолдер для задолженности не совпадает");
    }

    @Test
    public void testFullPaymentCycle() throws InterruptedException {
        // Вводим 100
        mainPage.fillPaymentConnection("297777777", "100");
        // Пробуем искать просто "100" (без копеек), так как XPath contains(text(), '100')
        // найдет и "100", и "100.00"
        mainPage.verifyIframeDetails("100", "297777777");
        mainPage.verifyPaymentIcons();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}