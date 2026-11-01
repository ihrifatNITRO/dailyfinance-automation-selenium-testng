package tests;

import config.SetUp;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public class LoginTest extends SetUp {
    @Test
    public void doLogin() throws IOException {
        // Getting registered user detail JSONArray
        Path file = Path.of("src/test/resources/Users.json");
        JSONArray users = new JSONArray(Files.readString(file));
        // Picking 0-index user details for Login
        JSONObject user = users.getJSONObject(0);
        String email = user.getString("email");
        String password = user.getString("password");
        // Doing Login using the details
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin(email, password);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Assert.assertTrue(
            wait.until(
                    ExpectedConditions.textToBePresentInElementLocated(
                        By.tagName("h2"), "User Daily Costs"
                    )   
            ),
                "User page not displayed!"
        );

        // Cast driver to JavascriptExecutor so that auth_token can be saved
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Execute JavaScript to grab the token from local storage
        String authToken = (String) js.executeScript(
                "return window.localStorage.getItem('authToken')"
        );
        System.out.println("Extracted Auth Token: " + authToken);
        Assert.assertNotNull(authToken, "Auth token was not found in Local Storage!");

        // Saving the authToken inside AuthToken.txt file
        Files.writeString(
                Path.of("src/test/resources/AuthToken.txt"), authToken);

        System.out.println("Logged in to User Page");
    }
}
