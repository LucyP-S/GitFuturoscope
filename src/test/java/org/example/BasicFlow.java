package org.example;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BasicFlow {
    public static void main(String[] args) throws InterruptedException, ParseException {

        int days = 3;
        int nights = 2;
        int adults = 2;
        int kids = 1;
        int infants = 0;

        final String buttonCalendar = "a[data-target='#billethotel-widget-calendar']";
        final String buttonChooseThisDate = "a[data-ng-click='mr.confirmDate()']";
        final String selectDuration = "[class='stretched-link widget-duree-sejour']";
        final String addDays = "button[data-ng-disabled='!mr.canIncreaseDuree()']";
        final String selectNights = "[class='stretched-link widget-nuits']";
        final String addNights = "button[data-ng-disabled='!mr.canIncreaseNuits()']";
        final String participants = ".stretched-link.widget-pax-sejour";
        final String addAdults = "(//button[@aria-label='More'])[7]";
        final String addKids = "(//button[@aria-label='More'])[8]";
        final String addInfants = "(//button[@aria-label='More'])[9]";
        final String letsGoButton = "(//div[@class='col-lg-2 container-submit'])[2]";
        final String textDisplayed = "div[class='container'] h2";
        final String dateSelected = "a[data-target='#billethotel-widget-calendar']";


        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("user-data-dir=/Users/lucyna.palucka/Library/Application Support/Google/Chrome/autotest");

        WebDriver driver = new ChromeDriver(options);
        Duration duration = Duration.ofSeconds(5);
        WebDriverWait expWait = new WebDriverWait(driver, duration);

        driver.get("https://www.futuroscope.com/en");
        expWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(buttonCalendar)));
        //JavascriptExecutor js = (JavascriptExecutor) driver;
        //js.executeScript("window.scrollBy(0,300)");

        // Get today's day from Calendar
        int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        System.out.println("Today's day is: " + dayOfMonth);

        // Select date from calendar
        driver.findElement(By.cssSelector(buttonCalendar)).click();
        expWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("#billethotel-datepicker-debut .datepicker-days tbody td.day:not(.disabled):not(.old)")));
        List<WebElement> enabledDays = driver.findElements(By.cssSelector("#billethotel-datepicker-debut .datepicker-days tbody td.day:not(.disabled):not(.old)"));
        boolean clicked = false;

        for (WebElement day : enabledDays) {
            Thread.sleep(500);
            String dayText = day.getText();
            if (dayText.equals(String.valueOf(dayOfMonth))) {
                day.click();
                System.out.println("Today was available and is selected: " + dayOfMonth);
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

        expWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(buttonChooseThisDate)));
        Thread.sleep(500);

        // Get selected date for assertion
        String selectedDate = driver.findElement(By.cssSelector(".modal-title")).getText();
        System.out.println("Selected date is: " + selectedDate);

        driver.findElement(By.cssSelector(buttonChooseThisDate)).click();

        // Days
        driver.findElement(By.cssSelector(selectDuration)).click();
        expWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(addDays)));
        addingDays(driver, days, addDays);

        // Nights - cannot be changed if Days are set
        /* driver.findElement(By.cssSelector(selectNights)).click();
        addingNights(driver, nights, addNights); */

        // Participants
        driver.findElement(By.cssSelector(participants)).click();
        expWait.until(ExpectedConditions.elementToBeClickable(By.xpath(addAdults)));
        addingParticipants(driver, adults, kids, infants, addAdults, addKids, addInfants);
        driver.findElement(By.xpath(letsGoButton)).click();

        // Assertions
        expWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(textDisplayed)));

        // Verify URL
        String URL = driver.getCurrentUrl();
        System.out.println("Full URL: " + URL);
        String[] urlParts = URL.split("Resultats");
        System.out.println("First part of the URL: " + urlParts[0]);
        Assert.assertEquals(urlParts[0], "https://booking.futuroscope.com/");
        System.out.println("URL is correct");

        // Verify dates
        // Create date object from selectedDate string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy", Locale.ENGLISH);
        LocalDate selectedDateFormatted = LocalDate.parse(selectedDate, formatter);
        System.out.println("Selected date saved as date object: "+selectedDateFormatted);

        // Get displayed date
        String displayedDate = driver.findElement(By.cssSelector("a[data-target='#billethotel-widget-calendar']")).getText();
        System.out.println("Displayed date is: "+displayedDate);

        // Displayed date as date object
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.ENGLISH);
        LocalDate displayedDateFormatted = LocalDate.parse(displayedDate, formatter2);
        System.out.println("Displayed date saved as date object: "+displayedDateFormatted);

        // Assert dates
        Assert.assertEquals(selectedDateFormatted, displayedDateFormatted);
        System.out.println("Assertion date is correct");

        driver.quit();
    }

    public static void addingDays(WebDriver driver, int days, String addDays) {
        int i = 0;
        while (i < days)
        {
            driver.findElement(By.cssSelector(addDays)).click();
            i++;
        }
    }

    public static void addingNights(WebDriver driver, int nights, String addNights) {
        int i = 0;
        while (i < nights)
        {
            driver.findElement(By.cssSelector(addNights)).click();
            i++;
        }
    }

    public static void addingParticipants (WebDriver driver, int adults, int kids, int infants, String addAdults, String addKids, String addInfants)
    {
        int i = 0;
        while (i < adults)
        {
            driver.findElement(By.xpath(addAdults)).click();
            i++;
        }
        i = 0;
        while (i < kids)
        {
            driver.findElement(By.xpath(addKids)).click();
            i++;
        }
        i = 0;
        while (i < infants)
        {
            driver.findElement(By.xpath(addInfants)).click();
            i++;
        }
    }
}


 /* @DataProvider
    public Object[][] getData() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("days", 4);
        map.put("nights", 4);
        map.put("adults", 4);
        map.put("kids", 3);
        map.put("infants", 2);

        HashMap<String, Integer> map1 = new HashMap<String, Integer>();
        map1.put("days", 3);
        map1.put("nights", 3);
        map1.put("adults", 2);
        map1.put("kids", 2);
        map1.put("infants", 0);

        return new Object[][] {{map}, {map1}};
    } */