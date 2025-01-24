package pageobjects;

import abstractcomponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultsPage extends AbstractComponents {
    WebDriver driver;

    public ResultsPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css="div[class='container'] h2")
    WebElement resultsPageTitle;

    public ResultsPage waitForResultsPageToLoad() {
        waitForElementToBeVisible(resultsPageTitle);
        return this;
    }
}
