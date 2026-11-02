package tests;

import config.SetUp;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddCostPage;
import pages.LoginPage;
import pages.UserPage;
import utils.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public class AddCostTest extends SetUp {
    @BeforeMethod
    public void getAuthToken() throws IOException {
        // getting authToken first
        String authToken = Files.readString(
                Path.of("src/test/resources/AuthToken.txt")
        ).trim();
        // injecting authToken on localStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "localStorage.setItem('authToken', arguments[0]);",
                authToken
        );

        // get /add-cost page
        driver.get("https://dailyfinance.roadtocareer.net/add-cost");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(priority=1, description = "Add item with full info inputs")
    public void doAddCostFull() throws IOException {
        // getting random details of utils
        Utils utils = new Utils();
        String itemName = utils.getItemName();
        double amount = utils.getAmount();
        String purchaseDate = utils.getPurchaseDate();
        String month = utils.getMonth();
        String remarks = utils.getRemarks();
        // initializing AddCostPage and inserting utils into it
        AddCostPage addCostPage = new AddCostPage(driver);
        String successMsg = addCostPage.doAddCostFull(
                itemName, amount, purchaseDate, month, remarks
        );
        Assert.assertEquals(successMsg, "Product added successfully!");
    }

    @Test(priority = 2, description = "Add item with only required inputs")
    public void doAddCostRequires() throws IOException {
        // getting random details of utils
        Utils utils = new Utils();
        String itemName = utils.getItemName();
        double amount = utils.getAmount();
        // initializing AddCostPage and inserting utils into it
        AddCostPage addCostPage = new AddCostPage(driver);
        String successMsg = addCostPage.doAddCostRequires(
                itemName, amount
        );
        Assert.assertEquals(successMsg, "Product added successfully!");
    }
}
