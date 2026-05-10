package com.estore.e_strore_backend.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(String message) {
        super(message);
    }

    public InsufficientStockException(String productName, Integer requested, Integer available) {
        super("Insufficient stock for " + productName + ". Requested: " + requested + ", Available: " + available);
    }
}