package tests;

import config.SetUp;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProfileUpdatePage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class ProfileUpdateTest extends SetUp {
    @BeforeMethod
    public void login() throws IOException {
        /* The saved token was accepted from storage, but the app could not
        find a matching user for it when /user loaded, so it showed
        “User ID not found.” The token may have been valid but missing
        session context the app expects; the error alone doesn’t tell us
         exactly why. Logging in through the page works because it lets
         the app create the complete session itself.*/
        // Log in directly so this test does not depend on an auth token saved by another test.
        JSONArray users = new JSONArray(Files.readString(
                Path.of("src/test/resources/Users.json")
        ));
        JSONObject user = users.getJSONObject(users.length() - 1);
        new LoginPage(driver).doLogin(user.getString("email"), user.getString("password"));

        /* Previous token-based setup retained for easy rollback:
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
        // get /user page
        driver.get("https://dailyfinance.roadtocareer.net/user");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        */
    }

    @Test
    public void changeEmail() throws InterruptedException, IOException {
        ProfileUpdatePage profilePageUpdate = new ProfileUpdatePage(driver);
        String editEmail = "abra"+ UUID.randomUUID() +"@gmail.com";
        String message = profilePageUpdate.changeEmail(editEmail);
        Assert.assertEquals(message, "User updated successfully!");

        // saving the updated email on that exact user details
        Path usersFilePath = Path.of("src/test/resources/Users.json");
        JSONArray users = new JSONArray(Files.readString(usersFilePath));
        JSONObject user = users.getJSONObject(users.length() - 1);
        user.put("email", editEmail);
        Files.writeString(usersFilePath, users.toString(4));
    }
}
