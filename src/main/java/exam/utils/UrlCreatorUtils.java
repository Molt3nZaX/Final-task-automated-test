package exam.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import static aquality.selenium.browser.AqualityServices.getLogger;
import static constants.JsonKeys.*;
import static constants.SettingsFiles.CONFIG_DATA_FILE;
import static constants.SettingsFiles.TEST_DATA_FILE;

public class UrlCreatorUtils {
    public static String createUrl(String login, String password, int portValue, String path) {
        getLogger().info("Generating URL with credentials");
        String credentials = login + TEST_DATA_FILE.getValue(COLON_KEY) + password;
        try {
            return String.valueOf(new URI(CONFIG_DATA_FILE.getValue(PROTOCOL_KEY).toString(), credentials, CONFIG_DATA_FILE.getValue(HOST_KEY).toString(), portValue, path, null, null).toURL());
        } catch (MalformedURLException | URISyntaxException e) {
            getLogger().error("The malformed URL has occurred or string could not be parsed as a URI reference.");
            throw new RuntimeException(e);
        }
    }

    public static String createUrl(int portValue, String path) {
        try {
            return String.valueOf(new URI(CONFIG_DATA_FILE.getValue(PROTOCOL_KEY).toString(), null, CONFIG_DATA_FILE.getValue(HOST_KEY).toString(), portValue, path, null, null).toURL());
        } catch (MalformedURLException | URISyntaxException e) {
            getLogger().error("The malformed URL has occurred or string could not be parsed as a URI reference.");
            throw new RuntimeException(e);
        }
    }
}