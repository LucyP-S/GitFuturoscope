package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageobjects.LandingPage;
import testcomponents.BaseTest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class FooterSection extends BaseTest {

    @Test
    public void urlsCheck() throws IOException {   // Function verifies if all footer URLs are working correctly

        landingPage.verifyFooterLinksRespCode();




       /* List<WebElement> links = driver.findElements(By.cssSelector("section[class*='bg-darkblue'] div[class*='flex'] a"));
        for (WebElement link : links) {
            String url = link.getAttribute("href");
            SoftAssert softAssert = new SoftAssert();

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("HEAD");
            conn.connect();
            int respCode = conn.getResponseCode();
            System.out.println(respCode);
            softAssert.assertTrue(respCode<400, "The link with the text: " + link.getText() + " is broken with code " + respCode);
        } */
    }
}
