package demo.boards;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class SetUpAndTearDown {

    String trelloApiToken, trelloApiKey;
    EnvironmentVariables variables;
    Boolean setupOK = false;

    @Step("Set up - check there are no boards present")
    public void setUp() {
        ArrayList<String> boardIds;

        variables = SystemEnvironmentVariables.createEnvironmentVariables();
        trelloApiToken = EnvironmentSpecificConfiguration.from(variables).getProperty("trello.api.token");
        trelloApiKey = EnvironmentSpecificConfiguration.from(variables).getProperty("trello.api.key");

        boardIds = SerenityRest.given().contentType("application/json").and()
                .queryParam("key", trelloApiKey)
                .queryParam("token", trelloApiToken)
                .when().get("https://api.trello.com/1/members/me/boards")
                .then().statusCode(200)
                .extract().response().jsonPath().get("id");

        if (boardIds.size() != 0) {
            boardsError(boardIds.size());
        } else {
            setupOK = true;
        }
    }

    @Step("There are boards present - tests will only run if no boards are present")
    public void boardsError(int boardsCount) {
        assertThat(boardsCount).isEqualTo(0);
    }

    @Step("Tear Down - delete all boards")
    public void tearDown() {
        ArrayList<String> boardIds;

        //@After always runs, even if @Before fails (boards detected),
        //so check manually if the setup was ok (no boards detected)
        if (setupOK) {
            boardIds = SerenityRest.given().contentType("application/json").and()
                    .queryParam("key", trelloApiKey)
                    .queryParam("token", trelloApiToken)
                    .when().get("https://api.trello.com/1/members/me/boards")
                    .then().statusCode(200)
                    .extract().response().jsonPath().get("id");

            for (String boardId : boardIds) {
                SerenityRest.given().contentType("application/json").and()
                        .queryParam("key", trelloApiKey)
                        .queryParam("token", trelloApiToken)
                        .when().delete("https://api.trello.com/1/boards/" + boardId)
                        .then().statusCode(200);
            }
        } else {
            org.assertj.core.api.Assertions.fail("Setup failed - don't delete");
        }
    }
}
