package com.order.exception;

public class InvalidStockNumberException extends RuntimeException {
    public InvalidStockNumberException(String message) {
        super(message);
    }
}
