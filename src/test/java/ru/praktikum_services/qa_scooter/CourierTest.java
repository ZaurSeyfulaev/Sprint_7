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
import static ru.praktikum_services.qa_scooter.generator.CourierRequestGenerator.*;
import static ru.praktikum_services.qa_scooter.generator.LoginRequestGenerator.getLoginRequest;

public class CourierTest {
    private CourierClient courierClient;

    private Integer id;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
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

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверяю создание курьера. Добавляю авторизацию, чтобы получить id и удалить")
    public void courierShouldBeCreatedTest() {
        CourierRequest randomCourierRequest = getCourierRequestWithoutFirstName();
        courierClient.createCourier(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));

        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);

        id = courierClient.loginCourier(loginRequest)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("id", notNullValue())
                .extract()
                .path("id");
    }

    @Test
    @DisplayName("Создание курьера с пустым firsNane")
    @Description("Проверяю создание курьера с пустым необязательным полем. Добавляю авторизацию, чтобы получить id и удалить")
    public void courierShouldBeCreatedFirstNameIsNullTest() {
        CourierRequest randomCourierRequest = getRandomCourierRequest();
        courierClient.createCourier(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));

        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);
        id = courierClient.loginCourier(loginRequest)
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("id", notNullValue())
                .extract()
                .path("id");
    }

    @DisplayName("Создание курьера. Логин не уникальный")
    @Description("Проверяю создание курьера с неуникальным логином. Курьер не должен быть создан")
    @Test
    public void courierMustNotBeCreatedLoginIsNotUniqueTest() {
        CourierRequest randomCourierRequest = getRandomCourierRequest();
        courierClient.createCourier(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
        courierClient.createCourier(randomCourierRequest)
                .assertThat()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @DisplayName("Создание курьера. Не передаю login")
    @Description("Проверяю создание курьера с пустым полем login. Курьер не должен быть создан")
    @Test
    public void courierMustNotBeCreatedLoginIsNullTest() {

        CourierRequest courierRequest = getCourierRequestWithoutLogin();
        courierClient.createCourier(courierRequest)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @DisplayName("Создание курьера. Не передаю password")
    @Description("Проверяю создание курьера с пустым полем password. Курьер не должен быть создан")
    @Test
    public void courierMustNotBeCreatedPasswordIsNullTest() {

        CourierRequest courierRequest = getCourierRequestWithoutPassword();
        courierClient.createCourier(courierRequest)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }
    @DisplayName("Создание курьера. Все параметры пустые")
    @Description("Проверяю создание курьера с пустыми параметрами. Курьер не должен быть создан")
    @Test
    public void courierMustNotBeCreatedAllParamIsEmptyTest() {

        CourierRequest courierRequest = getCourierRequestAllParamIsEmpty();
        courierClient.createCourier(courierRequest)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }
}
