package org.koffa.service;

import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;


import java.io.IOException;
import java.util.NoSuchElementException;

public class UserService {
    private static final String BASE_URL = "http://localhost:5000/user";
    private final CloseableHttpClient httpClient;

    public UserService() {
        this.httpClient = HttpClients.createDefault();
    }

    public String getAllUsers() {
        try {
            HttpGet request = new HttpGet(BASE_URL);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return handleResponse(response);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Failed to fetch users.", e);
        }
    }

    public String getUserById(Long id) {
        try {
            HttpGet request = new HttpGet(BASE_URL + "/" + id);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return handleResponse(response);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch user.", e);
        }
    }

    public String updateUser(Long id, String user) {
        try {
            HttpPatch request = new HttpPatch(BASE_URL + "/" + id);
            StringEntity params = new StringEntity(user, ContentType.APPLICATION_JSON);
            request.setEntity(params);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return handleResponse(response);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Failed to update user.", e);
        }
    }

    public String updateUserRole(Long id, Integer roleId) {
        try {
            HttpPatch request = new HttpPatch(BASE_URL + "/" + id + "/role/" + roleId);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return handleResponse(response);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to update user role.", e);
        }
    }

    public String deleteUser(Long id) {
        try {
            HttpDelete request = new HttpDelete(BASE_URL + "/" + id);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return handleResponse(response);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Failed to delete user.", e);
        }
    }

    private String handleResponse(CloseableHttpResponse response) throws IOException, ParseException {
        int statusCode = response.getCode();
        HttpEntity entity = response.getEntity();
        if (statusCode == HttpStatus.SC_OK) {
            return EntityUtils.toString(entity);
        } else if (statusCode == HttpStatus.SC_NOT_FOUND) {
            throw new NoSuchElementException("User not found.");
        } else {
            throw new RuntimeException("Server returned status code: " + statusCode);
        }
    }
}

