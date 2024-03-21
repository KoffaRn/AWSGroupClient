package org.koffa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.koffa.model.City;
import org.koffa.model.CityToSendForUpdate;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class CityServiceTest {

    private CloseableHttpClient httpClient;
    private CloseableHttpResponse response;
    private CityService cityService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        httpClient = mock(CloseableHttpClient.class);
        response = mock(CloseableHttpResponse.class);
        cityService = new CityService("http://localhost");
        objectMapper = new ObjectMapper();
        ReflectionTestUtils.setField(cityService, "httpClient", httpClient);
        ReflectionTestUtils.setField(cityService, "objectMapper", objectMapper);
    }

    @Test
    void addCitySuccess() throws Exception {
        City mockCity = new City(); // Initialize with test data
        String cityJson = objectMapper.writeValueAsString(mockCity);

        when(httpClient.execute(any(HttpPost.class))).thenReturn(response);
        when(response.getCode()).thenReturn(HttpStatus.SC_OK);
        when(response.getEntity()).thenReturn(new StringEntity(cityJson));

        City result = cityService.addCity("TestCity", "dummyJwt");
        assertNotNull(result);
    }

    @Test
    void addCityFailure() throws Exception {
        when(httpClient.execute(any(HttpPost.class))).thenReturn(response);
        when(response.getCode()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);

        City result = cityService.addCity("TestCity", "dummyJwt");
        assertNull(result);
    }

    @Test
    void updateCitySuccess() throws Exception {
        CityToSendForUpdate cityToUpdate = new CityToSendForUpdate(); // Initiera med testdata
        City updatedCity = new City(); // Initiera med förväntad uppdaterad data
        String updatedCityJson = objectMapper.writeValueAsString(updatedCity);

        when(httpClient.execute(any(HttpPut.class))).thenReturn(response);
        when(response.getCode()).thenReturn(HttpStatus.SC_OK);
        when(response.getEntity()).thenReturn(new StringEntity(updatedCityJson));

        City result = cityService.updateCity(cityToUpdate, "dummyJwt");
        assertNotNull(result);
    }

    @Test
    void updateCityFailure() throws Exception {
        CityToSendForUpdate cityToUpdate = new CityToSendForUpdate(); // Initiera med testdata

        when(httpClient.execute(any(HttpPut.class))).thenReturn(response);
        when(response.getCode()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);

        assertThrows(RuntimeException.class, () -> cityService.updateCity(cityToUpdate, "dummyJwt"));
    }

    @Test
    void getCityByIdSuccess() throws Exception {
        City city = new City(); // Initiera med förväntade data
        String cityJson = objectMapper.writeValueAsString(city);

        when(httpClient.execute(any(HttpGet.class))).thenReturn(response);
        when(response.getCode()).thenReturn(HttpStatus.SC_OK);
        when(response.getEntity()).thenReturn(new StringEntity(cityJson));

        City result = cityService.getCityById(1, "dummyJwt");
        assertNotNull(result);
    }


    @Test
    void getAllCitiesSuccess() throws Exception {
        List<City> cities = Arrays.asList(new City(), new City()); // Initiera med testdata
        String citiesJson = objectMapper.writeValueAsString(cities);

        when(httpClient.execute(any(HttpGet.class))).thenReturn(response);
        when(response.getCode()).thenReturn(HttpStatus.SC_OK);
        when(response.getEntity()).thenReturn(new StringEntity(citiesJson));

        List<City> result = cityService.getAllCities("dummyJwt");
        assertNotNull(result);
        assertEquals(2, result.size());
    }


    @Test
    void deleteCitySuccess() throws Exception {
        when(httpClient.execute(any(HttpDelete.class))).thenReturn(response);
        when(response.getCode()).thenReturn(HttpStatus.SC_OK);

        assertDoesNotThrow(() -> cityService.deleteCity(1, "dummyJwt"));
    }

}
