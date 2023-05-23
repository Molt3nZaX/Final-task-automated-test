package exam.utils;

import org.apache.commons.lang3.RandomStringUtils;

import static aquality.selenium.browser.AqualityServices.getLogger;
import static constants.JsonKeys.QUANTITY_RANDOM_NUMBERS_KEY;
import static constants.SettingsFiles.TEST_DATA_FILE;

public class RandomTestDataUtils {
    public static String generateRandomText() {
        getLogger().info("Generating a random text");
        return RandomStringUtils.randomAlphanumeric((Integer) TEST_DATA_FILE.getValue(QUANTITY_RANDOM_NUMBERS_KEY));
    }
}