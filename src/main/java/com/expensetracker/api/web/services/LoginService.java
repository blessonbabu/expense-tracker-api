package com.expensetracker.api.web.services;

import com.expensetracker.api.web.api.request.LoginRequest;
import com.expensetracker.api.web.api.response.LoginResponse;
import com.expensetracker.api.web.core.UserLogin;
import com.expensetracker.api.web.db.LoginDAO;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class LoginService {

    private final LoginDAO loginDAO;

    public LoginService(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        final long nowMillis = System.currentTimeMillis();
        final long expMillis = nowMillis + TimeUnit.HOURS.toMillis(5);
        UserLogin userLogin = loginDAO.checkLogin(loginRequest.getEmail());
        if (userLogin == null)
            throw new WebApplicationException("Email Not Registered", Response.Status.UNAUTHORIZED);
        if (!(userLogin.getPassword().equals(loginRequest.getPassword())))
            throw new WebApplicationException("Email and Password Not match", Response.Status.UNAUTHORIZED);
        String JwtToken = createJWT(userLogin.getId(), nowMillis, expMillis);
        return new LoginResponse(
                        userLogin.getId(),
                        "Blesson Babu",
                        "Admin",
                        expMillis,
                        JwtToken
                );
    }

    private String createJWT(long employeeId, long nowMillis, long expMillis) {

        final Date now = new Date(nowMillis);
        final Date expiry = new Date(expMillis);

        return Jwts.builder()
                .setIssuer("http://localhost:8080/auth")
                .setIssuedAt(now)
                .setSubject(String.valueOf(employeeId))
                .setAudience(String.valueOf("attendance"))
                .claim("exp", expiry)
                .claim("auth_time", now)
                .signWith(SignatureAlgorithm.HS512, "expense-tracker")
                .compact();
    }
}
