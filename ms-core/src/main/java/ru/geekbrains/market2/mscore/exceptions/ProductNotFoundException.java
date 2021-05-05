package ru.geekbrains.market2.mscore.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String msg) {
        super("Product not found:" + msg);
    }
}
