package ru.geekbrains.market2.msauth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.market2.msauth.model.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}