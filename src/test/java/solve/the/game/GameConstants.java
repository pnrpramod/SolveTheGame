package solve.the.game;

/**
 * Class to hold all the constants
 */

public class GameConstants {

    public static final String SIMULATE_URL = "http://ec2-54-208-152-154.compute-1.amazonaws.com/";

    public static final String SUCCESS_MESSAGE = "Yay! You find it!";

    public static final String FAILURE_MESSAGE = "Oops! Try Again!";

    public static final String WINDOWS_BINARY_PATH = "src/test/resources/chromedriver.exe";

    public static final String MAC_BINARY_PATH = "src/test/resources/chromedriver";

    //Identifiers
    public static final String GAME_INFO = "//div[@class='game-info']";

    public static final String RESET_BUTTON = "//button[@id='reset' and contains(text(),'Reset')]";

    public static final String WEIGH_BUTTON = "//button[@id='weigh' and contains(text(),'Weigh')]";

    public static final String GOLD_COIN_INDEXES = "//div[@class='coins']/button[contains(@id,'coin_')]";

    public static final String GOLD_COIN_INDEX = "//div[@class='coins']/button[contains(@id,'coin_";

    public static final String CHEAT_SOLUTION = "//div[@class='coins']/button[contains(@id,'coin_') and @data-value=0]";

    public static final String LEFT_BOX = "//input[@id='left_";

    public static final String RIGHT_BOX = "//input[@id='right_";

    public static final String ITEM_WEIGHINGS = "//div[@class='game-info']//li";

    public static final String LIST_ITEM_WEIGHINGS = "//div[@class='game-info']//li[";

}
