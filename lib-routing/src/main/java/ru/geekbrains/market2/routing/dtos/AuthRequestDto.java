package ru.geekbrains.market2.routing.dtos;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String login;
    private String password;
}