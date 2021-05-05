package ru.geekbrains.market2.mscore.exceptions;

public class InvalidPageException extends RuntimeException{
    public InvalidPageException(String msg) {
        super("Invalid page:" + msg);
    }
}
