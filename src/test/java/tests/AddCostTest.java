package tests;

import config.SetUp;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AddCostPage;
import pages.LoginPage;
import pages.UserPage;

import java.time.Duration;

public class AddCostTest extends SetUp {
    @Test
    public void doAddCostFull(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin("adf12@gmail.com", "1234$$ruet2020");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.tagName("h2"), "User Daily Costs"));

        UserPage userPage = new UserPage(driver);
        userPage.clickAddCost();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.tagName("h2"), "Add New Cost"));

        AddCostPage addCostPage = new AddCostPage(driver);
        String successMsg = addCostPage.doAddCostFull(
                "Item-1", 100.0, "01022027", "June", "This is item-1");

        Assert.assertEquals(successMsg, "Product added successfully!");
    }
}
