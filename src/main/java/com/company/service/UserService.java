package com.company.service;

import com.company.model.User;

import java.util.Collection;

public interface UserService {

    Collection<User> getAll();

    User getById(int id);

    void create(User user);

    void deleteById(int id);

    void update(User user);
}
