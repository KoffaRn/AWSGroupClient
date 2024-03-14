package org.koffa.service;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.codehaus.jackson.map.ObjectMapper;
import org.koffa.model.City;

import java.io.IOException;
import java.util.List;

public class CityService {

    private static final String BASE_URL = "http://localhost:8080/city";

    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public CityService() {
        this.httpClient = HttpClients.createDefault();
        this.objectMapper = new ObjectMapper();
    }

    public City findCityById(long cityId) throws IOException {
        HttpGet httpGet = new HttpGet(BASE_URL + "/" + cityId);
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            return objectMapper.readValue(response.getEntity().getContent(), City.class);
        }
    }

    public List<City> findAllCities() throws IOException {
        HttpGet httpGet = new HttpGet(BASE_URL);
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            return objectMapper.readValue(
                    response.getEntity()
                            .getContent(), objectMapper.getTypeFactory()
                            .constructCollectionType(List.class, City.class));
        }
    }

    public City addCity(City city) throws IOException {
        HttpPost httpPost = new HttpPost(BASE_URL);
        httpPost.setEntity(new StringEntity(objectMapper.writeValueAsString(city), ContentType.APPLICATION_JSON));
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            return objectMapper.readValue(response.getEntity()
                    .getContent(), City.class);
        }
    }

    public City updateCity(Long cityId, City city) throws IOException {
        HttpPut httpPut = new HttpPut(BASE_URL + "/" + cityId);
        httpPut.setEntity(new StringEntity(objectMapper.writeValueAsString(city), ContentType.APPLICATION_JSON));
        try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
            return objectMapper.readValue(response.getEntity().getContent(), City.class);
        }
    }

    public String deleteCity(Long cityId) throws IOException {
        HttpDelete httpDelete = new HttpDelete(BASE_URL + "/" + cityId);
        try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
            return response.getCode() + " " + response.getReasonPhrase();
        }
    }
}
