package exam;

import entities.ProjectsTestObjects;
import exam.utils.RandomTestDataUtils;
import forms.pages.AddNewProjectPage;
import forms.pages.AllProjectsPage;
import forms.pages.AnyProjectPage;
import forms.pages.TestInfoPage;
import org.openqa.selenium.OutputType;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static aquality.selenium.browser.AqualityServices.getBrowser;
import static aquality.selenium.browser.AqualityServices.getLogger;
import static constants.FilesPaths.PATH_TO_DYNAMIC_TESTS_DATA_FILE;
import static constants.FilesPaths.PATH_TO_LOGS_FILE;
import static constants.JsonKeys.*;
import static constants.SettingsFiles.TEST_DATA_FILE;
import static exam.utils.ApiAppRequestsUtils.*;
import static exam.utils.FileUtils.*;
import static org.testng.Assert.*;

public class ExamTest extends BaseTest {
    @Test(testName = "UI + API test")
    public void firstTest() {
        getLogger().info("Step 1: Get token according to variant number (API).");
        String variant = TEST_DATA_FILE.getValue(VARIANT_KEY).toString();
        String tokenResponse = getTokenResponse(variant);

        getLogger().info("Step 2: Go to site. Get authorized. Use the cookie to pass the token generated in step 1. Refresh the page.");
        addCookie(tokenResponse);
        getBrowser().refresh();
        AllProjectsPage projectsPage = new AllProjectsPage();
        assertTrue(projectsPage.state().waitForDisplayed(), "Projects page is nor opened");
        assertTrue(projectsPage.getVariantNumber().contains(variant), "Version is not correct");

        getLogger().info("Step 3: Go to the \"Nexage\" project page. Get a list of tests in JSON/XML format (API).");
        String projectName = TEST_DATA_FILE.getValue(PROJECT_NAME_KEY).toString();
        String projectId = projectsPage.getProjectId(projectName);
        projectsPage.clickOnProjectButton(projectName);

        AnyProjectPage projectPage = new AnyProjectPage();
        projectPage.state().waitForDisplayed();
        List<ProjectsTestObjects> apiResponseTestsList = getProjectsTestsList(projectId);
        List<ProjectsTestObjects> firstPageTestsList = projectPage.getTestsTableForm().getTableObjectList();
        List<ProjectsTestObjects> sortedList = new ArrayList<>(firstPageTestsList);
        sortedList.sort(Comparator.comparing(ProjectsTestObjects::getStartTime).reversed());
        assertEquals(firstPageTestsList, sortedList, "Tests on the first page are not sorted by date in descending order");
        assertTrue(apiResponseTestsList.containsAll(firstPageTestsList), "The tests on the first page don't match those returned by the API request.");

        getLogger().info("Step 4: Return to the previous page in the browser. Click on the \"+Add\" button. Enter a project name and save.");
        getBrowser().goBack();
        projectsPage.state().waitForDisplayed();
        projectsPage.clickOnAddButton();
        getBrowser().tabs().switchToLastTab();
        AddNewProjectPage addNewProjectPage = new AddNewProjectPage();
        addNewProjectPage.state().waitForEnabled();

        String newProjectsName = RandomTestDataUtils.generateRandomText();
        writeTextToFile(newProjectsName, PATH_TO_DYNAMIC_TESTS_DATA_FILE);
        addNewProjectPage.fillProjectNameTextBox(newProjectsName);
        addNewProjectPage.clickOnSaveProjectButton();
        assertTrue(addNewProjectPage.getSuccessText().contains(TEST_DATA_FILE.getValue(SAVED_KEY).toString()),
                "The message about the successful saving of the project did not appear");

        getLogger().info("Step 4.1: Call the js-method closePopUp() to close the window for adding a project. Refresh the page.");
        getBrowser().tabs().closeTab();

        getLogger().info("Step 4.2: The Add Project window is closed. After refreshing the page, the project appeared in the list.");
        getBrowser().tabs().switchToLastTab();
        getBrowser().refresh();

        getLogger().info("Step 5: Go to the page of the created project. Add a test with a log and a screenshot of the current page (API).");
        projectsPage.clickOnProjectButton(newProjectsName);
        String testId = createNewTestRequest(newProjectsName);
        addTextToFile(testId, PATH_TO_DYNAMIC_TESTS_DATA_FILE);
        sendLogRequest(testId, readContent(PATH_TO_LOGS_FILE));
        sendAttachmentsRequest(testId, getBrowser().getDriver().getScreenshotAs(OutputType.BASE64), TEST_DATA_FILE.getValue(IMAGE_TYPE_KEY).toString());

        assertTrue(projectPage.getTestsTableForm().newAddedTestIsDisplayed(testId), "New test record is not displayed");

        getLogger().info("Step 5.1: Check that the log and screen are added to the UI.");
        projectPage.getTestsTableForm().clickOnAddedTest(testId);
        TestInfoPage testsInfoPage = new TestInfoPage();
        testsInfoPage.state().waitForDisplayed();
        assertFalse(testsInfoPage.getLogsText().isEmpty(), "Log is not added");
        assertFalse(testsInfoPage.getScreenshotLink().isEmpty(), "Screenshot is not added");
    }

