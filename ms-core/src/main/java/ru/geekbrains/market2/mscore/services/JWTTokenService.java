package ru.geekbrains.market2.mscore.services;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.geekbrains.market2.mscore.interfaces.ITokenService;
import ru.geekbrains.market2.mscore.model.entities.UserInfo;

import java.sql.Time;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JWTTokenService implements ITokenService {

    @Value("$(jwt.secret)")
    private String JWT_SECRET;

    @Override
    public String generateToken(UserInfo user) {
        Instant expirationTime = Instant.now().plus(1, ChronoUnit.HOURS);
        Date expirationDate = Date.from(expirationTime);

        String compactTokenString = Jwts.builder()
                .claim("id", user.getUserId())
                .claim("sub", user.getUserEmail())
                .claim("role", user.getRole())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();

        return "Bearer " + compactTokenString;
    }

    @Override
    public Date getExpirationDate(String header){
        String token = header.replace("Bearer ", "");
        Jws<Claims> jwsClaims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token);

        return jwsClaims.getBody().getExpiration();
    }

    @Override
    public UserInfo parseToken(String token) throws ExpiredJwtException {
        Jws<Claims> jwsClaims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token);

        String email = jwsClaims.getBody()
                .getSubject();

        Long userId = jwsClaims.getBody()
                .get("id", Long.class);

        String role = jwsClaims.getBody()
                .get("role", String.class);

        return UserInfo.builder()
                .userId(userId)
                .userEmail(email)
                .role(role)
                .build();
    }

//    @Override
//    public Date getTTL(String token) throws ExpiredJwtException {
//        Jws<Claims> jwsClaims = Jwts.parser()
//                .setSigningKey(JWT_SECRET)
//                .parseClaimsJws(token);
//        long from = jwsClaims.getBody().getExpiration().getTime();
//        long to = Date.from(Instant.now()).getTime();
//
//        return (int) TimeUnit.SECONDS.convert(from - to, TimeUnit.MILLISECONDS);
////        return 30;
//    }
}
