package lesson9;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class MtsTests {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 3);
        driver.get("https://www.mts.by");

        // Принимаем куки, если окно появилось
        try {
            WebElement cookieBtn = driver.findElement(By.id("cookie-agree"));
            if (cookieBtn.isDisplayed()) cookieBtn.click();
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testMtsTopUpBlock() {
        // 1. Проверить название блока
        WebElement blockTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section[@class='pay']//h2")));
        String actualTitle = blockTitle.getText().replace("\n", " ").trim();
        Assert.assertTrue(actualTitle.contains("Онлайн пополнение без комиссии"), "Заголовок не совпал!");

        // 2. Проверить наличие логотипов платёжных систем
        // Используем более точный путь к картинкам в блоке оплаты
        List<WebElement> logos = driver.findElements(By.xpath("//div[@class='pay__partners']//img | //ul[@class='pay__partners']//img"));
        Assert.assertFalse(logos.isEmpty(), "Логотипы платежных систем не найдены");

        // 3. Проверить работу ссылки «Подробнее о сервисе»
        WebElement detailsLink = driver.findElement(By.linkText("Подробнее о сервисе"));

        // Кликаем через JavaScript, чтобы избежать перекрытия
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", detailsLink);

        wait.until(ExpectedConditions.urlContains("help/poryadok-oplaty-i-bezopasnost"));
        driver.navigate().back();

        // 4. Заполнить поля и проверить работу кнопки
        WebElement phoneField = wait.until(ExpectedConditions.elementToBeClickable(By.id("connection-phone")));
        WebElement sumField = driver.findElement(By.id("connection-sum"));

        phoneField.sendKeys("297777777");
        sumField.sendKeys("100");

        WebElement continueBtn = driver.findElement(By.xpath("//form[@id='pay-connection']//button[contains(text(),'Продолжить')]"));
        wait.until(ExpectedConditions.elementToBeClickable(continueBtn));

        // Снова кликаем через JavaScript для надежности
        js.executeScript("arguments[0].click();", continueBtn);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
