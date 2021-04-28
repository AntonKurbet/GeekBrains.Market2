package ru.geekbrains.market2.msauth.model.dtos;

import lombok.Data;

@Data
public class SignUpRequestDto {

    private String login;

    private String password;
}