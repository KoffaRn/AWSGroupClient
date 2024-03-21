package org.koffa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.koffa.model.User;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private CloseableHttpClient httpClient;
    private UserService userService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        httpClient = mock(CloseableHttpClient.class);
        userService = new UserService("http://localhost");
        objectMapper = new ObjectMapper();
        ReflectionTestUtils.setField(userService, "httpClient", httpClient);
    }

    @Test
    void getAllUsers() throws Exception {
        List<User> expectedUsers = new ArrayList<>();
        String jsonUsers = objectMapper.writeValueAsString(expectedUsers);

        CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);
        when(mockResponse.getCode()).thenReturn(HttpStatus.SC_OK);
        when(mockResponse.getEntity()).thenReturn(new StringEntity(jsonUsers));
        when(httpClient.execute(any(HttpGet.class))).thenReturn(mockResponse);

        List<User> result = userService.getAllUsers("dummyJwt");
        assertNotNull(result);
        assertEquals(expectedUsers.size(), result.size());
    }

    @Test
    void getUserById() throws Exception {
        User expectedUser = new User(); // Assuming your User class has a no-arg constructor
        String jsonUser = objectMapper.writeValueAsString(expectedUser);

        CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);
        when(mockResponse.getCode()).thenReturn(HttpStatus.SC_OK);
        when(mockResponse.getEntity()).thenReturn(new StringEntity(jsonUser));
        when(httpClient.execute(any(HttpGet.class))).thenReturn(mockResponse);

        User result = userService.getUserById(1L, "dummyJwt");
        assertNotNull(result);
        assertEquals(expectedUser, result); // Assuming User class has an overridden equals method
    }

    @Test
    void updateUser() throws Exception {
        String expectedResponse = "User updated successfully";
        CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);
        when(mockResponse.getCode()).thenReturn(HttpStatus.SC_OK);
        when(mockResponse.getEntity()).thenReturn(new StringEntity(expectedResponse));
        when(httpClient.execute(any(HttpPatch.class))).thenReturn(mockResponse);

        String result = userService.updateUser(1, new User(), "dummyJwt");
        assertNotNull(result);
        assertEquals(expectedResponse, result);
    }

    @Test
    void updateUserRole() throws Exception {
        User expectedUser = new User(); // Assuming your User class has a no-arg constructor
        String jsonUser = objectMapper.writeValueAsString(expectedUser);

        CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);
        when(mockResponse.getCode()).thenReturn(HttpStatus.SC_OK);
        when(mockResponse.getEntity()).thenReturn(new StringEntity(jsonUser));
        when(httpClient.execute(any(HttpPatch.class))).thenReturn(mockResponse);

        User result = userService.updateUserRole(1, 2, "dummyJwt");
        assertNotNull(result);
        assertEquals(expectedUser, result); // Assuming User class has an overridden equals method
    }

    @Test
    void deleteUser() throws Exception {
        String expectedResponse = "User deleted successfully";
        CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);
        when(mockResponse.getCode()).thenReturn(HttpStatus.SC_OK);
        when(mockResponse.getEntity()).thenReturn(new StringEntity(expectedResponse));
        when(httpClient.execute(any(HttpDelete.class))).thenReturn(mockResponse);

        String result = userService.deleteUser(1L, "dummyJwt");
        assertNotNull(result);
        assertEquals(expectedResponse, result);
    }
}