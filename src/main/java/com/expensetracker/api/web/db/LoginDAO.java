package com.expensetracker.api.web.db;

import com.expensetracker.api.web.core.UserLogin;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

public interface LoginDAO {
    @Mapper(UserLogin.Mapper.class)
    @SqlQuery("SELECT * from user where email = :email")
    UserLogin checkLogin(@Bind("email") String email);
}
