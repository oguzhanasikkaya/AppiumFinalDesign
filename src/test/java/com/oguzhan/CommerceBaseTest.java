package com.oguzhan;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class CommerceBaseTest {

    public AndroidDriver driver;
    AppiumDriverLocalService service;

    @BeforeClass
    public void ConfigureAppium() throws MalformedURLException {

        //start the server automatic

        service = new AppiumServiceBuilder()
                .withAppiumJS(new File("C:\\Users\\90551\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .withTimeout(Duration.ofSeconds(300))
                .build();
        service.start();

        UiAutomator2Options options = new UiAutomator2Options();
        options.setUdid("c2f6b95c");
        options.setChromedriverExecutable("D:\\chromedriver.exe");//in order to web side
        options.setApp("C:\\Users\\90551\\IdeaProjects\\AgainAppium\\src\\test\\java\\resources\\General-Store.apk");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"),options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


    }
    @AfterClass
    public void tearDown() throws InterruptedException {
        //Thread.sleep(2000);
        driver.quit();
        service.stop();
    }

    /**
     * This method is used to long press the specified element
     * @param element
     */
    public void longPressGesture(WebElement element){
       ((JavascriptExecutor)driver).executeScript("mobile: longClickGesture",
       ImmutableMap.of("elementId",( (RemoteWebElement)element).getId(),"duration",2000));

    }
/**
 * This method is used to scroll the screen down until you see the desired text.
 */
    public  void scrollToText(String text){
        driver.findElement
        (AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+text+"\"));"));
    }

    /**
     * This method is useful if the screen can be scrolled in the desired direction and rate.
     * @param percent
     * @param direction
     */
    public void scrollToEnd(double percent, String direction){
        boolean canScrollMore;
        do {
            canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, "top", 100, "width", 200, "height", 200,
                    "direction", direction,
                    "percent", percent
            ));

        }while (canScrollMore) ;
    }



    /**
     * This method swipe the desired element in the desired direction
     * @param element
     * @param direction
     */
    public void swipeAction(WebElement element,String direction){
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId",((RemoteWebElement)element).getId(),
                "direction", direction,
                "percent", 0.75
        ));

    }
    public void sleep(int second){

        try {
            Thread.sleep(second* 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * this method allows to rotate the screen in the desired direction
     * right or left
     */
    public void rotationLandScape(String rotation){
        DeviceRotation landScape;
        if (rotation.equalsIgnoreCase("right")){
             landScape = new DeviceRotation(0,0,90);
            driver.rotate(landScape);
        } else if (rotation.equalsIgnoreCase("left")) {
            landScape = new DeviceRotation(0,90,0);
            driver.rotate(landScape);
        }
    }

    public Double getFormattedAmount(String amount){
        return Double.parseDouble(amount.substring(1));
    }



}
