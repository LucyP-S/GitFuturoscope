package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.BookingData;
import pageobjects.ResultsPage;
import testcomponents.BaseTest;
import testcomponents.Retry;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class BookingFlow extends BaseTest {

    @Test(dataProvider = "getData", groups= {"SmokeTests"}, retryAnalyzer = Retry.class)
    public void flowWithSelectingDays(HashMap<String, String> input) throws InterruptedException, ParseException {

        BookingData bookingData = new BookingData(driver);
        bookingData.tapCalendarButton().scrollDown();
        int today = bookingData.getTodayFromCalendar();
        bookingData.getEnabledDays();
        bookingData.selectFirstEnabledDay(String.valueOf(today));
        String selectedDate = bookingData.getSelectedDate();  // Get selected date for assertion
        bookingData.tapChooseThisDateButton().tapSelectDurationButton().addDays(Integer.parseInt(input.get("days"))).tapParticipantsButton().addParticipants(Integer.parseInt(input.get("adults")), Integer.parseInt(input.get("kids")), Integer.parseInt(input.get("infants")));
        ResultsPage resultsPage = bookingData.tapLetsGoButton().waitForResultsPageToLoad();
        String[] urlParts = resultsPage.getFirstPartOfCurrentUrl();  // Assert URLs
        Assert.assertEquals(urlParts[0], "https://booking.futuroscope.com/");
        System.out.println("Assertion: URL is correct");
        String displayedDate = bookingData.getDisplayedDate();  // Get displayed date for assertion
        System.out.println("Displayed date is: " + displayedDate);
        LocalDate selectedDateFormatted = resultsPage.selectedDateAsDateObject(selectedDate); // Create date object from selectedDate string
        LocalDate displayedDateFormatted = resultsPage.displayedDateAsDateObject(displayedDate);
        Assert.assertEquals(selectedDateFormatted, displayedDateFormatted); // Assert dates
        System.out.println("Assertion: date is correct");
    }

    @Test(dataProvider = "getData", retryAnalyzer = Retry.class)
    public void flowWithSelectingNights(HashMap<String, String> input) throws InterruptedException, ParseException {

        BookingData bookingData = new BookingData(driver);
        bookingData.tapCalendarButton().scrollDown();
        int today = bookingData.getTodayFromCalendar();
        bookingData.getEnabledDays();
        bookingData.selectFirstEnabledDay(String.valueOf(today));
        String selectedDate = bookingData.getSelectedDate();  // Get selected date for assertion
        bookingData.tapChooseThisDateButton().tapSelectNightsButton().addNights(Integer.parseInt(input.get("nights"))).tapParticipantsButton().addParticipants(Integer.parseInt(input.get("adults")), Integer.parseInt(input.get("kids")), Integer.parseInt(input.get("infants")));
        ResultsPage resultsPage = bookingData.tapLetsGoButton().waitForResultsPageToLoad();
        String[] urlParts = resultsPage.getFirstPartOfCurrentUrl();  // Assert URLs
        Assert.assertEquals(urlParts[0], "https://booking.futuroscope.com/");
        String displayedDate = bookingData.getDisplayedDate();  // Get displayed date for assertion
        LocalDate selectedDateFormatted = resultsPage.selectedDateAsDateObject(selectedDate); // Create date object from selectedDate string
        LocalDate displayedDateFormatted = resultsPage.displayedDateAsDateObject(displayedDate); // Create date object from displayed date string
        Assert.assertEquals(selectedDateFormatted, displayedDateFormatted); // Assert dates
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//data//DataToUse.json");
        return new Object[][]{{data.get(0)}, {data.get(1)}};
    }
}

