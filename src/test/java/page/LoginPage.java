package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement passwordField = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement errorMessage = $("[data-test-id='error-notification'] .notification__content");


    public VerificationPage validLogin(DataHelper.AuthInfo authInfo) {
        loginField.setValue(authInfo.getLogin());
        passwordField.setValue(authInfo.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    public void cleanForm() {
        loginField.doubleClick().sendKeys(Keys.BACK_SPACE);
        passwordField.doubleClick().sendKeys(Keys.BACK_SPACE);
    }

    public void getErrorMessage(String errorText) {
        errorMessage.shouldHave(Condition.exactText(errorText),
                Duration.ofSeconds(10)).shouldBe(Condition.visible);
    }
}