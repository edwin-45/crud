package com.torrado.crud.repositories;

import com.torrado.crud.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RolRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
