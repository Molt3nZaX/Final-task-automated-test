package exam;

import aquality.selenium.browser.Browser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static constants.JsonKeys.*;
import static constants.SettingsFiles.CONFIG_DATA_FILE;
import static exam.utils.UrlCreatorUtils.createUrl;

public abstract class BaseTest {
    @BeforeMethod
    public void setUp() {
        Browser browser = getBrowser();
        browser.maximize();
        browser.goTo(createUrl(CONFIG_DATA_FILE.getValue(LOGIN_KEY).toString(),
                CONFIG_DATA_FILE.getValue(PASSWORD_KEY).toString(),
                Integer.parseInt(CONFIG_DATA_FILE.getValue(PORT_KEY).toString()),
                CONFIG_DATA_FILE.getValue(WEB_PATH_KEY).toString()));
    }

    @AfterMethod
    public void tearDown() {
        Browser browser = getBrowser();
        browser.quit();
    }
}