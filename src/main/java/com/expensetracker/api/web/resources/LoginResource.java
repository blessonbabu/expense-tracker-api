package com.expensetracker.api.web.resources;

import com.expensetracker.api.web.api.request.LoginRequest;
import com.expensetracker.api.web.api.response.LoginResponse;
import com.expensetracker.api.web.services.LoginService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/login")
@Consumes({MediaType.APPLICATION_JSON})
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    private final LoginService loginService;

    public LoginResource(LoginService loginService) {
        this.loginService = loginService;
    }

    @POST
    public LoginResponse createLogin(LoginRequest loginRequest) {
        return loginService.login(loginRequest);
    }
}
