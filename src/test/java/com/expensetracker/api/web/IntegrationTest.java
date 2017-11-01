package com.expensetracker.api.web;

import com.expensetracker.api.web.api.request.LoginRequest;
import com.expensetracker.api.web.api.response.LoginResponse;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTest {

    public IntegrationTest() {
    }

    private static final String CONFIG_PATH = resourceFilePath("test-config.yml");

    @ClassRule
    public static final DropwizardAppRule<ExpenseConfiguration> RULE = new DropwizardAppRule<>(
            ExpenseApplication.class, CONFIG_PATH);

    @Test
    public void testPostLogin() throws Exception {
        final LoginRequest loginRequest = new LoginRequest("test@test.com", "test");
        final LoginResponse loginResponse= RULE.client().target(
                String.format("http://localhost:%d/login", RULE.getLocalPort()))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(loginRequest))
                .readEntity(LoginResponse.class);
        assertThat(loginResponse.getId()).isNotNull();
        assertThat(loginResponse.getName()).isNotNull();
        assertThat(loginResponse.getToken()).isNotNull();
    }

    @Test
    public void testLogFileWritten() throws IOException {
        // The log file is using a size and time based policy, which used to silently
        // fail (and not write to a log file). This test ensures not only that the
        // log file exists, but also contains the log line that jetty prints on startup
        final Path log = Paths.get("/tmp/application.log");
        assertThat(log).exists();
        final String actual = new String(Files.readAllBytes(log), UTF_8);
        assertThat(actual).contains("0.0.0.0:" + RULE.getLocalPort());
    }
}
