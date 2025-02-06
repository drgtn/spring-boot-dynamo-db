package com.drgtn.repository;

import com.drgtn.model.User;

import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findByEmail(String email);

    void remove(String email);

}
