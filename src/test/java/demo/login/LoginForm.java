package demo.login;

import org.openqa.selenium.By;

class LoginForm {
	static By COOKIES_CONSENT_BUTTON = By.className("gdpr-cookie-consent-button");
    static By EMAIL_FIELD = By.id("user");
	static By PASSWORD_FIELD = By.id("password");
	static By LOGIN_BUTTON = By.id("login");
}
