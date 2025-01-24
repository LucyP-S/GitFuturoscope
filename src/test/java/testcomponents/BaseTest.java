package testcomponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.BrowserType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pageobjects.LandingPage;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public class BaseTest {
    public WebDriver driver;
    public LandingPage landingPage;

    public void launchBrowser(BrowserType selectedBrowser) {
        {
            switch (selectedBrowser) {
                case CHROME: {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    // options.addArguments("start-maximized");
                    options.addArguments("user-data-dir=/Users/lucyna.palucka/Library/Application Support/Google/Chrome/autotest");
                    driver = new ChromeDriver(options);
                    break;
                }

                case FIREFOX: {
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                }

                default: {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    // options.addArguments("start-maximized");
                    options.addArguments("user-data-dir=/Users/lucyna.palucka/Library/Application Support/Google/Chrome/autotest");
                    driver = new ChromeDriver(options);
                    break;
                }
            }
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
        }
    }

    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {

        // Read JSON as String
        String jsonContent = FileUtils.readFileToString(new File(filePath), "UTF-8");

        // Convert String to HashMap (Jackson DataBind)
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });
        return data;
    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir")+"//reports//"+ testCaseName+ ".png");
        FileUtils.copyFile(source, file);
        return System.getProperty("user.dir")+"//reports//"+ testCaseName+ ".png";
    }


    @BeforeMethod
    public LandingPage launchApplication() throws IOException {
        launchBrowser(BrowserType.CHROME);
        landingPage = new LandingPage(driver);
        landingPage.goTo();
        return landingPage;
    }

    @AfterMethod
    public void quitDriver() {
        driver.quit();
    }
}





    /* public WebDriver initializeDriver() throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//src//main//resources//GlobalData.properties");
        prop.load(fis);
        String browserName = prop.getProperty("browser");

        if (browserName.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            //options.addArguments("start-maximized");
            options.addArguments("user-data-dir=/Users/lucyna.palucka/Library/Application Support/Google/Chrome/autotest");
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        } else if (browserName.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();

        } else if (browserName.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();

        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    @BeforeMethod
    public LandingPage launchApplication() throws IOException {
        driver = initializeDriver();
        landingPage = new LandingPage(driver);
        landingPage.goTo();
        return landingPage;
    } */





/*
    } */