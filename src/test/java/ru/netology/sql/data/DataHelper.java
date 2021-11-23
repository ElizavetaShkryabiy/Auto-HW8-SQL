package ru.netology.sql.data;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.DriverManager;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
        private String code;
    }

    @SneakyThrows
    public static AuthInfo getValidAuthInfo() {
        var runner = new QueryRunner();
        var user = "SELECT id, login, password FROM users;";
        var vCode = "SELECT user_id, code FROM auth_codes WHERE user_id IN (SELECT id FROM users);";
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            var first = runner.query(conn, user, new BeanHandler<>(AuthInfo.class));
            var uCode = runner.query(conn, vCode, new BeanHandler<>(AuthInfo.class));
            var login = first.login;
            var pass = first.password;

            var code = first.code;
            AuthInfo info = new AuthInfo(login, pass, code);
            return info;
        }
    }

    @SneakyThrows
    public static AuthInfo getInvalidLogin() {
        var runner = new QueryRunner();
        Faker faker = new Faker();
        var user = "SELECT id,login, password FROM users;";
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            var first = runner.query(conn, user, new BeanHandler<>(AuthInfo.class));
            var login = faker.name().username();
            var pass = first.password;
            AuthInfo info = new AuthInfo(login, pass, first.code);
            return info;
        }
    }

    @SneakyThrows
    public static AuthInfo getInvalidPassword() {
        var runner = new QueryRunner();
        Faker faker = new Faker();
        var user = "SELECT id,login, password FROM users;";
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            var first = runner.query(conn, user, new BeanHandler<>(AuthInfo.class));
            var login = first.login;
            var pass = faker.number().toString();
            AuthInfo info = new AuthInfo(login, pass, first.code);
            return info;
        }
    }

}
