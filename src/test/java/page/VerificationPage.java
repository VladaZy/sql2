package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;


public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification'] .notification__content");


    public void verify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();

    }
    public DashboardPage validVerify(String verificationCode) {
        verify(verificationCode);
        return new DashboardPage();

    }
    public void verificationPageVis() {
        codeField.shouldBe(visible);
    }

    public void errorCode () {
        errorNotification.shouldBe(visible);
        errorNotification.shouldHave(Condition.text("Неверно указан код! Попробуйте ещё раз"));

    }

}