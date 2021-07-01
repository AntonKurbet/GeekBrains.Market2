package ru.geekbrains.market2.msorder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.market2.msorder.model.entities.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> { }