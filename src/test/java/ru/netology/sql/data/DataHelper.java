package ru.netology.sql.data;

import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;

public class DataHelper {
    static final Faker faker = new Faker(Locale.forLanguageTag("ru"));

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("app", "pass");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {

        return new VerificationCode("12345");
    }

}
