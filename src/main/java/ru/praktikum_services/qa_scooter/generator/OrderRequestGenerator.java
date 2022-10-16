package ru.praktikum_services.qa_scooter.generator;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import ru.praktikum_services.qa_scooter.pojo.OrderRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderRequestGenerator {

    public static OrderRequest getCreateOrderRequest(String[] color) {
        OrderRequest orderRequest = new OrderRequest();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        orderRequest.setFirstName(RandomStringUtils.randomAlphabetic(10));
        orderRequest.setLastName(RandomStringUtils.randomAlphabetic(10));
        orderRequest.setAddress(RandomStringUtils.randomAlphabetic(10));
        orderRequest.setMetroStation(RandomStringUtils.randomAlphabetic(10));
        orderRequest.setPhone("+7 800 355 35 35");
        orderRequest.setRentTime(RandomUtils.nextInt(1, 30));
        orderRequest.setDeliveryDate("2020-06-06");
        orderRequest.setComment(RandomStringUtils.randomAlphabetic(10));
        orderRequest.setColor(color);

        return orderRequest;
    }

}
