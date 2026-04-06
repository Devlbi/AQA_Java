package lesson11;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Attachment(value = "Скриншот", type = "image/png")
    public byte[] saveScreenshot(WebElement element) {
        if (element != null) {
            // Прокручиваем к элементу, чтобы он был в центре экрана
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        }
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Step("Закрытие баннера Cookies")
    public void acceptCookies() {
        try {
            WebElement cookieBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("cookie-agree")));
            cookieBtn.click();
        } catch (Exception ignore) {
            // Если баннера нет или он не кликабелен, пропускаем
        }
    }

    private void clickJS(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    @Step("Выбор услуги: {serviceName}")
    public String getPlaceholderByServiceName(String serviceName) {
        try {
            WebElement header = wait.until(ExpectedConditions.elementToBeClickable(By.className("select__header")));
            saveScreenshot(header); // Скриншот ДО клика по списку
            clickJS(header);

            String xpath = "//ul[@class='select__list']//p[contains(normalize-space(text()), '" + serviceName + "')]";
            WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            saveScreenshot(option); // Скриншот выбранного пункта
            clickJS(option);

            Thread.sleep(1500);
        } catch (Exception e) {
            System.err.println("Ошибка при выборе услуги '" + serviceName + "': " + e.getMessage());
        }
        return getActiveInputPlaceholder();
    }

    @Step("Поиск активного плейсхолдера")
    private String getActiveInputPlaceholder() {
        List<WebElement> inputs = driver.findElements(By.cssSelector("input[placeholder]"));
        for (WebElement input : inputs) {
            try {
                if (input.isDisplayed() && !input.getAttribute("placeholder").isEmpty()) {
                    saveScreenshot(input); // Скриншот поля с плейсхолдером
                    return input.getAttribute("placeholder").trim();
                }
            } catch (Exception ignored) {}
        }
        saveScreenshot(null); // Если не нашли, просто скриншот экрана
        return "NOT_FOUND";
    }

    @Step("Заполнение данных платежа: телефон {phone}, сумма {sum}")
    public void fillPaymentConnection(String phone, String sum) {
        WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("connection-phone")));
        saveScreenshot(phoneInput);

        phoneInput.clear();
        phoneInput.sendKeys(phone);

        WebElement sumInput = driver.findElement(By.id("connection-sum"));
        sumInput.clear();
        sumInput.sendKeys(sum);

        saveScreenshot(sumInput); // Скриншот после заполнения данных

        WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//form[@id='pay-connection']//button[contains(., 'Продолжить')]")));
        clickJS(submitBtn);
    }

    @Step("Проверка данных во фрейме оплаты")
    public void verifyIframeDetails(String expectedSum, String expectedPhone) throws InterruptedException {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));

        boolean found = false;
        String digitsOnly = expectedPhone.replaceAll("\\D", "");
        String shortPhone = digitsOnly.substring(digitsOnly.length() - 7);

        for (WebElement iframe : iframes) {
            driver.switchTo().frame(iframe);
            try {
                Thread.sleep(2000);
                WebElement body = driver.findElement(By.tagName("body"));
                String pageSource = body.getText();

                if (pageSource.contains(expectedSum) && pageSource.contains(shortPhone)) {
                    saveScreenshot(body); // Скриншот ВНУТРИ фрейма
                    found = true;
                    driver.switchTo().defaultContent();
                    break;
                }
            } catch (Exception e) {}
            driver.switchTo().defaultContent();
        }
        Assert.assertTrue(found, "Данные оплаты не найдены во фрейме");
    }

    @Step("Проверка иконок платежных систем")
    public void verifyPaymentIcons() {
        // 1. Ждем появления любого фрейма
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        boolean iconsFound = false;

        for (WebElement iframe : iframes) {
            driver.switchTo().frame(iframe);
            try {
                // Ищем иконки (visa, mastercard и т.д.)
                List<WebElement> icons = driver.findElements(By.xpath(
                        "//img[contains(@src, 'visa') or contains(@src, 'mastercard')]"
                ));

                if (!icons.isEmpty()) {
                    // Если иконки найдены, делаем скриншот первой из них для отчета
                    saveScreenshot(icons.get(0));
                    iconsFound = true;
                    driver.switchTo().defaultContent();
                    break;
                }
            } catch (Exception e) {}
            driver.switchTo().defaultContent();
        }
        Assert.assertTrue(iconsFound, "Иконки платежных систем не найдены!");
    }
}