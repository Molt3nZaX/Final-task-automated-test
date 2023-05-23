package exam.utils;

import entities.ProjectsTestObjects;
import kong.unirest.HttpResponse;
import org.openqa.selenium.Cookie;

import java.util.HashMap;
import java.util.List;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static aquality.selenium.browser.AqualityServices.getLogger;
import static constants.JsonKeys.*;
import static constants.RequestParametersKeys.*;
import static constants.SettingsFiles.*;
import static exam.utils.ApiUtils.postRequest;
import static exam.utils.UrlCreatorUtils.createUrl;

public class ApiAppRequestsUtils {
    public static String getTokenResponse(String variant) {
        getLogger().info("Get token response");
        String apiMethod = createApiMethod(API_METHODS_FILE.getValue(TOKEN_PART_OF_METHOD_KEY).toString(),
                API_METHODS_FILE.getValue(GET_PART_OF_METHOD_KEY).toString());
        return getStringHttpResponse(apiMethod, VARIANT_PARAMETER, variant).getBody();
    }

    public static void addCookie(String cookie) {
        getLogger().info("Add cookie");
        getBrowser().getDriver().manage().addCookie(new Cookie(TEST_DATA_FILE.getValue(TOKEN_KEY).toString(), cookie));
    }

    public static List<ProjectsTestObjects> getProjectsTestsList(String projectId) {
        getLogger().info("Get project test list");
        String apiMethod = createApiMethod(API_METHODS_FILE.getValue(TEST_PART_OF_METHOD_KEY).toString(),
                API_METHODS_FILE.getValue(GET_PART_OF_METHOD_KEY).toString(),
                API_METHODS_FILE.getValue(JSON_PART_OF_METHOD_KEY).toString());
        HttpResponse<String> request = getStringHttpResponse(apiMethod, PROJECT_ID_PARAMETER, projectId);
        while (!String.valueOf(request.getBody().charAt(0)).equals(TEST_DATA_FILE.getValue(SQUARE_BRACKET_KEY))) {
            request = getStringHttpResponse(apiMethod, PROJECT_ID_PARAMETER, projectId);
        }
        return JsonUtils.readListFromString(request.getBody(), ProjectsTestObjects.class);
    }

    public static String createNewTestRequest(String projectName) {
        getLogger().info("Create new test request");
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put(SID_PARAMETER, RandomTestDataUtils.generateRandomText());
        parameters.put(PROJECT_NAME_PARAMETER, projectName);
        parameters.put(TEST_NAME_PARAMETER, TEST_DATA_FILE.getValue(TEST_NAME_KEY));
        parameters.put(METHOD_NAME_PARAMETER, TEST_DATA_FILE.getValue(METHOD_NAME_KEY));
        parameters.put(ENV_PARAMETER, TEST_DATA_FILE.getValue(HOST_NAME_KEY));

        String method = createApiMethod(API_METHODS_FILE.getValue(TEST_PART_OF_METHOD_KEY).toString(),
                API_METHODS_FILE.getValue(PUT_PART_OF_METHOD_KEY).toString());
        return performMultipartBodyRequest(method, parameters).getBody();
    }

    public static void sendLogRequest(String testId, String logFile) {
        getLogger().info("Send log file in the test");
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put(TEST_ID_PARAMETER, testId);
        parameters.put(CONTENT_PARAMETER, logFile);

        String method = createApiMethod(API_METHODS_FILE.getValue(TEST_PART_OF_METHOD_KEY).toString(),
                API_METHODS_FILE.getValue(PUT_PART_OF_METHOD_KEY).toString(),
                API_METHODS_FILE.getValue(LOG_PART_OF_METHOD_KEY).toString());
        performMultipartBodyRequest(method, parameters);
    }

    public static void sendAttachmentsRequest(String testId, String contentBase64, String contentType) {
        getLogger().info("Send attachments in the test");
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put(TEST_ID_PARAMETER, testId);
        parameters.put(CONTENT_PARAMETER, contentBase64);
        parameters.put(CONTENT_TYPE_PARAMETER, contentType);

        String method = createApiMethod(API_METHODS_FILE.getValue(TEST_PART_OF_METHOD_KEY).toString(),
                API_METHODS_FILE.getValue(PUT_PART_OF_METHOD_KEY).toString(),
                API_METHODS_FILE.getValue(ATTACHMENT_PART_OF_METHOD_KEY).toString());
        performMultipartBodyRequest(method, parameters);
    }

    private static HttpResponse<String> getStringHttpResponse(String apiMethod, String fieldKey, String value) {
        return postRequest(apiUrl() + apiMethod)
                .queryString(fieldKey, value)
                .asString();
    }

    private static HttpResponse<String> performMultipartBodyRequest(String apiMethod, HashMap<String, Object> parameters) {
        return postRequest(apiUrl() + apiMethod)
                .fields(parameters)
                .asString();
    }

    private static String apiUrl() {
        return createUrl(Integer.parseInt(CONFIG_DATA_FILE.getValue(PORT_KEY).toString()), CONFIG_DATA_FILE.getValue(API_PATH_KEY).toString());
    }

    private static String createApiMethod(String apiMethodPart1, String apiMethodPart2) {
        return String.format("/%s/%s", apiMethodPart1, apiMethodPart2);
    }

    private static String createApiMethod(String apiMethodPart1, String apiMethodPart2, String apiMethodPart3) {
        return String.format("/%s/%s/%s", apiMethodPart1, apiMethodPart2, apiMethodPart3);
    }
}