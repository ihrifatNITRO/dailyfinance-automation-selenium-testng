package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfileUpdatePage {
    @FindBy(css = "svg[data-testid='AccountCircleIcon']")
    WebElement accountCircleIcon;
    @FindBy(xpath = "//li[contains(text(), 'Profile')]")
    WebElement btnProfile;
    @FindBy(xpath = "//li[contains(text(), 'Logout')]")
    WebElement btnLogout;
    @FindBy(xpath = "//button[text() = 'Edit'] ")
    WebElement btnEdit;
    @FindBy(css = "input[type='email']")
    WebElement txtEmailChange;
    @FindBy(xpath = "//button[text() = 'Update']")
    WebElement btnUpdate;

    // saving the driver in private final so that this can be used here only and for one time
    private final WebDriver driver1;
    public ProfileUpdatePage(WebDriver driver) {
        this.driver1 = driver;
        PageFactory.initElements(driver, this);
    }

    public String changeEmail(String email) throws InterruptedException {
        accountCircleIcon.click();
        Thread.sleep(300);
        btnProfile.click();
        Thread.sleep(300);
        btnEdit.click();
        txtEmailChange.click();
        txtEmailChange.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE, email);
        btnUpdate.click();
        // clicking alert ok
        WebDriverWait wait = new WebDriverWait(driver1, Duration.ofSeconds(10));
        Alert successAlert = wait.until(
                ExpectedConditions.alertIsPresent()
        );
        String successMsg = successAlert.getText();
        successAlert.accept();
        // clicking logout
        accountCircleIcon.click();
        Thread.sleep(300);
        btnLogout.click();

        return successMsg;
    }
}
