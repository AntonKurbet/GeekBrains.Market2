package ru.geekbrains.market2.mscore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.market2.mscore.model.entities.TokenInfo;

import java.util.Optional;

@Repository
public interface RedisRepository extends CrudRepository<TokenInfo, String> {}