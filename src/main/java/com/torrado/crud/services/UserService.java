package com.torrado.crud.services;

import com.torrado.crud.entities.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User save(User user);
}
