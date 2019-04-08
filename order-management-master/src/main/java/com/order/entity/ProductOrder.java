package com.order.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;

/**
 * Product_Order Table
 */
@Entity
public class ProductOrder {

    @Id
    private String paymentId;

    private long productId;
    @Min(0)
    private long orderAmount;

    public ProductOrder() {
    }

    public ProductOrder(String paymentId, long productId, long orderAmount) {
        this.paymentId = paymentId;
        this.productId = productId;
        this.orderAmount = orderAmount;

    }

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


