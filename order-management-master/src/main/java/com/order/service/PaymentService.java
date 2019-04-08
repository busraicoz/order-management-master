package com.order.service;

import com.order.configuration.PaymentAPIConfiguration;
import com.order.model.OrderDto;
import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.request.CreatePaymentRequest;
import com.iyzipay.request.RetrievePaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    private final Options options = new Options();
    private Logger logger = LoggerFactory.getLogger(IyzicoPaymentService.class);

    PaymentService(PaymentAPIConfiguration paymentAPIConfiguration) {
        this.options.setApiKey(paymentAPIConfiguration.getApiKey());
        this.options.setSecretKey(paymentAPIConfiguration.getSecretKey());
        this.options.setBaseUrl(paymentAPIConfiguration.getBaseUrl());
    }

    /**
     * Creating payment request with hard coded values which same for all payment request
     *
     * @param orderDto
     * @param price    Order amount price
     * @return
     */

    public Payment createPaymentRequest(OrderDto orderDto, BigDecimal price) {

        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setLocale(Locale.TR.getValue());
        request.setConversationId(orderDto.getPaymentId());
        request.setPrice(price);
        request.setPaidPrice(price);
        request.setCurrency(Currency.TRY.name());
        request.setInstallment(1);
        request.setBasketId(orderDto.getPaymentId());
        request.setPaymentChannel(PaymentChannel.WEB.name());
        request.setPaymentGroup(PaymentGroup.PRODUCT.name());

        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardHolderName("John Doe");
        paymentCard.setCardNumber("5528790000000008");
        paymentCard.setExpireMonth("12");
        paymentCard.setExpireYear("2030");
        paymentCard.setCvc("123");
        paymentCard.setRegisterCard(0);
        request.setPaymentCard(paymentCard);

        Buyer buyer = new Buyer();
        buyer.setId(orderDto.getPaymentId());
        buyer.setName("John");
        buyer.setSurname("Doe");
        buyer.setGsmNumber("+905350000000");
        buyer.setEmail("email@email.com");
        buyer.setIdentityNumber("74300864791");
        buyer.setLastLoginDate("2015-10-05 12:43:35");
        buyer.setRegistrationDate("2013-04-21 15:12:09");
        buyer.setRegistrationAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
        buyer.setIp("85.34.78.112");
        buyer.setCity("Istanbul");
        buyer.setCountry("Turkey");
        buyer.setZipCode("34732");
        request.setBuyer(buyer);

        Address shippingAddress = new Address();
        shippingAddress.setContactName("Jane Doe");
        shippingAddress.setCity("Istanbul");
        shippingAddress.setCountry("Turkey");
        shippingAddress.setAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
        shippingAddress.setZipCode("34742");
        request.setShippingAddress(shippingAddress);

        Address billingAddress = new Address();
        billingAddress.setContactName("Jane Doe");
        billingAddress.setCity("Istanbul");
        billingAddress.setCountry("Turkey");
        billingAddress.setAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
        billingAddress.setZipCode("34742");
        request.setBillingAddress(billingAddress);

        List<BasketItem> basketItems = new ArrayList<BasketItem>();
        BasketItem firstBasketItem = new BasketItem();
        firstBasketItem.setId("BI101");
        firstBasketItem.setName("Binocular");
        firstBasketItem.setCategory1("Collectibles");
        firstBasketItem.setCategory2("Accessories");
        firstBasketItem.setItemType(BasketItemType.PHYSICAL.name());
        firstBasketItem.setPrice(price);
        basketItems.add(firstBasketItem);
        request.setBasketItems(basketItems);

        logger.info("Payment Request created successfully: " + Payment.create(request, options).getStatus());
        return Payment.create(request, options);
    }

    /**
     * Showing Payment Request Status
     *
     * @param orderDto
     * @return
     */
    public String retrievePaymentRequestStatus(OrderDto orderDto) {
        RetrievePaymentRequest retrievePaymentRequest = new RetrievePaymentRequest();
        retrievePaymentRequest.setLocale(Locale.TR.getValue());
        retrievePaymentRequest.setConversationId(orderDto.getPaymentId());
        retrievePaymentRequest.setPaymentId("1");
        retrievePaymentRequest.setPaymentConversationId(orderDto.getPaymentId());
        Payment payment = Payment.retrieve(retrievePaymentRequest, options);
        logger.info("Payment request result: " + payment.getStatus());
        return payment.getStatus();
    }

}