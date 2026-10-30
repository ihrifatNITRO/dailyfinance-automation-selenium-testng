package tests;

import config.SetUp;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegistrationPage;

import java.time.Duration;

public class RegistrationTest extends SetUp {
    @Test
    public void doReg(){
        RegistrationPage registrationPage = new RegistrationPage(driver);
        
        // clicking Register to enter registration page
        driver.findElement(By.partialLinkText("Register")).click();
        // calling doReg method inside the RegistrationPage
        registrationPage.doReg("Ismail", "Hossain",
            "adf+12so2d2@gmail.com", "1234", "01111111111",
                "Dhaka, Bangladesh", 0);
                
        // waiting for success toast
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Locating the success toast
        By successToast = By.cssSelector(".Toastify__toast--success .Toastify__toast-body");
        // Performing the assertion
        Assert.assertTrue(
            wait.until(
                    // Waiting for the expected message
                ExpectedConditions.textToBePresentInElementLocated(
                    successToast, "registered successfully"
                )
            ),
                "Registration failed"
        );
    }
}
