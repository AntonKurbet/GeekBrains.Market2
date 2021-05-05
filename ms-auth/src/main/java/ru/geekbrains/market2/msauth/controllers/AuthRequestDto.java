package ru.geekbrains.market2.msauth.controllers;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String login;
    private String password;
}