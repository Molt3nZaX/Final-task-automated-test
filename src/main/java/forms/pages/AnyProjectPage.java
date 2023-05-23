package forms.pages;

import aquality.selenium.forms.Form;
import forms.TestsTableForm;
import org.openqa.selenium.By;

public class AnyProjectPage extends Form {
    private TestsTableForm testsTableForm = new TestsTableForm();

    public AnyProjectPage() {
        super(By.xpath("//button[contains(@data-target,'add')]"), "Project page");
    }

    public TestsTableForm getTestsTableForm() {
        return testsTableForm;
    }
}