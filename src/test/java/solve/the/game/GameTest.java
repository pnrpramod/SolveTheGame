package solve.the.game;

/**
 * Java class to hold the logic for solving the game with webdriver
 */

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameTest {

    public WebDriver driver;

    public static long timeOutInSeconds = 5;

    private static final Logger LOGGER = Logger.getLogger( GameTest.class.getName() );


    @BeforeTest
    public void init() {
        //Creating a driver instance
        driver = GameUtil.createDriver();
        Assert.assertNotNull(driver);
    }

    @Test
    public void determineWeight() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, timeOutInSeconds);

        //Launching the url
        driver.get(GameConstants.SIMULATE_URL);

        //Wait until the element 'Weighings' is present
        GameUtil.presenceOfElement(driver, By.xpath(GameConstants.GAME_INFO));

        //Capturing the count of gold coins.
        List<WebElement> goldCoinIndexes = driver.findElements(By.xpath(GameConstants.GOLD_COIN_INDEXES));

        /*
        Creating initial list of gold coins as 0 - 8 assuming it would remain same. This has been made static
        in this case. This can been tweaked for dynamic creation.
         */
        List<Integer> indexes = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8));

        //Calling the util method to determine the index of the fakebar weight
        int fakeBar = GameUtil.determineWeight(driver, indexes);

        //Building the identifier of the fake bar element
        By fakeBarElement = By.xpath(GameConstants.GOLD_COIN_INDEX.concat(String.valueOf(fakeBar)).concat("')]"));

        //Clicking the fakeBar element and waiting for the success alert.
        GameUtil.clickElement(driver, fakeBarElement);
        webDriverWait.until(ExpectedConditions.alertIsPresent());

        //Switching to alert and reading the text message.
        String successAlert = driver.switchTo().alert().getText();

        //Validating that the alert message is as expected
        Assert.assertEquals(successAlert, GameConstants.SUCCESS_MESSAGE);

        //Accepting the alert
        driver.switchTo().alert().accept();

        LOGGER.log(Level.INFO, "The fake bar is "+ fakeBar);

    }

    @Test
    /*
    The below test exploits the current construction of the page where data-value is set as 0 for the solution.
     */
    public void determineWeighWithDataValue() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, timeOutInSeconds);

        //Launching the url
        driver.get(GameConstants.SIMULATE_URL);

        //Clicking the element directly based on the xpath.
        GameUtil.clickElement(driver, By.xpath(GameConstants.CHEAT_SOLUTION));

        //Waiting for the alert and reading the text message.
        webDriverWait.until(ExpectedConditions.alertIsPresent());
        String successAlert = driver.switchTo().alert().getText();

        //Validating that the alert message is as expected
        Assert.assertEquals(successAlert, GameConstants.SUCCESS_MESSAGE);

        //Accepting the alert
        driver.switchTo().alert().accept();


    }

    @AfterTest(alwaysRun = true)
    public void killBrowser() {
        //Destroying the driver instance
        driver.quit();
    }
}