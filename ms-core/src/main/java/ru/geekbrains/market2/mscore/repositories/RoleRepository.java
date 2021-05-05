package ru.geekbrains.market2.mscore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.market2.mscore.model.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}