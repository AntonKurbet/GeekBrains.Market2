package ru.geekbrains.market2.mscore.interfaces;


import ru.geekbrains.market2.mscore.model.entities.UserInfo;

public interface ITokenService {

    String generateToken(UserInfo user);

    UserInfo parseToken(String token);

    long getTTL(String token);
}
