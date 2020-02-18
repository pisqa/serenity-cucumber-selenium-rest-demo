package demo.stepdefinitions;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import demo.boards.BoardActions;
import demo.boards.SetUpAndTearDown;
import io.cucumber.datatable.DataTable;
import net.thucydides.core.annotations.Steps;

public class TrelloBoardRestStepDefinitions {

    @Steps SetUpAndTearDown setUpAndTearDown;
    @Steps BoardActions boardActions;

    @Before
    public void setUp() {
        setUpAndTearDown.setUp();
    }

    @Given("I create a new board called {string}")
    public void i_create_a_new_board(String board) {
        boardActions.createBoard(board);
    }

    @And("I create a list called {string} in the new board")
    public void i_create_a_list_called_x_in_the_new_board(String listName) {
        boardActions.createList(listName);
    }

    @And("I create a card called {string} in the new list")
    public void i_create_a_card_called_x_in_the_list_called_y(String cardName) {
        boardActions.createCard(cardName);
    }

    @And("I add comment {string} to the new card")
    public void i_add_a_comment_to_the_card(String comment) {
        boardActions.addCommentToCard(comment);
    }

    @And("I add checklist {string} with item {string} to the card")
    public void i_add_checklist_to_the_card(String checklist, String item) {
        boardActions.addChecklistToCard(checklist, item);
    }

    @And("I check the checklist item")
    public void i_check_the_checklist_item() {
        boardActions.checkItem();
    }

    @And("I remove the checklist from the card")
    public void i_remove_the_checklist_from_the_card() {
        boardActions.removeChecklist();
    }

    @And("I add attachment url {string} with name {string} to the card")
    public void i_add_attachment_to_the_card(String url, String name) {
        boardActions.addAttachment(url, name);
    }

    @And("I remove the attachment from the card")
    public void i_remove_the_attachment_from_the_card() {
        boardActions.removeAttachment();
    }

    @And("I close the card")
    public void i_close_the_card() {
        boardActions.closeCard();
    }

    @When("I get the actions for the board")
    public void i_get_the_actions_for_the_board() {
        boardActions.getActions();
    }

    @Then("the total number of actions should be {int}")
    public void the_total_number_of_actions_should_be_x(int expectedCount) {
        boardActions.checkActionsCount(expectedCount);
    }

    @And("the actions should include the following details:")
    public void the_actions_should_include_the_following_details(DataTable dt) {
        boardActions.checkActionsDetails(dt.asMaps(String.class, String.class));
    }

    @After
    public void tearDown() {
        setUpAndTearDown.tearDown();
    }
}
