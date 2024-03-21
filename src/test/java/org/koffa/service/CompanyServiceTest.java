package org.koffa.service;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPatch;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.koffa.model.Company;
import org.koffa.model.CompanyDTO;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompanyServiceTest {

    private CloseableHttpClient httpClient;
    private CloseableHttpResponse response;
    private CompanyService companyService;
    private Gson gson;

    @BeforeEach
    void setUp() {
        httpClient = mock(CloseableHttpClient.class);
        response = mock(CloseableHttpResponse.class);
        companyService = new CompanyService("http://localhost");
        gson = new Gson();
        ReflectionTestUtils.setField(companyService, "httpClient", httpClient);
    }

    @Test
    void getCompanyByNameSuccess() throws Exception {
        Company company = new Company(); // Initialize with test data
        String companyJson = gson.toJson(company);

        when(httpClient.execute(any(HttpGet.class))).thenReturn(response);
        when(response.getCode()).thenReturn(HttpStatus.SC_OK);
        when(response.getEntity()).thenReturn(new StringEntity(companyJson));

        Company result = companyService.getCompanyByName("TestCompany", "dummyJwt");
        assertNotNull(result);
    }

    @Test
    void getCompanyByNameFailure() throws Exception {
        when(httpClient.execute(any(HttpGet.class))).thenReturn(response);
        when(response.getCode()).thenReturn(HttpStatus.SC_NOT_FOUND);

        assertThrows(RuntimeException.class, () -> companyService.getCompanyByName("TestCompany", "dummyJwt"));
    }

    @Test
    void updateCompanySuccess() throws Exception {
        CompanyDTO companyDTO = new CompanyDTO(); // Initialize with test data
        String companyJson = gson.toJson(companyDTO);

        when(httpClient.execute(any(HttpPatch.class))).thenReturn(response);
        when(response.getCode()).thenReturn(HttpStatus.SC_OK);
        when(response.getEntity()).thenReturn(new StringEntity("Company updated successfully"));

        CompanyDTO result = companyService.updateCompany(1, companyDTO, "dummyJwt");
        assertNotNull(result);
    }

    @Test
    void updateCompanyFailure() throws Exception {
        CompanyDTO companyDTO = new CompanyDTO(); // Initialize with test data

        when(httpClient.execute(any(HttpPatch.class))).thenReturn(response);
        when(response.getCode()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);

        assertThrows(RuntimeException.class, () -> companyService.updateCompany(1, companyDTO, "dummyJwt"));
    }

    @Test
    void deleteCompanySuccess() throws Exception {
        when(httpClient.execute(any(HttpDelete.class))).thenReturn(response);
        when(response.getCode()).thenReturn(HttpStatus.SC_OK);
        when(response.getEntity()).thenReturn(new StringEntity("Company deleted successfully"));

        String result = companyService.deleteCompany(1, "dummyJwt");
        assertEquals("Company deleted successfully", result);
    }

    @Test
    void deleteCompanyFailure() throws Exception {
        when(httpClient.execute(any(HttpDelete.class))).thenReturn(response);
        when(response.getCode()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);

        assertThrows(RuntimeException.class, () -> companyService.deleteCompany(1, "dummyJwt"));
    }

    @Test
    void getCompaniesSuccess() throws Exception {
        List<CompanyDTO> companies = new ArrayList<>(); // Initialize with test data
        String companiesJson = gson.toJson(companies);

        when(httpClient.execute(any(HttpGet.class))).thenReturn(response);
        when(response.getCode()).thenReturn(HttpStatus.SC_OK);
        when(response.getEntity()).thenReturn(new StringEntity(companiesJson));

        Type listType = new TypeToken<List<CompanyDTO>>() {
        }.getType();
        List<CompanyDTO> result = companyService.getCompanies("dummyJwt");
        assertEquals(gson.fromJson(companiesJson, listType), result);
    }

    @Test
    void getCompaniesFailure() throws Exception {
        when(httpClient.execute(any(HttpGet.class))).thenReturn(response);
        when(response.getCode()).thenReturn(HttpStatus.SC_INTERNAL_SERVER_ERROR);

        assertThrows(RuntimeException.class, () -> companyService.getCompanies("dummyJwt"));
    }

}