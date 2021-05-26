package ru.geekbrains.market2.routing.dtos;

import lombok.Data;

@Data
public class SignUpRequestDto {

    private String login;
    private String password;
    private String email;
}