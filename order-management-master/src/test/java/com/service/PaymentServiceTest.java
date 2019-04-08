package com.service;

import com.order.model.OrderDto;
import com.order.service.PaymentService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
@Transactional
public class PaymentServiceTest {
    @Autowired
    PaymentService paymentService;

    @Test
    public void check_create_payment_status_success() {
        {
            OrderDto orderDto = new OrderDto();
            orderDto.setProductId(1001);
            orderDto.setPaymentId("pay");
            orderDto.setOrderAmount(5);

            Assert.assertEquals(paymentService.createPaymentRequest(orderDto, BigDecimal.valueOf(50)).getStatus(), "success");
        }
    }

    @Test
    public void check_create_payment_status_failure() {
        {
            OrderDto orderDto = new OrderDto();
            orderDto.setProductId(1001);
            orderDto.setOrderAmount(5);

            Assert.assertEquals(paymentService.createPaymentRequest(orderDto, BigDecimal.valueOf(50)).getStatus(), "failure");

        }
    }

    @Test
    public void check_create_conversation_id() {
        {
            OrderDto orderDto = new OrderDto();
            orderDto.setProductId(1001);
            orderDto.setPaymentId("pay");
            orderDto.setOrderAmount(5);

            Assert.assertEquals(paymentService.createPaymentRequest(orderDto, BigDecimal.valueOf(50)).getConversationId(), "pay");

        }
    }

    @Test
    public void check_create_payment_id() {
        {
            OrderDto orderDto = new OrderDto();
            orderDto.setProductId(1001);
            orderDto.setPaymentId("pay");
            orderDto.setOrderAmount(5);

            Assert.assertNotNull(paymentService.createPaymentRequest(orderDto, BigDecimal.valueOf(50)).getPaymentId());

        }
    }

    @Test
    public void check_paid_price() {
        OrderDto orderDto = new OrderDto();
        orderDto.setProductId(1001);
        orderDto.setPaymentId("pay");
        orderDto.setOrderAmount(5);

        Assert.assertEquals(paymentService.createPaymentRequest(orderDto, BigDecimal.valueOf(50)).getPaidPrice(), BigDecimal.valueOf(50));

    }

    @Test
    public void retrieve_payment_request_status() {
        OrderDto orderDto = new OrderDto();
        orderDto.setProductId(1001);
        orderDto.setPaymentId("pay");
        orderDto.setOrderAmount(5);

        Assert.assertEquals(paymentService.retrievePaymentRequestStatus(orderDto), "failure");
    }
}