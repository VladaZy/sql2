package data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import page.VerificationPage;

import java.util.Locale;

public class DataHelper {
    private static final Faker faker = new Faker(new Locale("en"));

    private DataHelper() {
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    private static String getRandomLogin() {
        return faker.name().username();
    }

    public static String getRandomPassword() {
        return faker.internet().password();

    }

    public static AuthInfo getRandomUser() {
        return new AuthInfo(getRandomLogin(), getRandomPassword());
    }

    public static VerificationCode generateRandomVerificationCode() {
        return new VerificationCode(faker.numerify("######"));

    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getRandomCode() {
        return new VerificationCode(faker.numerify("12345"));
    }
}