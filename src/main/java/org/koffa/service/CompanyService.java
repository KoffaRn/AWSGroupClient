package org.koffa.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPatch;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.koffa.model.CompanyDTO;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class CompanyService {
    private final String baseUrl;
    private final CloseableHttpClient httpClient;

    public CompanyService(String baseUrl) {
        this.baseUrl = baseUrl + "/company";
        this.httpClient = HttpClients.createDefault();
    }

    public CompanyDTO getCompanyByName(String companyName, String jwt) throws RuntimeException {
        try {
            HttpGet httpGet = new HttpGet(baseUrl + "/getByName?name=" + companyName);
            httpGet.setHeader("Content-type", "application/json");
            httpGet.setHeader("Authorization", "Bearer " + jwt);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                int status = response.getCode();
                String result = EntityUtils.toString(response.getEntity());
                if (status == HttpStatus.SC_OK) {
                    return new Gson().fromJson(result, CompanyDTO.class);
                } else {
                    throw new RuntimeException("Failed to get company. Status code: " + status);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to execute request or read response.", e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CompanyDTO updateCompany(int id, CompanyDTO companyDTO, String jwt) throws RuntimeException {
        try {
            HttpPatch httpPatch = new HttpPatch(baseUrl + "/update/" + id);
            httpPatch.setHeader("Content-type", "application/json");
            httpPatch.setHeader("Authorization", "Bearer " + jwt);
            httpPatch.setEntity(new StringEntity(new Gson().toJson(companyDTO)));
            try (CloseableHttpResponse response = httpClient.execute(httpPatch)) {
                int status = response.getCode();
                String result = EntityUtils.toString(response.getEntity());
                if (status == HttpStatus.SC_OK && result.equals("Company updated successfully")) {
                    return companyDTO;
                } else {
                    throw new RuntimeException("Failed to update company. Status code: " + status);
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to update company.", e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String deleteCompany(int id, String jwt) throws RuntimeException {
        try {
            HttpDelete httpDelete = new HttpDelete(baseUrl + "/delete/" + id);
            httpDelete.setHeader("Content-type", "application/json");
            httpDelete.setHeader("Authorization", "Bearer " + jwt);
            try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
                int status = response.getCode();
                String result = EntityUtils.toString(response.getEntity());
                if (status == HttpStatus.SC_OK && result.equals("Company deleted successfully")) {
                    return result;
                } else {
                    throw new RuntimeException("Failed to delete company. Status code: " + status);
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to delete company.", e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<CompanyDTO> getCompanies(String jwt) throws RuntimeException {
        try {
            HttpGet httpGet = new HttpGet(baseUrl + "/getAll");
            httpGet.setHeader("Content-type", "application/json");
            httpGet.setHeader("Authorization", "Bearer " + jwt);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                int status = response.getCode();
                String result = EntityUtils.toString(response.getEntity());
                if (status == HttpStatus.SC_OK) {
                    Type listType = new TypeToken<List<CompanyDTO>>(){}.getType();
                    return new Gson().fromJson(result, listType);
                } else {
                    throw new RuntimeException("Failed to get companies. Status code: " + status);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to execute request or read response.", e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CompanyDTO addCompany(CompanyDTO companyDTO, String jwt) throws RuntimeException {
        try {
            HttpPost httpPost = new HttpPost(baseUrl + "/add");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + jwt);
            httpPost.setEntity(new StringEntity(new Gson().toJson(companyDTO)));
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int status = response.getCode();
                String result = EntityUtils.toString(response.getEntity());
                if (status == HttpStatus.SC_OK && result.equals("Company added successfully")) {
                    return companyDTO;
                } else {
                    throw new RuntimeException("Failed to add company. Status code: " + status);
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to add company.", e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
