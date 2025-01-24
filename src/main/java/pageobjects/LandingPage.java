package pageobjects;

import abstractcomponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class LandingPage extends AbstractComponents {

    WebDriver driver;

    public LandingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "h1[class*='font-display']")
    WebElement textOnMovie;

    @FindBy(css = "section[class*='bg-darkblue'] div[class*='flex'] a")
    List<WebElement> footerLinks;

    @FindBy(id = "didomi-notice-agree-button")
    WebElement agreeButton;


    public void goTo() {
        driver.get("https://www.futuroscope.com/en");
    }

    public String getTextOnMovie() {
        return textOnMovie.getText();
    }

    public List<WebElement> getListOfFooterLinks() {
        return footerLinks;
    }

    public void verifyFooterLinksRespCode() throws IOException {
        for (WebElement link : footerLinks) {
            String url = link.getAttribute("href");
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("HEAD");
            conn.connect();
            int respCode = conn.getResponseCode();
            System.out.println(respCode);
            }
        }

    public BookingData acceptCookiesOnWindow() {
        waitForElementToBeClickable(agreeButton);
        agreeButton.click();
        return new BookingData(driver);
    }
    }








