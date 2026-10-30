package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class RegistrationPage {
    @FindBy(id = "firstName")
    WebElement txtFirstname;
    @FindBy(id = "lastName")
    WebElement txtLastName;
    @FindBy(id = "email")
    WebElement txtEmail;
    @FindBy(id = "password")
    WebElement txtPassword;
    @FindBy(id = "phoneNumber")
    WebElement txtPhoneNumber;
    @FindBy(id = "address")
    WebElement txtAddress;
    @FindBy(css = "[type=radio]")
    List<WebElement> choiceGender;
    @FindBy(css = "[type=checkbox]")
    WebElement checkAccept;
    @FindBy(id = "register")
    WebElement btnRegister;
    
    
    public RegistrationPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }
    
    public void doReg(String firstName, String lastName, String email,
                      String password, String phoneNumber, String address, int gender){
        txtFirstname.sendKeys(firstName);
        txtLastName.sendKeys(lastName);
        txtEmail.sendKeys(email);
        txtPassword.sendKeys(password);
        txtPhoneNumber.sendKeys(phoneNumber);
        txtAddress.sendKeys(address);
        choiceGender.get(gender).click();
        checkAccept.click();
        btnRegister.click();
    }
}
