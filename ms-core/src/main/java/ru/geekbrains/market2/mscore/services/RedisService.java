package ru.geekbrains.market2.mscore.services;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class RedisService {

    public static boolean checkExists(String token) {
        Jedis jedis = new Jedis();
        String result = jedis.get(token);
        return result != null;
    }

    public static void putInvalidToken(String token, long seconds) {
        Jedis jedis = new Jedis();
        jedis.set(token, "invalid");
        jedis.expire(token, (int) seconds);
    }
}
