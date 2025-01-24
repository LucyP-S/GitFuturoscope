package abstractcomponents;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class AbstractComponents {

    WebDriver driver;

    public AbstractComponents(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public AbstractComponents waitForElementToBeClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return this;
    }

    public AbstractComponents waitForElementToBeVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(element));
        return this;
    }

    public AbstractComponents waitForAllElementsLocated(By FindBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(FindBy));
        return this;
    }


    public AbstractComponents scrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,300)");
        return this;
    }

    public int getTodayFromCalendar() {
        int dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return dayOfMonth;
    }

    public String[] getFirstPartOfCurrentUrl() {
        String url = driver.getCurrentUrl();
        System.out.println("Full URL: " + url);
        String[] urlParts = url.split("Resultats");
        System.out.println("First part of the URL: " + urlParts[0]);
        return urlParts;
    }

    public LocalDate selectedDateAsDateObject(String selectedDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.ENGLISH);
        LocalDate selectedDateFormatted = LocalDate.parse(selectedDate, formatter);
        System.out.println("Selected date saved as date object: "+selectedDateFormatted);
        return selectedDateFormatted;
    }

    public LocalDate displayedDateAsDateObject(String displayedDate) {
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.ENGLISH);
        LocalDate displayedDateFormatted = LocalDate.parse(displayedDate, formatter2);
        System.out.println("Displayed date saved as date object: "+displayedDateFormatted);
        return displayedDateFormatted;
    }




}
