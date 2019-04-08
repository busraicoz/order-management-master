package com.integration;

import com.order.controller.OrderController;
import com.order.controller.ProductController;
import com.order.exception.NotEnoughStockException;
import com.order.model.OrderDto;
import com.order.repository.OrderRepository;
import com.order.repository.ProductRepository;
import com.order.service.OrderService;
import com.order.service.ProductService;
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

import static org.hamcrest.Matchers.is;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
@Transactional
public class IntegrationTests {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderController orderController;
    @Autowired
    OrderService orderService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductController productController;
    @Autowired
    ProductService productService;

    @Test(expected = NotEnoughStockException.class)
    public void check_update_order_function_with_more_than_product_stock_num() {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderAmount(300);
        orderDto.setPaymentId("abc");
        orderDto.setProductId(1002);

        ResponseEntity<?> responseEntity = orderController.createOrder(orderDto);
        Assert.assertEquals(responseEntity, is(HttpStatus.OK));
    }

    @Test
    public void check_when_create_order_product_stock_size_decrease() {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderAmount(5);
        orderDto.setPaymentId("abc");
        orderDto.setProductId(1002);
        long orderAmount = orderDto.getOrderAmount();
        long initialSize = productRepository.findById(orderDto.getProductId()).get().getQuantity();
        orderController.createOrder(orderDto);
        long finalSize = productRepository.findById(orderDto.getProductId()).get().getQuantity();

        Assert.assertEquals(finalSize, initialSize - orderAmount);
    }

}
