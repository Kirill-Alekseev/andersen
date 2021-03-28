package com.company.service;

import com.company.dao.UserDao;
import com.company.model.User;

import java.util.Collection;

public class UserServiceImpl implements UserService {

    private UserDao dao;

    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public Collection<User> getAll() {
        return dao.getAll();
    }

    @Override
    public User getById(int id) {
        return dao.getById(id);
    }

    @Override
    public void create(User user) {
        dao.create(user);
    }

    @Override
    public void deleteById(int id) {
        dao.deleteById(id);
    }

    @Override
    public void update(User user) {
        dao.update(user);
    }
}
