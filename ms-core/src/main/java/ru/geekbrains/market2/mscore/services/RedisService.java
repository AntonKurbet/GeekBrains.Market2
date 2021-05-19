package ru.geekbrains.market2.mscore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.market2.mscore.model.entities.TokenInfo;
import ru.geekbrains.market2.mscore.repository.RedisRepository;

import java.util.Optional;

@Service
public class RedisService {

    @Autowired
    private RedisRepository redisRepository;

    public boolean checkExists(String token) {
        Optional<TokenInfo> result = redisRepository.findById(token);
        return result.isPresent();
    }

    public void putInvalidToken(String token, long seconds) {
        redisRepository.save(new TokenInfo(token, (int) seconds));
    }
}
