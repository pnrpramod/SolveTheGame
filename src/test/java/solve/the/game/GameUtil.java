package solve.the.game;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;

/**
 * Utility class to hold all the logic that could potentially be re-used
 */
public class GameUtil {

    /**
     * Method to generate a chromedriver instance and return the same.
     * This method checks for OS and returns the instance accordingly.
     * By default, if the OS is not Windows, it would return driver instance for Mac.
     *
     * @return chromedriver
     */
    public static WebDriver createDriver() {

        File binary;
        //Checking the OS and determining the chrome driver binary.
        String os = System.getProperty("os.name");
        os = os.toLowerCase();

        if (os.contains("windows")) {
            binary = new File(GameConstants.WINDOWS_BINARY_PATH);
        }
        //defaulting the binary to Mac if it is not Windows.
        else {
            binary = new File(GameConstants.MAC_BINARY_PATH);
        }
        System.setProperty("webdriver.chrome.driver", binary.getAbsolutePath());
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;

    }


    /**
     * This method applies binary search to the weights and picks the weight that is fake.
     * @param driver - WebDriver instance
     * @param list - list of weights in the indexes form
     * @return the index of the fakebar index
     */
    public static int determineWeight(WebDriver driver, List<Integer> list) {
        List<Integer> leftList;
        List<Integer> rightList;
        int fakeBar = 0;
        int middleIndex;
        while (list.size() > 1) {
            middleIndex = list.size()/2;
            if(list.size()%2==0){
                leftList = list.subList(0, ((list.size()) / 2));
                rightList = list.subList(((list.size()) / 2) , list.size());
            }
            else{
                leftList = list.subList(0, (list.size()) / 2);
                rightList = list.subList(((list.size()) / 2) + 1, list.size());
            }

            //Calling the verdict method to get the verdict
            String verdict = enterWeightsVerdict(driver, leftList, rightList);

            //Clicking the reset button.
            clickElement(driver, By.xpath(GameConstants.RESET_BUTTON));

            //Making a decision based on verdict.
            if(verdict.contains("=")){
                return middleIndex;
            }
            else if(verdict.contains(">")){
                if(list.size()==2){
                    return list.get(1);
                }
                list = rightList;
            }
            else{
                if(list.size()==2){
                    return list.get(0);
                }
                list = leftList;
            }
        }
        return  fakeBar;
    }


    /**
     * This method enters the two lists into two different weigh scales and weighs. Then, the recent verdict
     * will be passed as a string.
     * @param driver - WebDriver instance
     * @param leftList - Left List of the master list
     * @param rightList - Right List of the master List
     * @return the verdict after weighing
     */
    public static String enterWeightsVerdict(WebDriver driver, List<Integer> leftList, List<Integer> rightList){
        for (int i=0;i<leftList.size();i++) {
            By by = By.xpath(GameConstants.LEFT_BOX.concat(String.valueOf(i)).concat("']"));
            driver.findElement(by).sendKeys(String.valueOf(leftList.get(i)));
        }

        for (int i=0;i<rightList.size();i++) {
            By by = By.xpath(GameConstants.RIGHT_BOX.concat(String.valueOf(i)).concat("']"));
            driver.findElement(by).sendKeys(String.valueOf(rightList.get(i)));
        }

        clickElement(driver, By.xpath(GameConstants.WEIGH_BUTTON));

        List<WebElement> items = driver.findElements(By.xpath(GameConstants.ITEM_WEIGHINGS));
        By recentWeighing = By.xpath(GameConstants.LIST_ITEM_WEIGHINGS.concat(String.valueOf(items.size())).concat("]"));
        return driver.findElement(recentWeighing).getText();
    }

    /**
     * Verifies if an element is present.
     *
     * @param driver  - WebDriver Instance
     * @param element - WebElement
     */
    public static void presenceOfElement(WebDriver driver, By element) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(element));
        } catch (NoSuchElementException e) {
            throw e;
        }
    }

    /**
     * Method to click an element
     *
     * @param driver  - WebDriver Instance
     * @param element - WebElement
     */
    public static void clickElement(WebDriver driver, By element) {
        driver.findElement(element).click();
    }
}
