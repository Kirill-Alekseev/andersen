package com.company.dao;

import static com.company.dao.DatabaseConnection.getInstance;

import com.company.exceptions.DaoException;
import com.company.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public Collection<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection connection = getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Unable to output all students", e);
        }
        return users;
    }

    @Override
    public User getById() {
        return null;
    }

    @Override
    public void create(User user) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void update(User user) {

    }
}
