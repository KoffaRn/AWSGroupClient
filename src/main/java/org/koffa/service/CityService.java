package org.koffa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.koffa.model.City;
import org.koffa.model.CityToSendForUpdate;

import java.io.IOException;
import java.util.*;

public class CityService {

    private final String BASE_URL;
    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public CityService(String baseUrl) {
        BASE_URL = baseUrl + "/city";
        this.httpClient = HttpClients.createDefault();
        this.objectMapper = new ObjectMapper();
    }

    public City addCity(String cityName, String jwt) throws RuntimeException {
        try {
            Map<String, String> cityData = new HashMap<>();
            cityData.put("cityName", cityName);
            String cityJson = objectMapper.writeValueAsString(cityData);

            HttpPost httpPost = new HttpPost(BASE_URL);
            httpPost.setHeader("Authorization", "Bearer " + jwt);
            httpPost.setEntity(new StringEntity(cityJson, ContentType.APPLICATION_JSON));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getCode();
                if (statusCode == 200) {
                    System.out.println("City added successfully");
                    return objectMapper.readValue(response.getEntity().getContent(), City.class);
                } else {
                    System.out.println("Failed to add city. Status code: " + statusCode);
                    return null;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public City updateCity(CityToSendForUpdate cityToUpdate, String jwt) throws RuntimeException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(cityToUpdate);

            HttpPut httpPut = new HttpPut(BASE_URL + "/" + cityToUpdate.getCityId());
            httpPut.setHeader("Authorization", "Bearer " + jwt);
            httpPut.setHeader("Content-Type", "application/json");
            httpPut.setEntity(new StringEntity(jsonBody));

            try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
                int statusCode = response.getCode();
                if (statusCode == HttpStatus.SC_OK) {
                    System.out.println("City updated successfully");
                    String result = EntityUtils.toString(response.getEntity());
                    return objectMapper.readValue(result, City.class);
                } else {
                    System.out.println("Failed to update city. Status code: " + statusCode);
                    throw new RuntimeException("Failed to update city. Status code: " + statusCode);
                }
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }



    public City getCityById(int id, String jwt) throws RuntimeException {
        try {
            HttpGet httpGet = new HttpGet(BASE_URL + "/" + id);
            httpGet.setHeader("Authorization", "Bearer " + jwt);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                return objectMapper.readValue(response.getEntity().getContent(), City.class);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<City> getAllCities(String jwt) throws RuntimeException {
        try {
            HttpGet httpGet = new HttpGet(BASE_URL);
            httpGet.setHeader("Authorization", "Bearer " + jwt);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                return objectMapper.
                        readValue(response.getEntity()
                                        .getContent(), objectMapper.getTypeFactory()
                                        .constructCollectionType(List.class, City.class));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCity(int cityId, String jwt) throws RuntimeException {
        try {
            HttpDelete httpDelete = new HttpDelete(BASE_URL + "/" + cityId);
            httpDelete.setHeader("Authorization", "Bearer " + jwt);

            try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
                int statusCode = response.getCode();
                if (statusCode == 200) {
                    System.out.println("City deleted successfully");
                } else {
                    System.out.println("Failed to delete city. Status code: " + statusCode);
                    // Optionally, you can throw an exception here or handle the error accordingly.
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete city", e);
        }
    }


}
