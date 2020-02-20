package demo.boards;

import com.google.common.base.CharMatcher;
import net.serenitybdd.core.steps.UIInteractionSteps;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

public class Templates extends UIInteractionSteps {

    String selectedTemplate;
    WebDriver driver;
    WebDriverWait wait;

    @Step("Search for template")
    public void searchTemplate(String template) {

        selectedTemplate = template.trim();
        $(TemplateForm.TEMPLATES_BUTTON).click();

        //wait for the templates page to load
        driver = getDriver();
        wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(TemplateForm.TEMPLATES_SEARCH_FIELD));

        $(TemplateForm.TEMPLATES_SEARCH_FIELD).clear();
        $(TemplateForm.TEMPLATES_SEARCH_FIELD).type(template + "\n");

        //verify specified template loads
        //url is lowercase and any space replaced with hyphen
        wait.until(ExpectedConditions.urlContains(selectedTemplate.toLowerCase().replace(' ', '-')));
    }

    @Step("Create board from template")
    public void createBoardFromTemplate(String board) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(TemplateForm.TEMPLATES_VIEW_BUTTON));
        $(TemplateForm.TEMPLATES_VIEW_BUTTON).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(TemplateForm.CREATE_BOARD_FROM_TEMPLATE_BUTTON));
        $(TemplateForm.CREATE_BOARD_FROM_TEMPLATE_BUTTON).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(TemplateForm.NEW_BOARD_TITLE_FIELD));
        $(TemplateForm.NEW_BOARD_TITLE_FIELD).type(board);
        $(TemplateForm.NEW_BOARD_KEEP_CARDS_CHECKBOX).click();
        $(TemplateForm.NEW_BOARD_SUBMIT_BUTTON).click();

        //verify new board loads
        wait.until(ExpectedConditions.urlContains(board.toLowerCase()));
    }

    @Step("Check board lists")
    public void checkBoardLists(String lists) {

        String[] listArray = lists.split(",");
        List<WebElement> elements = driver.findElements(net.serenitybdd.core.annotations.findby.By.xpath(
                "//div[@class='board-main-content']" +
                "//div[@class='js-list list-wrapper']" +
                "//textarea[@class='list-header-name mod-list-name js-list-name-input']"));
        assertThat(elements.size()).isEqualTo(listArray.length);

        for (int i = 0; i < elements.size(); i++) {
            //strip any non-ASCII characters from the actual list name e.g. in Kanban/Done
            assertThat(CharMatcher.ascii().retainFrom(elements.get(i).getText()).trim()).
                    isEqualTo(listArray[i].trim());
        }
    }

    @Step("Check activity log")
    public void checkActivityLog(String entryText) {

        //if menu not open, then open it
        WebElement showMenuBtn = driver.findElement(net.serenitybdd.core.annotations.findby.By.xpath(
                "//a[@class='board-header-btn mod-show-menu js-show-sidebar']"));
        if (showMenuBtn.isDisplayed()) {
            showMenuBtn.click();
        }

        String actualText = find(TemplateForm.ACTIVITY_LOG_ENTRY).getText();
        assertThat(actualText).isEqualTo(entryText);
    }
}
