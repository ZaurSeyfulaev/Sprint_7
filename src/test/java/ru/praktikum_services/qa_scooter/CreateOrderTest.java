package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.client.OrderClient;
import ru.praktikum_services.qa_scooter.pojo.OrderRequest;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;
import static ru.praktikum_services.qa_scooter.generator.OrderRequestGenerator.getCreateOrderRequest;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private OrderClient orderClient;
    private String[] color;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }


    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][]{
                {new String[]{""}},
                {new String[]{"BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{"BLACK", "BLACK"}}
        };

    }
    @DisplayName("Создание заказа.")
    @Description("Проверяю создание заказаб в том числе с пустым параметром color. Все тесты позитивные")
    @Test
    public void createOrdersTest() {
        OrderRequest orderRequest = getCreateOrderRequest(color);
        orderClient.createOrder(orderRequest)
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("track", notNullValue());
    }


}
