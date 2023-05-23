package forms;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import entities.ProjectsTestObjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class TestsTableForm extends Form {
    private final int GET_TABLE_OBJECTS_LIST_METHOD_INDEX_0 = 0;
    private final int GET_TABLE_OBJECTS_LIST_METHOD_INDEX_1 = 1;
    private final int GET_TABLE_OBJECTS_LIST_METHOD_INDEX_2 = 2;
    private final int GET_TABLE_OBJECTS_LIST_METHOD_INDEX_3 = 3;
    private final int GET_TABLE_OBJECTS_LIST_METHOD_INDEX_4 = 4;
    private final int GET_TABLE_OBJECTS_LIST_METHOD_INDEX_5 = 5;

    public TestsTableForm() {
        super(By.xpath("//table[@class='table']"), "Table with tests");
    }

    public List<ProjectsTestObjects> getTableObjectList() {
        AqualityServices.getLogger().info("Get tests list from first page of project");
        List<ProjectsTestObjects> originalList = new ArrayList<>();
        List<WebElement> trList = AqualityServices.getBrowser().getDriver().findElements(By.xpath("//table[@class='table']//tbody//tr"));

        trList.remove(GET_TABLE_OBJECTS_LIST_METHOD_INDEX_0);
        for (WebElement tableRow : trList) {
            List<WebElement> thList = tableRow.findElements(By.xpath(".//td"));
            ProjectsTestObjects tablePojo = new ProjectsTestObjects();
            tablePojo.setName(thList.get(GET_TABLE_OBJECTS_LIST_METHOD_INDEX_0).findElement(By.xpath(".//a")).getText());
            tablePojo.setMethod(thList.get(GET_TABLE_OBJECTS_LIST_METHOD_INDEX_1).getText());
            tablePojo.setStatus(thList.get(GET_TABLE_OBJECTS_LIST_METHOD_INDEX_2).findElement(By.xpath(".//span")).getText());
            tablePojo.setStartTime(thList.get(GET_TABLE_OBJECTS_LIST_METHOD_INDEX_3).getText());
            String endTime = thList.get(GET_TABLE_OBJECTS_LIST_METHOD_INDEX_4).getText();
            if (endTime.equals("")) {
                endTime = null;
            }
            tablePojo.setEndTime(endTime);
            tablePojo.setDuration(thList.get(GET_TABLE_OBJECTS_LIST_METHOD_INDEX_5).getText());
            originalList.add(tablePojo);
        }
        return originalList;
    }

    public boolean newAddedTestIsDisplayed(String testId) {
        return getNewTestRecordById(testId).state().waitForEnabled();
    }

    public void clickOnAddedTest(String testId) {
        getNewTestRecordById(testId).click();
    }

    private ILink getNewTestRecordById(String testId) {
        return getElementFactory().getLink(By.xpath(String.format("//table[@class='table']//a[contains(@href, 'testId=%s')]", testId)),
                String.format("Link to new test with ID=%s", testId));
    }
}