package ru.praktikum_services.qa_scooter.generator;

import ru.praktikum_services.qa_scooter.pojo.CourierRequest;
import ru.praktikum_services.qa_scooter.pojo.LoginRequest;

public class LoginRequestGenerator {

    public static LoginRequest getLoginRequest(CourierRequest courierRequest) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(courierRequest.getLogin());
        loginRequest.setPassword(courierRequest.getPassword());
        return loginRequest;

    }
}
