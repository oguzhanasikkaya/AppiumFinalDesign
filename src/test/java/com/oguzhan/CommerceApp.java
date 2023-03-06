package com.oguzhan;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.oguzhan.pages.FormPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class CommerceApp extends CommerceBaseTest{

    @Test
    public void login(){
        FormPage formPage = new FormPage(driver);
        formPage.setNameField("oğuzhan");


        driver.findElement(By.xpath("//android.widget.RadioButton[@text='Female']")).click();
        driver.findElement(By.id("android:id/text1")).click();
        /*driver.findElement
                (AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Argentina\"));"));*/
        scrollToText("Argentina");

        driver.findElement(By.xpath("//android.widget.TextView[@text='Argentina']")).click();

        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        
    }
    @Test
    public void handleToastMessage(){
        driver.findElement(By.xpath("//android.widget.RadioButton[@text='Female']")).click();
        driver.findElement(By.id("android:id/text1")).click();
        scrollToText("Argentina");
        driver.findElement(By.xpath("//android.widget.TextView[@text='Argentina']")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();

        //android.widget.Toast
        String toastMessage = driver.findElement(By.xpath("(//android.widget.Toast)[1]")).getAttribute("name");
        assertEquals(toastMessage,"Please enter your name");
    }

    @Test
    public void scrollAndSelectThenGoToCart(){
        login();
        scrollToText("Jordan 6 Rings");

        int productCount = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).size();

        for (int i = 0; i < productCount; i++) {
            String productName = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).get(i).getText();
            if (productName.equalsIgnoreCase("Jordan 6 Rings")){
                driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart")).get(i).click();
            }

        }
        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();

        //wait until cart page loaded
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement titleElement = driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title"));
        wait.until(ExpectedConditions.attributeContains(titleElement,"text","Cart"));

        String lastPageProduct = driver.findElement(By.id("com.androidsample.generalstore:id/productName")).getText();
        assertEquals(lastPageProduct,"Jordan 6 Rings");

    }

    @Test
    public void sumOfProductPrize(){
        login();

        //driver.findElement(By.xpath("(//android.widget.TextView[@text=\"ADD TO CART\"])[1]")).click();
        driver.findElements(By.xpath("//android.widget.TextView[@text=\"ADD TO CART\"]")).get(0).click();
        driver.findElements(By.xpath("//android.widget.TextView[@text=\"ADD TO CART\"]")).get(0).click();

        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();

        List<WebElement> productsPrices = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice"));
        int count = productsPrices.size();
        double sum = 0;
        for (int i = 0; i < count; i++) {
            String amountString = productsPrices.get(i).getText();
            double price = Double.parseDouble(amountString.substring(1));
            sum += price;
        }

        String totalPrice = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
        Double totalFormattedPrice = getFormattedAmount(totalPrice);
        Assert.assertEquals(sum,totalFormattedPrice);


    }

    @Test
    public void readTermOfCondition(){
        sumOfProductPrize();
        WebElement termsBtn = driver.findElement(By.id("com.androidsample.generalstore:id/termsButton"));
        longPressGesture(termsBtn);
        WebElement closeBtn = driver.findElement(By.id("android:id/button1"));
        closeBtn.click();

        driver.findElement(AppiumBy.className("android.widget.CheckBox")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();

    }
    @Test
    public void webSide(){
        readTermOfCondition();
        sleep(5);
        Set<String> contexts = driver.getContextHandles();
        for (String context : contexts) {
            System.out.println(context);
            //NATIVE_APP
            //WEBVIEW_com.androidsample.generalstore
        }
        driver.context("WEBVIEW_com.androidsample.generalstore"); //we need chrome driver
        driver.findElement(By.name("q")).sendKeys("oğuzhan aşıkkaya");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        driver.pressKey(new KeyEvent(AndroidKey.BACK));

        driver.context("NATIVE_APP");
    }


}
