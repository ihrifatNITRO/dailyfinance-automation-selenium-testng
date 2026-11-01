package tests;

import config.SetUp;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.time.Duration;

public class LoginTest extends SetUp {
    @Test
    public void doLogin(){
        LoginPage loginPage = new LoginPage(driver);
        
        loginPage.doLogin("adf12@gmail.com", "1234$$ruet2020");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        Assert.assertTrue(
            wait.until(
                    ExpectedConditions.textToBePresentInElementLocated(
                        By.tagName("h2"), "User Daily Costs"
                    )   
            ),
                "User page not displayed!"
        );
        System.out.println("Logged in to User Page");
    }
}
