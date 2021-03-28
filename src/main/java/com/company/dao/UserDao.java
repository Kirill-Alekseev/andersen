package com.company.dao;

import com.company.model.User;

import java.util.Collection;

public interface UserDao {

    Collection<User> getAll();

    User getById(int id);

    void create(User user);

    void deleteById(int id);

    void update(User user);
}
