package ru.geekbrains.market2.msorder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbrains.market2.msorder.model.entities.Cart;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    @Query("select c from Cart c where c.userId = ?1")
    Optional<Cart> findByUserId(Long id);
}
