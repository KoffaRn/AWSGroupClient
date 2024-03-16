package org.koffa.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import org.koffa.model.User;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.NoSuchElementException;

public class UserService {
    private final String BASE_URL;
    private final CloseableHttpClient httpClient;

    public UserService(String baseUrl) {
        this.BASE_URL = baseUrl + "/user";
        this.httpClient = HttpClients.createDefault();
    }
    public List<User> getAllUsers(String jwt) {
        try {
            HttpGet request = new HttpGet(BASE_URL);
            request.setHeader("Authorization", "Bearer " + jwt);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getCode();
                if (statusCode == HttpStatus.SC_OK) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonResponse = EntityUtils.toString(response.getEntity());
                    return objectMapper.readValue(jsonResponse, new TypeReference<List<User>>() {});
                } else {
                    throw new RuntimeException("Failed to fetch users. Status code: " + statusCode);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to execute HTTP request or read response.", e);
        }
    }


    public String getUserById(Long id, String jwt) {
        try {
            HttpGet request = new HttpGet(BASE_URL + "/" + id);
            request.setHeader("Authorization", "Bearer " + jwt);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return handleResponse(response);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch user.", e);
        }
    }

    public String updateUser(Long id, String user, String jwt) {
        try {
            HttpPatch request = new HttpPatch(BASE_URL + "/" + id);
            StringEntity params = new StringEntity(user, org.apache.hc.core5.http.ContentType.APPLICATION_JSON);
            request.setEntity(params);
            request.setHeader("Authorization", "Bearer " + jwt);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return handleResponse(response);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Failed to update user.", e);
        }
    }

    public String updateUserRole(Long id, Integer roleId, String jwt) {
        try {
            HttpPatch request = new HttpPatch(BASE_URL + "/" + id + "/role/" + roleId);
            request.setHeader("Authorization", "Bearer " + jwt);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return handleResponse(response);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to update user role.", e);
        }
    }

    public String deleteUser(Long id, String jwt) {
        try {
            HttpDelete request = new HttpDelete(BASE_URL + "/" + id);
            request.setHeader("Authorization", "Bearer " + jwt);
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