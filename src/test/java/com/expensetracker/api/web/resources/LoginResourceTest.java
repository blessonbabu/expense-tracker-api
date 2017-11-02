package com.expensetracker.api.web.resources;

import com.expensetracker.api.web.api.request.LoginRequest;
import com.expensetracker.api.web.api.response.LoginResponse;
import com.expensetracker.api.web.core.UserLogin;
import com.expensetracker.api.web.db.LoginDAO;
import com.expensetracker.api.web.services.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class LoginResourceTest {

    private static final LoginDAO loginDAO = mock(LoginDAO.class);
    private static final LoginService loginService = new LoginService(loginDAO);
    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new LoginResource(loginService))
            .build();
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private final LoginRequest loginRequest = new LoginRequest("test@test.com", "test");

    private final UserLogin userLogin = MAPPER.readValue(fixture("fixtures/user.json"), UserLogin.class);

    public LoginResourceTest() throws IOException {
    }

    @Before
    public void setup() {
        when(loginDAO.checkLogin(eq("test@test.com"))).thenReturn(userLogin);
    }

    @After
    public void tearDown() {
        // we have to reset the mock after each test because of the
        // @ClassRule, or use a @Rule as mentioned below.
        reset(loginDAO);
    }

    @Test
    public void testGetPerson() {
        LoginResponse loginResponse = resources.client().target("/login").request().post(Entity.json(loginRequest)).readEntity(LoginResponse.class);
        assertThat(loginResponse.getId()).isEqualTo(1);
        assertThat(loginResponse.getName()).isEqualTo("Blesson Babu");
        assertThat(loginResponse.getRole()).isEqualTo("Admin");
        verify(loginDAO).checkLogin("test@test.com");
    }

}
