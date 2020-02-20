package demo.boards;

import org.openqa.selenium.By;

class TemplateForm {

	static By TEMPLATES_BUTTON = By.xpath("//li[@data-test-id='templates']");
	static By TEMPLATES_SEARCH_FIELD = By.xpath("//input[@id='react-select-2-input']");
	static By TEMPLATES_VIEW_BUTTON = By.xpath("//a[text()='View Template']");
	static By CREATE_BOARD_FROM_TEMPLATE_BUTTON = By.xpath("//a[@title ='Create Board from Template']");
	static By NEW_BOARD_TITLE_FIELD = By.xpath("//input[@id ='boardNewTitle']");
	static By NEW_BOARD_KEEP_CARDS_CHECKBOX = By.xpath("//label[@for ='idKeepCards']");
	static By NEW_BOARD_SUBMIT_BUTTON = By.xpath("//input[@type ='submit']");
	static By HOME_BUTTON = By.xpath("//a[@aria-label ='Back to Home']");
	static By ACTIVITY_LOG_ENTRY = By.xpath("//div[@class='phenom-desc' and text()=' copied this board from ']/a"  );

}
