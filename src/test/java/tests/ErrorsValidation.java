package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import testcomponents.BaseTest;
import testcomponents.Retry;

public class ErrorsValidation extends BaseTest {


    @Test(groups="Error Handling")
    public void testFailedOnPurpose() {
        String textDisplayed = landingPage.getTextOnMovie();
        System.out.println(textDisplayed);
        Assert.assertEquals(textDisplayed, "2 parks");
    }
}
