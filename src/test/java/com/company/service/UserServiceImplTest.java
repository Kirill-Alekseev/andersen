package com.company.service;

import com.company.dao.ConnectionProvider;
import com.company.dao.DefaultConnectionProvider;
import com.company.dao.UserDao;
import com.company.dao.UserDaoJdbc;
import com.company.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {

    private UserService userService;
    private UserDao userDao;
    private ConnectionProvider connectionProvider;

    @BeforeAll
    public void setUp() {
        connectionProvider = new DefaultConnectionProvider(
                "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false",
                "sa",
                null,
                "org.h2.Driver");
        userDao = Mockito.spy(new UserDaoJdbc(connectionProvider));
        userService = new UserServiceImpl(userDao);
    }

    @BeforeEach
    public void refresh() {
        SqlRunner sqlRunner = new SqlRunner(connectionProvider);
        sqlRunner.runSql("drop_tables.sql");
        sqlRunner.runSql("tables_creation.sql");
        sqlRunner.runSql("test_data.sql");
    }

    @Test
    public void shouldReturnCorrectUser() {
        User expected = new User(1, "name1");
        when(userDao.getById(1)).thenReturn(expected);
        assertEquals(expected, userService.getById(1));
    }

    @Test
    public void shouldReturnCorrectUsers() {
        User user1 = new User(1, "name1");
        User user2 = new User(2, "name2");
        ArrayList<User> expected = new ArrayList<>();
        expected.add(user1);
        expected.add(user2);
        when(userDao.getAll()).thenReturn(expected);
        assertEquals(expected, userService.getAll());
    }

    @Test
    public void shouldCreateUser() {
        User user = new User("name");
        int beforeNumberOfUsers = userDao.getAll().size();
        userService.create(user);
        assertEquals(beforeNumberOfUsers + 1, userDao.getAll().size());
    }

    @Test
    public void shouldUpdateUser() {
        User expected = new User(1, "name");
        userService.update(expected);
        assertEquals(expected, userDao.getById(expected.getId()));
    }

    @Test
    public void shouldDeleteUser() {
        int beforeNumberOfUsers = userDao.getAll().size();
        userService.deleteById(1);
        assertEquals(beforeNumberOfUsers - 1, userDao.getAll().size());
    }

}
