package resources;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class ExtentReporterNG {
    public static ExtentReports getReportObject() {
        String path = System.getProperty("user.dir")+"//reports//index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Extent Report Futuroscope");
        reporter.config().setDocumentTitle("Extent Report Title");

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Lucy");
        return extent;
    }
}
