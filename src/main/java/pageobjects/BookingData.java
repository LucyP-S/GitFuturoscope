package pageobjects;

import abstractcomponents.AbstractComponents;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class BookingData extends AbstractComponents {

    WebDriver driver;

    public BookingData(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css="a[data-target='#billethotel-widget-calendar']")
    WebElement calendarButton;

    @FindBy(css="a[data-ng-click='mr.confirmDate()']")
    WebElement ChooseThisDateButton;

    @FindBy(css=".modal-title")
    WebElement confirmDateModal;

    @FindBy(css="[class='stretched-link widget-duree-sejour']")
    WebElement selectDurationButton;

    @FindBy(css="button[data-ng-disabled='!mr.canIncreaseDuree()']")
    WebElement increaseDaysButton;

    @FindBy(css="[class='stretched-link widget-nuits']")
    WebElement selectNightsButton;

    @FindBy(css="button[data-ng-disabled='!mr.canIncreaseNuits()']")
    WebElement increaseNightsButton;

    @FindBy(css=".stretched-link.widget-pax-sejour")
    WebElement participantsButton;

    @FindBy(xpath="(//button[@aria-label='More'])[7]")
    WebElement increaseAdultsButton;

    @FindBy(xpath="(//button[@aria-label='More'])[8]")
    WebElement increaseChildrenButton;

    @FindBy(xpath="(//button[@aria-label='More'])[9]")
    WebElement increaseInfantsButton;

    @FindBy(xpath="(//div[@class='col-lg-2 container-submit'])[2]")
    WebElement letsGoButton;

    @FindBy(css="#billethotel-datepicker-debut .datepicker-days tbody td.day:not(.disabled):not(.old)")
    List<WebElement> enabledDays;

    By calendarLoaded = By.cssSelector("#billethotel-datepicker-debut .datepicker-days tbody td.day:not(.disabled):not(.old)");


    public BookingData tapCalendarButton() {
        waitForElementToBeClickable(selectDurationButton);
        calendarButton.click();
        return this;
    }

    public List<WebElement> getEnabledDays() {
        waitForAllElementsLocated(calendarLoaded);
        return enabledDays;
    }

    public BookingData selectFirstEnabledDay(String today) throws InterruptedException {
        boolean clicked = false;

        for (WebElement day : enabledDays) {
            Thread.sleep(100);
            String dayText = day.getText();
            if (dayText.equals(String.valueOf(today))) {
                day.click();
                System.out.println("Today was available and is selected: " + today);
                clicked = true;
                break;
            }
            ;
        }
        if (!clicked && !enabledDays.isEmpty()) {
            WebElement nextEnabledDay = enabledDays.get(0);
            String nextDayText = nextEnabledDay.getText();
            nextEnabledDay.click();
            System.out.println("First available day was selected: " + nextDayText);
        } else if (!clicked) {
            System.out.println("No day was selected");
        }
        return this;
    }

    public BookingData tapChooseThisDateButton() {
        waitForElementToBeClickable(ChooseThisDateButton);
        ChooseThisDateButton.click();
        return this;
    }

    public String getSelectedDate() {
        waitForElementToBeClickable(ChooseThisDateButton);
        return confirmDateModal.getText();
    }

    public String getDisplayedDate() {
        return calendarButton.getText();
    }

    public BookingData tapSelectDurationButton() {
        waitForElementToBeClickable(selectDurationButton);
        selectDurationButton.click();
        return this;
    }

    public BookingData addDays(int days) {
        waitForElementToBeClickable(increaseDaysButton);
        int i = 0;
        while (i < days) {
            increaseDaysButton.click();
            i++;
        }
        return this;
    }

    public BookingData tapSelectNightsButton() {
        selectNightsButton.click();
        return this;
    }

    public BookingData addNights(int nights) {
        waitForElementToBeClickable(increaseNightsButton);
        int i = 0;
        while (i < nights) {
            increaseNightsButton.click();
            i++;
        }
        return this;
    }

    public BookingData tapParticipantsButton() {
        participantsButton.click();
        return this;
    }

    public BookingData addParticipants (int adults, int kids, int infants) {
        waitForElementToBeClickable(increaseAdultsButton);
        int i = 0;
        while (i < adults) {
            increaseAdultsButton.click();
            i++;
        }
        i = 0;
        while (i < kids) {
            increaseChildrenButton.click();
            i++;
        }
        i = 0;
        while (i < infants) {
            increaseInfantsButton.click();
            i++;
        }
        return this;
    }

    public ResultsPage tapLetsGoButton() {
        letsGoButton.click();
        return new ResultsPage(driver);
    }
}
