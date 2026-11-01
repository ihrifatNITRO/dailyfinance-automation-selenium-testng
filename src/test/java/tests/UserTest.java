package tests;

import config.SetUp;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.UserPage;

import java.time.Duration;

public class UserTest extends SetUp {
    @Test
    public void clickAddCost() {
        UserPage userPage= new UserPage(driver);
        userPage.clickAddCost();

        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));
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
