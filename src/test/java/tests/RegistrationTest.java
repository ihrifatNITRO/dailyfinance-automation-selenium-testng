package tests;

import config.SetUp;
import org.openqa.selenium.By;
// import org.openqa.selenium.WebElement;
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
            "adf12@gmail.com", "1234$$ruet2020", "01111111111",
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
        System.out.println("Registration Successful!");

        // wait for 10s until login page with h1 "Login" tag appears
        Assert.assertTrue(
            wait.until(
                ExpectedConditions.textToBePresentInElementLocated(
                    By.tagName("h1"), "Login"
                )
            ),
                "Login page not displayed!"
        );
        System.out.println("Redirected to Login Page");
    }
}
