package forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class ConfirmModalWindow extends Form {
    private ITextBox confirmWindowTextbox = getElementFactory().getTextBox(By.id("failReasonComment"), "Confirm window's text box");
    private IButton yesButton = getElementFactory().getButton(By.xpath("//button[contains(@class,'success')]"), "Confirm window's \"YES\" button");
    private IButton closeButton = getElementFactory().getButton(By.xpath("//button[contains(@class,'btn')]"), "Confirm window's \"Close\" button");

    public ConfirmModalWindow() {
        super(By.xpath("//div[@class='messi']"), "Confirm window form");
    }

    public void fillInConfirmsWindowTextBox(String comment) {
        confirmWindowTextbox.sendKeys(comment);
    }

    public void clickOnYesButton() {
        yesButton.click();
    }

    public void clickOnCloseButton() {
        closeButton.state().waitForEnabled();
        closeButton.click();
    }
}