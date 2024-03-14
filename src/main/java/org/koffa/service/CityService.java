package org.koffa.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.StatusLine;
import org.koffa.model.City;

import java.io.IOException;
import java.util.List;

public class CityService {

    private final String BASE_URL;
    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public CityService(String baseUrl) {
        BASE_URL = baseUrl + "/city";
        this.httpClient = HttpClients.createDefault();
        this.objectMapper = new ObjectMapper();
    }

    public City getCityById(Long cityId, String jwt) throws RuntimeException {
        try {
            HttpGet httpGet = new HttpGet(BASE_URL + "/" + cityId);
            httpGet.setHeader("Authorization", "Bearer " + jwt);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                int statusCode = response.getCode();
                if (statusCode == HttpStatus.SC_OK) {
                    return objectMapper.readValue(response.getEntity().getContent(), City.class);
                } else {
                    throw new RuntimeException("Failed to retrieve city. Server returned status code: " + statusCode);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve city.", e);
        }
    }

    public List<City> getAllCities(String jwt) throws RuntimeException {
        try {
            HttpGet httpGet = new HttpGet(BASE_URL);
            httpGet.setHeader("Authorization", "Bearer " + jwt);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                int statusCode = response.getCode();
                if (statusCode == HttpStatus.SC_OK) {
                    return objectMapper.readValue(
                            response.getEntity().getContent(), objectMapper.getTypeFactory()
                                    .constructCollectionType(List.class, City.class));
                } else {
                    throw new RuntimeException("Failed to retrieve cities. Server returned status code: " + statusCode);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve cities.", e);
        }
    }

    public City addCity(City city, String jwt) throws RuntimeException {
        try {
            HttpPost httpPost = new HttpPost(BASE_URL);
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + jwt);
            httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(city)));
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getCode();
                if (statusCode == HttpStatus.SC_OK) {
                    // Deserialize the response JSON to City object
                    String responseJson = EntityUtils.toString(response.getEntity());
                    return objectMapper.readValue(responseJson, City.class);
                } else {
                    String result = EntityUtils.toString(response.getEntity());
                    throw new RuntimeException("Failed to add city. Server returned status code: " + statusCode + ", Response: " + result);
                }
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Failed to add city.", e);
        }
    }

    public City updateCity(Long cityId, City city, String jwt) throws RuntimeException {
        try {
            HttpPut httpPut = new HttpPut(BASE_URL + "/" + cityId);
            httpPut.setHeader("Content-type", "application/json");
            httpPut.setHeader("Authorization", "Bearer " + jwt);
            httpPut.setEntity(new StringEntity(objectMapper.writeValueAsString(city)));
            try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
                int statusCode = response.getCode();
                String result = EntityUtils.toString(response.getEntity());
                if (statusCode == HttpStatus.SC_OK && result.equals("City updated successfully")) {
                    return city;
                } else {
                    throw new RuntimeException("Failed to update city. Server returned status code: " + statusCode + ", Response: " + result);
                }
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Failed to update city.", e);
        }
    }

    public String deleteCity(Long cityId, String jwt) throws RuntimeException {
        try {
            HttpDelete httpDelete = new HttpDelete(BASE_URL + "/" + cityId);
            httpDelete.setHeader("Authorization", "Bearer " + jwt);
            try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
                int statusCode = response.getCode();
                if (statusCode == HttpStatus.SC_OK) {
                    return "City deleted successfully";
                } else {
                    String result = EntityUtils.toString(response.getEntity());
                    throw new RuntimeException("Failed to delete city. Server returned status code: " + statusCode + ", Response: " + result);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete city.", e);
        }
    }






}