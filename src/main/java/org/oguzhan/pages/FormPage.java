package org.oguzhan.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;


public class FormPage {
    AndroidDriver driver;

    public FormPage(AndroidDriver driver){
        this.driver = driver;

        PageFactory.initElements(new AppiumFieldDecorator(driver),this);

    }


    @AndroidFindBy(id = "com.androidsample.generalstore:id/nameField")
    private WebElement nameField;

    @AndroidFindBy(xpath = "/android.widget.RadioButton[@text='Female']")
    private WebElement femaleOption;
    @AndroidFindBy(xpath = "/android.widget.RadioButton[@text='Male']")
    private WebElement maleOption;

    public void setNameField(String name){
        nameField.sendKeys(name);
        driver.hideKeyboard();
    }
}
