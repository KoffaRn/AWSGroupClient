package org.koffa.service;

import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.koffa.model.LoginResponse;
import org.koffa.model.RegistrationPayload;
import org.springframework.test.util.ReflectionTestUtils;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    private CloseableHttpClient httpClient;
    private CloseableHttpResponse response;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        httpClient = mock(CloseableHttpClient.class);
        response = mock(CloseableHttpResponse.class);
        authService = new AuthService("http://localhost");
        ReflectionTestUtils.setField(authService, "httpClient", httpClient);
    }

    @Test
    void registerSuccess() throws Exception {
        when(httpClient.execute(any(HttpPost.class))).thenReturn(response);
        when(response.getCode()).thenReturn(HttpStatus.SC_OK);

        RegistrationPayload payload = new RegistrationPayload(); // Initialize with test data

        assertDoesNotThrow(() -> authService.register(payload));
    }


    @Test
    void loginSuccess() throws Exception {
        when(httpClient.execute(any(HttpPost.class))).thenReturn(response);
        when(response.getCode()).thenReturn(HttpStatus.SC_OK);
        LoginResponse expectedResponse = new LoginResponse(); // Initialize with expected data
        when(response.getEntity()).thenReturn(new StringEntity(new Gson().toJson(expectedResponse)));

        RegistrationPayload payload = new RegistrationPayload(); // Initialize with test data

        LoginResponse actualResponse = authService.login(payload);

        assertNotNull(actualResponse);
    }

}
