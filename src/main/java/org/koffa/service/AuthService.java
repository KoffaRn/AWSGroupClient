package org.koffa.service;

import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.koffa.model.LoginResponse;
import org.koffa.model.RegistrationPayload;
import org.koffa.model.User;

import java.io.IOException;

public class AuthService {
    private final String baseUrl;
    private final CloseableHttpClient httpClient;

    public AuthService(String baseUrl) {
        this.baseUrl = baseUrl + "/auth";
        this.httpClient = HttpClients.createDefault();
    }

    public void register(RegistrationPayload payload) throws RuntimeException{
        try{
            HttpPost httpPost = new HttpPost(baseUrl + "/register");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(new StringEntity(new Gson().toJson(payload)));
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int status = response.getCode();
                if (status != HttpStatus.SC_OK) {
                    throw new RuntimeException("Failed to register user. Status code: " + status);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to execute request or read response.", e);
        }
    }

    public LoginResponse login(RegistrationPayload payload) throws RuntimeException{
        try{
            HttpPost httpPost = new HttpPost(baseUrl + "/login");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(new StringEntity(new Gson().toJson(payload)));
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int status = response.getCode();
                String result = EntityUtils.toString(response.getEntity());
                if (status == HttpStatus.SC_OK) {
                    return new Gson().fromJson(result, LoginResponse.class);
                } else {
                    throw new RuntimeException("Failed to login. Status code: " + status);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to execute request or read response.", e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
