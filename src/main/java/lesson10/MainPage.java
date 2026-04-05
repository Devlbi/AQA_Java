package lesson10;

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

    private void clickJS(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public String getPlaceholderByServiceName(String serviceName) {
        try {
            // 1. Клик по селектору выбора услуги
            WebElement header = wait.until(ExpectedConditions.elementToBeClickable(By.className("select__header")));
            clickJS(header);

            // 2. Поиск пункта меню с очисткой пробелов (normalize-space)
            String xpath = "//ul[@class='select__list']//p[contains(normalize-space(text()), '" + serviceName + "')]";
            WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            clickJS(option);

            // 3. Пауза, чтобы JS на сайте успел подменить форму
            Thread.sleep(1500);
        } catch (Exception e) {
            System.err.println("Ошибка при выборе услуги '" + serviceName + "': " + e.getMessage());
        }
        return getActiveInputPlaceholder();
    }

    private String getActiveInputPlaceholder() {
        // Сначала ищем через стандартный Selenium среди всех инпутов с плейсхолдером
        List<WebElement> inputs = driver.findElements(By.cssSelector("input[placeholder]"));

        for (WebElement input : inputs) {
            try {
                if (input.isDisplayed() && !input.getAttribute("placeholder").isEmpty()) {
                    return input.getAttribute("placeholder").trim();
                }
            } catch (Exception ignored) {
            }
        }
        // Если Selenium не видит, достаем через JavaScript
        try {
            return (String) ((JavascriptExecutor) driver).executeScript(
                    "var inputs = document.querySelectorAll('input');" +
                            "for (var i = 0; i < inputs.length; i++) {" +
                            "  var style = window.getComputedStyle(inputs[i]);" +
                            "  if (style.display !== 'none' && style.visibility !== 'hidden' && inputs[i].placeholder) {" +
                            "    return inputs[i].placeholder;" +
                            "  }" +
                            "}" +
                            "return 'NOT_FOUND';"
            );
        } catch (Exception e) {
            return "NOT_FOUND";
        }
    }

    public void fillPaymentConnection(String phone, String sum) {
        WebElement phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("connection-phone")));
        phoneInput.clear();
        phoneInput.sendKeys(phone);

        WebElement sumInput = driver.findElement(By.id("connection-sum"));
        sumInput.clear();
        sumInput.sendKeys(sum);

        try {
            driver.findElement(By.className("pay__wrapper")).click();
            Thread.sleep(1000);
        } catch (Exception ignore) {
        }

        WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//form[@id='pay-connection']//button[contains(., 'Продолжить')]")));
        clickJS(submitBtn);
    }

    public void verifyIframeDetails(String expectedSum, String expectedPhone) throws InterruptedException {
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // 1. Ждем фреймы
        longWait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));

        boolean found = false;
        String shortPhone = expectedPhone.substring(expectedPhone.length() - 7);

        for (WebElement iframe : iframes) {
            driver.switchTo().frame(iframe);

            try {
                // Даем фрейму полсекунды на прогрузку внутреннего JS
                Thread.sleep(1000);

                // Ищем любой элемент, содержащий сумму (100 или 100.00)
                List<WebElement> sumElements = driver.findElements(By.xpath(
                        "//*[contains(text(), '" + expectedSum + "')]"));

                // Ищем любой элемент, содержащий последние 7 цифр номера
                List<WebElement> phoneElements = driver.findElements(By.xpath(
                        "//*[contains(text(), '" + shortPhone + "')]"));

                if (!sumElements.isEmpty() && !phoneElements.isEmpty()) {
                    System.out.println("Успех: Сумма и номер найдены во фрейме!");
                    found = true;
                    driver.switchTo().defaultContent();
                    break;
                }
            } catch (Exception e) {
                // Если во фрейме ошибка или он пустой — просто идем дальше
            }

            driver.switchTo().defaultContent();
        }

        if (!found) {
            Assert.fail("Данные оплаты не найдены. Ожидали сумму: " + expectedSum + " и номер: " + shortPhone);
        }
    }

    public void verifyPaymentIcons() {
        // 1. Ждем появления фреймов (до 15 секунд)
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        longWait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));

        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        boolean iconsFound = false;

        for (WebElement iframe : iframes) {
            // Переключаемся во фрейм
            driver.switchTo().frame(iframe);

            // Ищем иконки платежных систем по атрибуту src (самый надежный способ)
            List<WebElement> icons = driver.findElements(By.xpath(
                    "//img[contains(@src, 'visa') or contains(@src, 'mastercard') or contains(@src, 'belkart') or contains(@src, 'maestro')]"
            ));

            if (!icons.isEmpty()) {
                System.out.println("Найдено иконок платежных систем: " + icons.size());
                iconsFound = true;
                driver.switchTo().defaultContent(); // Выходим из фрейма
                break;
            }
            // Если в этом фрейме иконок нет, возвращаемся в основной документ и проверяем следующий фрейм
            driver.switchTo().defaultContent();
        }

        Assert.assertTrue(iconsFound, "Иконки платежных систем не найдены ни в одном из фреймов!");
    }
}