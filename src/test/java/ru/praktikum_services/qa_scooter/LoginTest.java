package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.client.CourierClient;
import ru.praktikum_services.qa_scooter.pojo.CourierRequest;
import ru.praktikum_services.qa_scooter.pojo.LoginRequest;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static ru.praktikum_services.qa_scooter.generator.CourierRequestGenerator.getRandomCourierRequest;
import static ru.praktikum_services.qa_scooter.generator.LoginRequestGenerator.getLoginRequest;

public class LoginTest {
    private CourierClient courierClient;
    private CourierRequest randomCourierRequest;
    private Integer id;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        randomCourierRequest = getRandomCourierRequest();
        courierClient.createCourier(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
    }

    @After
    public void tearDown() {
        if (id != null) {
            courierClient.deleteCourier(id)
                    .assertThat()
                    .statusCode(SC_OK)
                    .and()
                    .body("ok", equalTo(true));
        }
    }
    @DisplayName("Авторизация курьера.")
    @Description("Проверяю авторизацию курьера. Все параметры авторизации корректны")
    @Test
    public void courierMustBeAuthorizedTest() {
        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);
        id = courierClient.loginCourier(loginRequest)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("id", notNullValue())
                .extract()
                .path("id");
    }
    @DisplayName("Авторизация курьера. Пустой login")
    @Description("Проверяю авторизацию курьера с пустым login. Ожиаю ошибку авторизации")
    @Test
    public void courierMustNotBeAuthorizedLoginIsEmptyTest() {
        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);
        loginRequest.setLogin("");
        courierClient.loginCourier(loginRequest)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @DisplayName("Авторизация курьера. Пустой password")
    @Description("Проверяю авторизацию курьера с пустым password. Ожиаю ошибку авторизации")
    @Test
    public void courierMustNotBeAuthorizedPasswordIsEmptyTest() {
        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);
        loginRequest.setPassword("");
        courierClient.loginCourier(loginRequest)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @DisplayName("Авторизация курьера. Некорректный login")
    @Description("Проверяю авторизацию курьера с некорректным  login. Ожиаю ошибку авторизации")
    @Test
    public void courierMustNotBeAuthorizedLoginIsNotCorrectTest() {
        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);
        loginRequest.setLogin("1");
        courierClient.loginCourier(loginRequest)
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @DisplayName("Авторизация курьера. Некорректный password")
    @Description("Проверяю авторизацию курьера с некорректным  password. Ожиаю ошибку авторизации")
    @Test
    public void courierMustNotBeAuthorizedPasswordIsNotCorrectTest() {
        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);
        loginRequest.setPassword("1");
        courierClient.loginCourier(loginRequest)
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));

    }
}