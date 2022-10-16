package ru.praktikum_services.qa_scooter.generator;


import org.apache.commons.lang3.RandomStringUtils;
import ru.praktikum_services.qa_scooter.pojo.CourierRequest;


public class CourierRequestGenerator {
    public static CourierRequest getRandomCourierRequest() {
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setLogin(RandomStringUtils.randomAlphabetic(10));
        courierRequest.setPassword("12345");
        courierRequest.setFirstName("Vasya");
        return courierRequest;
    }


    public static CourierRequest getCourierRequestWithoutLogin() {
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setLogin("");
        courierRequest.setPassword("12345");
        courierRequest.setFirstName("Vasya");
        return courierRequest;
    }

    public static CourierRequest getCourierRequestWithoutPassword() {
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setLogin(RandomStringUtils.randomAlphabetic(10));
        courierRequest.setPassword("");
        courierRequest.setFirstName("Vasya");
        return courierRequest;
    }

    public static CourierRequest getCourierRequestWithoutFirstName() {
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setLogin(RandomStringUtils.randomAlphabetic(10));
        courierRequest.setPassword("12345");
        return courierRequest;
    }

    public static CourierRequest getCourierRequestAllParamIsEmpty() {
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setLogin("");
        courierRequest.setPassword("");
        courierRequest.setFirstName("");
        return courierRequest;
    }
}
