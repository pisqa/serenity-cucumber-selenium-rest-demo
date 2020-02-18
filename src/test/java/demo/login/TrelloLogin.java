package demo.login;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.core.steps.UIInteractionSteps;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

public class TrelloLogin extends UIInteractionSteps {

    @Step("Trello login")
    public void login() {
        EnvironmentVariables variables;
        String trelloEmail, trelloPasssword;

        //check if cookies consent banner appears
        if ($(LoginForm.COOKIES_CONSENT_BUTTON).isPresent()) {
            $(LoginForm.COOKIES_CONSENT_BUTTON).click();
        }

        variables = SystemEnvironmentVariables.createEnvironmentVariables();
        trelloEmail = EnvironmentSpecificConfiguration.from(variables).getProperty("trello.login.email");
        trelloPasssword = EnvironmentSpecificConfiguration.from(variables).getProperty("trello.login.password");

        $(LoginForm.EMAIL_FIELD).clear();
        $(LoginForm.EMAIL_FIELD).type(trelloEmail);
        $(LoginForm.PASSWORD_FIELD).clear();
        $(LoginForm.PASSWORD_FIELD).type(trelloPasssword);
        $(LoginForm.LOGIN_BUTTON).click();
    }
}