    @Test(testName = "UI test")
    public void secondTest() {
        getLogger().info("Step 1: Get token according to variant number (API).");
        String variant = TEST_DATA_FILE.getValue(VARIANT_KEY).toString();
        String tokenResponse = getTokenResponse(variant);

        getLogger().info("Step 2: Go to site. Get authorized. Use the cookie to pass the token generated in step 1. Refresh the page.");
        addCookie(tokenResponse);
        getBrowser().refresh();
        AllProjectsPage projectsPage = new AllProjectsPage();
        assertTrue(projectsPage.state().waitForDisplayed(), "Projects page is nor opened");
        assertTrue(projectsPage.getVariantNumber().contains(variant), "Version is not correct");

        getLogger().info("Step 3: Go to the page of the created project.");
        String textFromDynamicTestData = readContent(String.valueOf(PATH_TO_DYNAMIC_TESTS_DATA_FILE));
        projectsPage.clickOnProjectButton(textFromDynamicTestData.split(TEST_DATA_FILE.getValue(COLON_KEY).toString())[0]);
        AnyProjectPage projectPage = new AnyProjectPage();
        assertTrue(projectPage.state().waitForDisplayed(), "New added page is not displayed");

        getLogger().info("Step 4: Go to the page of the created test.");
        projectPage.getTestsTableForm().clickOnAddedTest(textFromDynamicTestData.split(TEST_DATA_FILE.getValue(COLON_KEY).toString())[1]);
        TestInfoPage testsInfoPage = new TestInfoPage();
        assertTrue(testsInfoPage.state().waitForDisplayed(), "New added test is not displayed");

        getLogger().info("Step 5: Change setFailReason to \"Bug\" - the value should change to \"Bug\"" +
                "Поле \"Current fail reason\" field should appear. The comment should match the comment from the Confirm window");
        testsInfoPage.clickOnDropDownList();
        testsInfoPage.clickOnBugButton();
        String commentForConfirmWindow = RandomTestDataUtils.generateRandomText();
        testsInfoPage.getConfirmModalWindow().state().waitForDisplayed();
        testsInfoPage.getConfirmModalWindow().fillInConfirmsWindowTextBox(commentForConfirmWindow);
        testsInfoPage.getConfirmModalWindow().clickOnYesButton();
        testsInfoPage.getConfirmModalWindow().clickOnCloseButton();
        assertEquals(testsInfoPage.getDropDownListsText(), TEST_DATA_FILE.getValue(DROPDOWN_LIST_CHOICE_KEY).toString(),
                "Drop-down list value didn't change to \"Bug\"");
        assertTrue(testsInfoPage.currentFailReasonFieldIsDisplayed(),
                "\"Current fail reason\" field is not displayed");
        assertEquals(testsInfoPage.getCurrentFailReasonComment().split(TEST_DATA_FILE.getValue(COLON_KEY).toString())[1].trim(), commentForConfirmWindow,
                "The comment doesn't match the comment from the Confirm window");
    }
}