package org.koffa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
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

    public City addCity(City city, String jwt) throws RuntimeException {
        try {
            HttpPost httpPost = new HttpPost(BASE_URL + "/add");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + jwt);
            httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(city)));
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String result = EntityUtils.toString(response.getEntity());
                if (result.equals("City added successfully")) {
                    return city;
                } else {
                    throw new RuntimeException(result);
                }
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public City updateCity(long cityId, City city, String jwt) throws RuntimeException {
        try {
            HttpPatch httpPatch = new HttpPatch(BASE_URL + "/update/" + cityId);
            httpPatch.setHeader("Content-type", "application/json");
            httpPatch.setHeader("Authorization", "Bearer " + jwt);
            httpPatch.setEntity(new StringEntity(objectMapper.writeValueAsString(city)));
            try (CloseableHttpResponse response = httpClient.execute(httpPatch)) {
                String result = EntityUtils.toString(response.getEntity());
                if (result.equals("City updated successfully")) {
                    return city;
                } else {
                    throw new RuntimeException(result);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public City getCityByName(String cityName, String jwt) throws RuntimeException {
        try {
            HttpGet httpGet = new HttpGet(BASE_URL + "/getByName?cityName=" + cityName);
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
            HttpGet httpGet = new HttpGet(BASE_URL + "/getAll");
            httpGet.setHeader("Authorization", "Bearer " + jwt);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                return objectMapper.readValue(response.getEntity().getContent(), objectMapper.getTypeFactory().constructCollectionType(List.class, City.class));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCity(long cityId, String jwt) throws RuntimeException {
        try {
            HttpDelete httpDelete = new HttpDelete(BASE_URL + "/delete/" + cityId);
            httpDelete.setHeader("Authorization", "Bearer " + jwt);
            try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
                String result = EntityUtils.toString(response.getEntity());
                if (!result.equals("City deleted successfully")) {
                    throw new RuntimeException(result);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
