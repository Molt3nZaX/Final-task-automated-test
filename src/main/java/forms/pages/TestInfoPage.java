package forms.pages;

import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.IElement;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import forms.ConfirmModalWindow;
import org.openqa.selenium.By;

public class TestInfoPage extends Form {
    private final IElement attachmentsArea = getElementFactory().getLabel(By.xpath("//div[contains(@class,'col')]"), "Attachments area");
    private final ILabel logsText = attachmentsArea.findChildElement(By.xpath("//td"), "Text of the log file", ElementType.LABEL);
    private final ILink screenLink = logsText.findChildElement(By.xpath("//a"), "Screenshot link", ElementType.LINK);
    private final IElement failReasonInfoArea = getElementFactory().getLabel(By.xpath("//div[contains(@class,'fail-reason')]"), "\"Fail reason info\" block");
    private final IButton bugButton = failReasonInfoArea.findChildElement(By.xpath("//li[@data-option-array-index='2']"), "Drop-down list's \"Bug\" button", ElementType.BUTTON);
    private final IButton dropDownList = failReasonInfoArea.findChildElement(By.xpath("//div[contains(@class,'chosen')]"), "Drop-down list", ElementType.BUTTON);
    private final ILabel dropDownListsLabel = dropDownList.findChildElement(By.xpath("//span"), "Drop-down list's label", ElementType.LABEL);
    private final IElement currentFailReasonField = failReasonInfoArea.findChildElement(By.xpath("//h4[text()='Current fail reason']"), "\"Current fail reason\" field", ElementType.LABEL);
    private final ILabel currentFailReasonComment = failReasonInfoArea.findChildElement(By.xpath("//p[contains(text(),'Comment')]"), "Comment from \"Current fail reason\" field", ElementType.LABEL);
    private ConfirmModalWindow confirmModalWindow = new ConfirmModalWindow();

    public ConfirmModalWindow getConfirmModalWindow() {
        return confirmModalWindow;
    }

    public TestInfoPage() {
        super(By.xpath("//div[contains(@class,'heading')]"), "Test info page");
    }

    public String getScreenshotLink() {
        return screenLink.getHref();
    }

    public String getLogsText() {
        return logsText.getText();
    }

    public String getDropDownListsText() {
        return dropDownListsLabel.getText();
    }

    public String getCurrentFailReasonComment() {
        return currentFailReasonComment.getText();
    }

    public void clickOnDropDownList() {
        dropDownList.click();
    }

    public void clickOnBugButton() {
        bugButton.state().waitForEnabled();
        bugButton.click();
    }

    public boolean currentFailReasonFieldIsDisplayed() {
        return currentFailReasonField.state().waitForDisplayed();
    }
}