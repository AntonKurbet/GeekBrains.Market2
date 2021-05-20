package ru.geekbrains.market2.mscore.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisRepository {

    @Autowired
    private RedisTemplate<String, Integer> tokenRedisTemplate;

    public void putToken(String token, long duration) {
        tokenRedisTemplate.opsForValue().set("token:" + token, 1, duration, TimeUnit.SECONDS);
    }

    public Integer getToken(String token) {
        return tokenRedisTemplate.opsForValue().get("token:" + token);
    }
}