package ru.geekbrains.market2.mscore.interfaces;


import ru.geekbrains.market2.mscore.model.entities.UserInfo;

import java.util.Date;

public interface ITokenService {

    String generateToken(UserInfo user);

    UserInfo parseToken(String token);

    Date getExpirationDate(String token);
}
