package com.company;

import com.company.dao.UserDao;
import com.company.dao.UserDaoImpl;
import com.company.model.User;

import java.util.Collection;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        UserDao dao = new UserDaoImpl();
        Collection<User> all = dao.getAll();
        System.out.println("Bye");
    }
}
