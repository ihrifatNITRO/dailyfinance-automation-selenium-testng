package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddCostPage {
    @FindBy(id = "itemName")
    WebElement txtItemName;
    @FindBy(id = "amount")
    WebElement numAmount;
    @FindBy(id = "purchaseDate")
    WebElement datePurchaseDate;
    @FindBy(id = "month")
    WebElement selectMonth;
    @FindBy(id = "remarks")
    WebElement txtRemarks;
    @FindBy(css = "[type=submit]")
    WebElement btnSubmit;

    // saving the driver in private final so that this can be used here only and for one time
    private final WebDriver driver;
    public AddCostPage(WebDriver driver) {
        this.driver = driver; // saving for future use here on one time
        PageFactory.initElements(driver, this);
    }

    public String doAddCostFull(String itemName, double amount,
                              String purchaseDate, String month,
                              String remarks) {

        txtItemName.sendKeys(itemName);
        numAmount.sendKeys(String.valueOf(amount));
        datePurchaseDate.click();
        datePurchaseDate.sendKeys(purchaseDate);
        Select monthDropDown = new Select(selectMonth);
        monthDropDown.selectByVisibleText(month);
        txtRemarks.sendKeys(remarks);
        btnSubmit.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Alert successAlert = wait.until(
                ExpectedConditions.alertIsPresent()
        );
        String successMsg = successAlert.getText();
        successAlert.accept();
        return successMsg;
    }
}
