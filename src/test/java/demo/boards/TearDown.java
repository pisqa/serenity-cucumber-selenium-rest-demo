package demo.boards;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class TearDown {

    @Step("Tear Down - delete all boards")
    public void tearDown() {
        String trelloApiToken, trelloApiKey;
        EnvironmentVariables variables;
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

        for (String boardId : boardIds) {
            SerenityRest.given().contentType("application/json").and()
                    .queryParam("key", trelloApiKey)
                    .queryParam("token", trelloApiToken)
                    .when().delete("https://api.trello.com/1/boards/" + boardId)
                    .then().statusCode(200);
        }

    }
}
