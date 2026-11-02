package tests;

import config.SetUp;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.UserPage;
import utils.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public class UserTest extends SetUp {

    @Test
    public void clickAddCost() throws IOException {
        // getting authToken for use
        String authToken = Files.readString(
                Path.of("src/test/resources/AuthToken.txt")
        ).trim();
        // injecting authToken on localStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "localStorage.setItem('authToken', arguments[0]);", authToken
        );

        // get /user page
        driver.get("https://dailyfinance.roadtocareer.net/user");
        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/user"));
//        wait.until(ExpectedConditions.textToBePresentInElementLocated(
//                org.openqa.selenium.By.tagName("h2"), "User Daily Costs"
//        ));
        // clicking on the "Add cost" button
        UserPage userPage= new UserPage(driver);
        userPage.clickAddCost();
        // assuring if it landed on add cost page or not
        Assert.assertTrue(
                wait.until(
                        ExpectedConditions.textToBePresentInElementLocated(
                                By.tagName("h2"), "Add New Cost"
                        )
                ),
                "Add New Cost Failed"
        );
        System.out.println("Add New Cost Page Appeared");
    }
}
