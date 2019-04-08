package com.controller;

import com.order.controller.OrderController;
import com.order.exception.NotEnoughStockException;
import com.order.exception.ProductNotFoundException;
import com.order.model.OrderDto;
import com.order.repository.OrderRepository;
import com.order.service.OrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
@Transactional
public class OrderControllerTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderController orderController;
    @Autowired
    OrderService orderService;

    @Test
    public void check_create_order_function_with_valid_quantity_number() {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderAmount(10);
        orderDto.setPaymentId("xyz");
        orderDto.setProductId(1001);
        orderController.createOrder(orderDto);

        ResponseEntity<?> responseEntity = orderController.createOrder(orderDto);
        Assert.assertEquals(responseEntity.getStatusCode(), (HttpStatus.CREATED));
    }

    @Test(expected = NotEnoughStockException.class)
    public void check_create_order_function_with_quantity_number_more_than_stock_number_value() {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderAmount(30);
        orderDto.setPaymentId("xyz");
        orderDto.setProductId(1002);
        orderController.createOrder(orderDto);
    }

    @Test
    public void check_find_all_order_function() {
        ResponseEntity<?> responseEntity = orderController.showAllOrders();
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void check_update_order_function_with_valid_product_id() {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderAmount(30);
        orderDto.setPaymentId("abc");
        orderDto.setProductId(1001);

        ResponseEntity<?> responseEntity = orderController.createOrder(orderDto);
        Assert.assertEquals(responseEntity.getStatusCode(), (HttpStatus.CREATED));

    }

    @Test(expected = ProductNotFoundException.class)
    public void check_update_order_function_with_invalid_product_id() {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderAmount(30);
        orderDto.setPaymentId("abc");
        orderDto.setProductId(1005);

        ResponseEntity<?> responseEntity = orderController.createOrder(orderDto);
        Assert.assertEquals(responseEntity.getStatusCode(), (HttpStatus.CREATED));

    }
}