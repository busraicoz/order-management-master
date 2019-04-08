package com.order.model;

import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.Min;

/**
 * OrderDto class for Product_Order table
 */
@Data
public class OrderDto {
    @Id
    private String paymentId;

    private long productId;
    @Min(0)
    private long orderAmount;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(long orderAmount) {
        this.orderAmount = orderAmount;
    }


    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

}
