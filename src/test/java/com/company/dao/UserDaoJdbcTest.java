package com.company.dao;

import com.company.model.User;
import com.company.service.SqlRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDaoJdbcTest {

    private UserDao dao;
    private ConnectionProvider connectionProvider;

    @BeforeAll
    public void setup() {
        connectionProvider = new DefaultConnectionProvider(
                "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false",
                "sa",
                null,
                "org.h2.Driver");
        dao = new UserDaoJdbc(connectionProvider);
    }

    @BeforeEach
    public void refresh() {
        SqlRunner sqlRunner = new SqlRunner(connectionProvider);
        sqlRunner.runSql("drop_tables.sql");
        sqlRunner.runSql("tables_creation.sql");
        sqlRunner.runSql("test_data.sql");
    }

    @Test
    public void shouldReturnCorrectStudent() {
        User expected = new User(1, "name1");
        expected.setId(1);
        expected.setName("name1");
        assertEquals(expected, dao.getById(1));
    }

    @Test
    public void shouldReturnCorrectUsers() {
        User user1 = new User(1, "name1");
        User user2 = new User(2, "name2");
        ArrayList<User> expected = new ArrayList<>();
        expected.add(user1);
        expected.add(user2);
        assertEquals(expected, dao.getAll());
    }

    @Test
    public void shouldCreateUser() {
        User user = new User("name3");
        int beforeNumberOfUsers = dao.getAll().size();
        dao.create(user);
        assertEquals(beforeNumberOfUsers + 1, dao.getAll().size());
    }

    @Test
    public void shouldUpdateUser() {
        User expected = new User(1, "name");
        dao.update(expected);
        assertEquals(expected, dao.getById(expected.getId()));
    }

    @Test
    public void shouldDeleteUser() {
        int beforeNumberOfUsers = dao.getAll().size();
        dao.deleteById(1);
        assertEquals(beforeNumberOfUsers - 1, dao.getAll().size());
    }
}
