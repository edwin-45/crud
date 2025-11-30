package com.torrado.crud.services;

import com.torrado.crud.entities.Role;
import com.torrado.crud.entities.User;
import com.torrado.crud.repositories.RolRepository;
import com.torrado.crud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User save(User user) {
        Optional<Role> userRol = rolRepository.findByName("ROL_USER");
        List<Role> roles = new ArrayList<>();
        userRol.ifPresent(role -> roles.add(role));

        if(user.isAdmin()){
            Optional<Role> adminRol = rolRepository.findByName("ROL_ADMIN");
            adminRol.ifPresent(role -> roles.add(role));
        }

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
