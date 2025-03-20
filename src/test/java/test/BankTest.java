package test;

import data.DataHelper;
import data.SQLHelper;
import org.junit.jupiter.api.*;

import page.LoginPage;
import page.VerificationPage;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;
import static data.SQLHelper.CleanDatabase;
import static data.SQLHelper.cleanAuthCodes;

public class BankTest {
    LoginPage loginPage;


    @AfterEach
    void tearDown() {
        cleanAuthCodes();
    }

    @AfterAll
    static void tearDownAll() {
        CleanDatabase();
    }

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test
    public void shouldMustBeSuccessfulLogin() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVis();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());

    }

    @Test
    void shouldErrorInvalideLogin() {
        var authInfo = DataHelper.getRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.getErrorMessage("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    void shouldErrorInvalidePassword() {
        var authInfo = new DataHelper.AuthInfo(DataHelper.getAuthInfo().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authInfo);
        loginPage.getErrorMessage("Ошибка! Неверно указан логин или пароль");

    }

    @Test
    void shouldInvalidVerificationCode() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVis();
        var verificationCode = DataHelper.getRandomCode().getCode();
        verificationPage.verify(verificationCode);
        verificationPage.errorCode();
    }

    @Test
    void shouldUserIsBlockedAfterThreeAttempts() {
        var authInfo = DataHelper.getAuthInfo();
        var authInfoFirst = new DataHelper.AuthInfo(DataHelper.getAuthInfo().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authInfoFirst);
        loginPage.getErrorMessage("Ошибка! Неверно указан логин или пароль");
        loginPage.cleanForm();
        clearBrowserCookies();
        var authInfoSecond = new DataHelper.AuthInfo(DataHelper.getAuthInfo().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authInfoSecond);
        loginPage.getErrorMessage("Ошибка! Неверно указан логин или пароль");
        loginPage.cleanForm();
        clearBrowserCookies();
        var authInfoThird = new DataHelper.AuthInfo(DataHelper.getAuthInfo().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authInfoThird);
        loginPage.getErrorMessage("Ошибка! Пользователь заблокирован");



    }
}