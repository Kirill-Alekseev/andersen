package com.company.dao;

import com.company.exceptions.DaoException;
import com.company.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDaoJdbc implements UserDao {

    private ConnectionProvider connectionProvider;

    public UserDaoJdbc(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Collection<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection connection = connectionProvider.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Unable to get all users", e);
        }
        return users;
    }

    @Override
    public User getById(int id) {
        User user = new User();
        String sql = "SELECT * FROM users u WHERE u.id = ?";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
            } else {
                throw new DaoException("User not found by id\n");
            }
        } catch (SQLException e) {
            throw new DaoException("Unable to get user by id", e);
        }
        return user;
    }

    @Override
    public void create(User user) {
        String sql = "INSERT INTO users(name) VALUES(?)";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Unable to create user", e);
        }

    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM users u WHERE u.id = ?";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Unable to remove user", e);
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET name = ? WHERE id = ?";
        try (Connection connection = connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Unable to update user", e);
        }
    }
}
