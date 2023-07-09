package page.objects;

import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class Searcher {

    SelenideElement searchInput = $(By.cssSelector(".search__container input[name='search']"));
    SelenideElement fruityJelly = $(By.cssSelector("ul.search__list a[href='/Slodycze/Zelki-owocowe']"));
    SelenideElement lastSearchedPhrase = $(By.cssSelector("span.search__history-item-value"));
    private static Logger logger = LogManager.getLogger(Searcher.class);

    protected WebDriver driver;

    public Searcher(WebDriver driver) {
        this.driver = driver;
    }

    public void loadCookies(WebDriver driver) {
        Cookie welcomePopup = new Cookie("popup_v2_21301", "true");
        driver.manage().addCookie(welcomePopup);
        driver.navigate().refresh();
    }

    public void searchFruitJellyFromDropDown() {
        open("https://slodyczowo.pl/");
        org.openqa.selenium.WebDriver driver = getWebDriver();
        driver.manage().window().maximize();
        $(By.className("js__accept-all-consents")).click();
        loadCookies(driver);
        searchInput.setValue("żelki");
        fruityJelly.shouldBe(visible).click();
    }

    public void findResultsForPhrase(String lookForPhrase) {
        open("https://slodyczowo.pl/");
        org.openqa.selenium.WebDriver driver = getWebDriver();
        driver.manage().window().maximize();
        $(By.className("js__accept-all-consents")).click();
        loadCookies(driver);
        searchInput.setValue(lookForPhrase);
        searchInput.pressEnter();
    }

    public boolean checkIfHistoryIsDisplayed() {
        actions().moveToElement(searchInput).click().build().perform();
        if (!lastSearchedPhrase.is(visible)) {
            logger.error("Confirmation OK button for Information Modal is not displayed");
        }
        return lastSearchedPhrase.is(visible);
    }
}