package com.expensetracker.api.web.core;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLogin {
    private long id;

    private String email;

    private String password;

    public UserLogin() {
    }

    public UserLogin(long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class Mapper implements ResultSetMapper<UserLogin> {
        public UserLogin map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException {
            return new UserLogin(
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
            );
        }
    }
}
