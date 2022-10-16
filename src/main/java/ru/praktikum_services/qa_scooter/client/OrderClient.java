package ru.praktikum_services.qa_scooter.client;

import io.restassured.response.ValidatableResponse;
import ru.praktikum_services.qa_scooter.pojo.OrderRequest;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {

    private static final String ORDER = "orders";


    public ValidatableResponse createOrder(OrderRequest orderRequest) {
        return given()
                .spec(getDefaultRequestSpec())
                .body(orderRequest)
                .post(ORDER)
                .then();
    }

    public ValidatableResponse getOrderList(OrderRequest orderRequest) {
        return given()
                .spec(getDefaultRequestSpec())
                .body(orderRequest)
                .get(ORDER)
                .then();
    }
}

