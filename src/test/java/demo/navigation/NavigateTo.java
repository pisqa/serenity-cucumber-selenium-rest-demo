package demo.navigation;

import net.thucydides.core.annotations.Step;

public class NavigateTo {

    TrelloLoginPage trelloLoginPage;

    @Step("Open the trello login page")
    public void theTrelloLoginPage() {
        trelloLoginPage.open();
    }
}
