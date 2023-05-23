package forms.pages;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class AddNewProjectPage extends Form {
    private ITextBox projectNameTextBox = getElementFactory().getTextBox(By.xpath("//input[contains(@type,'text')]"), "Enter project name text box");
    private IButton saveProjectButton = getElementFactory().getButton(By.xpath("//button[@type='submit']"), "\"Save project\" button");
    private ILabel successLabel = getElementFactory().getLabel(By.xpath("//div[contains(@class,'success')]"), "Success Label");

    public AddNewProjectPage() {
        super(By.xpath("//button[@type='submit']"), "\"Add new project\" window");
    }

    public void fillProjectNameTextBox(String randomName) {
        projectNameTextBox.sendKeys(randomName);
    }

    public void clickOnSaveProjectButton() {
        saveProjectButton.click();
    }

    public String getSuccessText() {
        return successLabel.getText();
    }
}