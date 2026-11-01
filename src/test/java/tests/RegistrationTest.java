package tests;

import config.SetUp;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
// import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegistrationPage;
import utils.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.*;

public class RegistrationTest extends SetUp {
    @Test
    public void doReg(){
        RegistrationPage registrationPage = new RegistrationPage(driver);
        
        // clicking Register to enter registration page
        driver.findElement(By.partialLinkText("Register")).click();
        // getting random user details on Utils for registration
        Utils utils = new Utils();
        String firstName = utils.getFirstName();
        String lastName = utils.getLastName();
        String email = utils.getEmail();
        String password = utils.getPassword();
        String phoneNumber = utils.getPhoneNumber();
        String address = utils.getAddress();
        int gender = utils.getGender();
        // calling doReg method inside the RegistrationPage
        registrationPage.doReg(firstName, lastName, email, password,
                phoneNumber, address, gender);
                
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

        saveUser(firstName, lastName, email, password, phoneNumber, address, gender);
    }

    public void saveUser(String firstName, String lastName, String email, String password,
                         String phoneNumber, String address, int gender) {

        Path file = Path.of("src/test/resources/Users.json");
        JSONArray users = new JSONArray();

        try {
            // Read the existing array if the file already contains data.
            if (Files.exists(file) && Files.size(file) > 0) {
                users = new JSONArray(Files.readString(file));
            }

            // Create one JSON object for this user.
            JSONObject user = new JSONObject();
            user.put("firstName", firstName);
            user.put("lastName", lastName);
            user.put("email", email);
            user.put("password", password);
            user.put("phoneNumber", phoneNumber);
            user.put("address", address);
            user.put("gender", gender == 0 ? "Male" : "Female");

            // Add the user and write the full array back to the file.
            users.put(user);
            Files.writeString(file, users.toString(4));

        } catch (IOException e) {
            throw new RuntimeException("Could not save user to " + file, e);
        }
    }
}
