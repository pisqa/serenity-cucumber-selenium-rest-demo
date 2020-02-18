package demo.stepdefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import demo.boards.Templates;
import demo.login.TrelloLogin;
import demo.navigation.NavigateTo;
import net.thucydides.core.annotations.Steps;

public class TrelloBoardStepDefinitions {

    @Steps NavigateTo navigateTo;
    @Steps TrelloLogin trelloLogin;
    @Steps Templates templates;

    @Given("I am on the trello login page")
    public void i_am_on_the_trello_login_page() {
        navigateTo.theTrelloLoginPage();
    }

    @And("I log into trello")
    public void i_log_into_trello() {
        trelloLogin.login();
    }

    @When("I search for template {string}")
    public void i_search_for_template(String template) {
        templates.searchTemplate(template);
    }

    @And("I create new board {string} from the template")
    public void i_create_board_from_template(String board) {
        templates.createBoardFromTemplate(board);
    }

    @Then("the board should contain the lists {string}")
    public void the_board_should_contain_the_lists(String lists) {
        templates.checkBoardLists(lists);
    }

    @And("the activity log should contain the entry \"copied this board from {string}\"")
    public void the_activity_log_should_contain_the_entry(String entryText) {
        templates.checkActivityLog(entryText);
    }
}
