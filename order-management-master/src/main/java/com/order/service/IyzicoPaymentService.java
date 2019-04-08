package com.order.service;

import com.order.repository.PaymentRepository;
import com.order.entity.Payment;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.*;

@Service
@EnableHystrix
public class IyzicoPaymentService {
    Logger logger = LoggerFactory.getLogger(IyzicoPaymentService.class);
    private BankService bankService;
    private PaymentRepository paymentRepository;

    public IyzicoPaymentService(BankService bankService, PaymentRepository paymentRepository) {
        this.bankService = bankService;
        this.paymentRepository = paymentRepository;
    }

    @HystrixCommand(fallbackMethod = "fallbackPay")
    public void pay(BigDecimal price) {

        //pay with bank
        BankPaymentRequest request = new BankPaymentRequest();
        request.setPrice(price);
        BankPaymentResponse response = bankService.pay(request);

        //insert records
        Payment payment = new Payment();

        payment.setBankResponse(response.getResultCode());
        payment.setPrice(price);
        paymentRepository.save(payment);
        logger.info("Payment saved successfully!");
    }

    public void fallbackPay(BigDecimal price){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Payment payment = new Payment();
                payment.setBankResponse("invalid response");
                payment.setPrice(price);
                paymentRepository.save(payment);
                logger.info("Invalid payment activity!");
            }
        });
        executorService.shutdown();

    }

}
