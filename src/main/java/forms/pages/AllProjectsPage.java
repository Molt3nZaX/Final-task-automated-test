package forms.pages;

import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.IElement;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

import static constants.JsonKeys.EQUAL_KEY;
import static constants.SettingsFiles.TEST_DATA_FILE;

public class AllProjectsPage extends Form {
    private IElement projectsPanel = getElementFactory().getLabel(By.xpath("//div[contains(@class,'panel')]"), "Projects panel");
    private IButton addButton = projectsPanel.findChildElement(By.xpath("//a[contains(@href,'add')]"), "\"+Add\" project button", ElementType.BUTTON);
    private ILabel version = getElementFactory().getLabel(By.xpath("//footer//span"), "Version text");

    public AllProjectsPage() {
        super(By.xpath("//a[contains(@href,'projects')]"), "Projects page");
    }

    public void clickOnAddButton() {
        addButton.click();
    }

    public String getVariantNumber() {
        return version.getText();
    }

    public void clickOnProjectButton(String projectName) {
        IElement projectButton = getProjectButton(projectName);
        projectButton.state().waitForClickable();
        projectButton.click();
    }

    public String getProjectId(String projectName) {
        return getProjectButton(projectName).getHref().split(TEST_DATA_FILE.getValue(EQUAL_KEY).toString())[1];
    }

    private ILink getProjectButton(String projectName) {
        return projectsPanel.findChildElement(By.xpath(String.format("//a[text()='%s']", projectName)), String.format("%s project button", projectName), ElementType.LINK);
    }
}