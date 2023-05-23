package exam.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static aquality.selenium.browser.AqualityServices.getLogger;
import static constants.JsonKeys.COLON_KEY;
import static constants.SettingsFiles.TEST_DATA_FILE;

public class FileUtils {
    public static String readContent(String filePath) {
        getLogger().info("Reading content from the file with path: " + filePath);
        String result = null;
        try {
            File file = new File(filePath);
            Path path = file.toPath();
            byte[] bytes = Files.readAllBytes(path);
            result = new String(bytes);
        } catch (IOException e) {
            getLogger().error("Reading from the file failed");
            e.printStackTrace();
        }
        return result;
    }

    public static void writeTextToFile(String text, Path pathToFile) {
        getLogger().info("Write content to file with path: " + pathToFile);
        try {
            Files.write(pathToFile, text.getBytes());
        } catch (IOException e) {
            getLogger().error("Error writing to file");
            throw new RuntimeException(e);
        }
    }

    public static void addTextToFile(String text, Path pathToFile) {
        getLogger().info("Add content to file with path: " + pathToFile);
        try {
            Files.write(pathToFile, (TEST_DATA_FILE.getValue(COLON_KEY).toString() + text).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            getLogger().error("Error writing to file");
            throw new RuntimeException(e);
        }
    }
}