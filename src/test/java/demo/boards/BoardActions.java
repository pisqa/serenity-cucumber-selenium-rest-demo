package demo.boards;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardActions {

	String trelloApiToken, trelloApiKey;
	String boardId;
	String listId;
	String cardId;
	String checklistId;
	String checklistItemId;
	String attachmentId;
	Response getActionsResponse;

	@Step("Create board")
	public void createBoard(String board) {

		EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
		trelloApiToken = EnvironmentSpecificConfiguration.from(variables).getProperty("trello.api.token");
		trelloApiKey = EnvironmentSpecificConfiguration.from(variables).getProperty("trello.api.key");

		boardId = SerenityRest.given().contentType("application/json").and()
				.queryParam("name", board)
				.queryParam("prefs_permissionLevel", "private")
				.queryParam("prefs_selfJoin", "false")
				.queryParam("defaultLists", "false")
				.queryParam("key", trelloApiKey)
				.queryParam("token", trelloApiToken)
				.when().post("https://api.trello.com/1/boards")
				.then().statusCode(200)
				.extract().response().jsonPath().get("id");
	}

	@Step("Create list")
	public void createList(String listName) {

		listId = SerenityRest.given().contentType("application/json").and()
				.queryParam("name", listName)
				.queryParam("closed", "false")
				.queryParam("idBoard", boardId)
				.queryParam("key", trelloApiKey)
				.queryParam("token", trelloApiToken)
				.when().post("https://api.trello.com/1/lists")
				.then().statusCode(200)
				.extract().response().jsonPath().get("id");
	}

	@Step("Create card")
	public void createCard(String cardName) {

		cardId = SerenityRest.given().contentType("application/json").and()
				.queryParam("name", cardName)
				.queryParam("closed", "false")
				.queryParam("idBoard", boardId)
				.queryParam("idList", listId)
				.queryParam("key", trelloApiKey)
				.queryParam("token", trelloApiToken)
				.when().post("https://api.trello.com/1/cards")
				.then().statusCode(200)
				.extract().response().jsonPath().get("id");
	}

	@Step("Add comment to card")
	public void addCommentToCard(String comment) {
		SerenityRest.given().contentType("application/json").and()
				.queryParam("text", comment)
				.queryParam("key", trelloApiKey)
				.queryParam("token", trelloApiToken)
				.when().post("https://api.trello.com/1/cards/" + cardId + "/actions/comments")
				.then().statusCode(200);
	}

	@Step("Add checklist")
	public void addChecklistToCard(String checklist, String item) {

		//first create the checklist
		checklistId = SerenityRest.given().contentType("application/json").and()
				.queryParam("idBoard", boardId)
				.queryParam("idCard", cardId)
				.queryParam("name", checklist)
				.queryParam("key", trelloApiKey)
				.queryParam("token", trelloApiToken)
				.when().post("https://api.trello.com/1/checklists" )
				.then().statusCode(200)
				.extract().response().jsonPath().get("id");

		//then add the checklist item
		checklistItemId = SerenityRest.given().contentType("application/json").and()
				.queryParam("name", item)
				.queryParam("key", trelloApiKey)
				.queryParam("token", trelloApiToken)
				.when().post("https://api.trello.com/1/cards/" + cardId +
							"/checklist/" + checklistId + "/checkItem")
				.then().statusCode(200)
				.extract().response().jsonPath().get("id");
	}


	@Step("Check item")
	public void checkItem() {

		SerenityRest.given().contentType("application/json").and()
				.queryParam("state", "complete")
				.queryParam("key", trelloApiKey)
				.queryParam("token", trelloApiToken)
				.when().put("https://api.trello.com/1/cards/" + cardId +
						"/checklist/" + checklistId + "/checkItem/" + checklistItemId)
				.then().statusCode(200);
	}

	@Step("Remove checklist")
	public void removeChecklist() {

		SerenityRest.given().contentType("application/json").and()
				.queryParam("key", trelloApiKey)
				.queryParam("token", trelloApiToken)
				.when().delete("https://api.trello.com/1/checklists/" + checklistId)
				.then().statusCode(200);
	}

	@Step("Add attachment")
	public void addAttachment(String url, String name) {

		attachmentId = SerenityRest.given().contentType("application/json").and()
				.queryParam("url", url)
				.queryParam("name", name)
				.queryParam("key", trelloApiKey)
				.queryParam("token", trelloApiToken)
				.when().post("https://api.trello.com/1/cards/" + cardId + "/attachments")
				.then().statusCode(200)
				.extract().response().jsonPath().get("id");
	}

	@Step("Remove attachment")
	public void removeAttachment() {

		SerenityRest.given().contentType("application/json").and()
				.queryParam("key", trelloApiKey)
				.queryParam("token", trelloApiToken)
				.when().delete("https://api.trello.com/1/cards/" +
						cardId + "/attachments/" + attachmentId)
				.then().statusCode(200);
	}

	@Step("Close card")
	public void closeCard() {

		SerenityRest.given().contentType("application/json").and()
				.queryParam("closed", "true")
				.queryParam("key", trelloApiKey)
				.queryParam("token", trelloApiToken)
				.when().put("https://api.trello.com/1/cards/" + cardId )
				.then().statusCode(200);
	}

	@Step("Get board actions")
	public void getActions() {

		getActionsResponse = SerenityRest.given().contentType("application/json").and()
				.queryParam("idBoard", boardId)
				.queryParam("idCard", cardId)
				.queryParam("key", trelloApiKey)
				.queryParam("token", trelloApiToken)
				.when().get("https://api.trello.com/1/boards/" + boardId + "/actions")
				.then().statusCode(200)
				.extract().response();
	}

	@Step("Get actions count")
	public void checkActionsCount(int expectedCount) {

		JsonPath jsonPathEvaluator = getActionsResponse.jsonPath();
		List<Object> values = jsonPathEvaluator.getList("");
		assertThat(values.size()).isEqualTo(expectedCount);
	}

	@Step("Check actions details")
	public void checkActionsDetails( List<Map<String, String>> actions) {

		String type, data, expectedValue, actualValue, getStr;
		JsonPath jsonPathEvaluator = getActionsResponse.jsonPath();

		for (Map<String, String> action : actions) {
			type = action.get("type");
			data = action.get("data");
			expectedValue = action.get("expectedValue");
			getStr = "find{node -> node.type == '" + type + "'}." + data;

			//special case for booleanS
			if (jsonPathEvaluator.get(getStr) instanceof Boolean) {
				if (jsonPathEvaluator.get(getStr)) {
					actualValue = "true";
				} else {
					actualValue = "false";
				}

			} else {
				actualValue = jsonPathEvaluator.get(getStr);
			}
			assertThat(actualValue).isEqualTo(expectedValue);
		}
	}
}
