package ru.geekbrains.market2.mscore.exceptions;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(String msg) {
        super("Invalid token:" + msg);
    }
}
