package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.sql.Driver;

public class UserPage {
    @FindBy(className = "add-cost-button")
    WebElement btnAddCost;

    public UserPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void clickAddCost() {
        btnAddCost.click();
    }
}
