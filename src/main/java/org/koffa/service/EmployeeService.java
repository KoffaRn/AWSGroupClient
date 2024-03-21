package org.koffa.service;

import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPatch;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.koffa.helper.EmployeeResponseHandler;
import org.koffa.helper.ListEmployeeResponseHandler;
import org.koffa.model.Employee;
import org.koffa.model.EmployeeDTO;

import java.util.List;

public class EmployeeService {
    private final String baseUrl;
    private CloseableHttpClient httpClient;

    public EmployeeService(String baseUrl) {
        this.baseUrl = baseUrl + "/employee";
        this.httpClient = HttpClients.createDefault(); // Skapar en standard HTTP-klient
    }


    public Employee addEmployee(Employee employee, String jwt) throws RuntimeException {
        try {
            HttpPost httpPost = new HttpPost(baseUrl + "/add");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + jwt);
            httpPost.setEntity(new StringEntity(new Gson().toJson(employee), ContentType.APPLICATION_JSON));
            String result = httpClient.execute(httpPost, new BasicHttpClientResponseHandler());
            if (result.equals("Employee added successfully")) {
                return employee;
            } else {
                throw new RuntimeException(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Employee updateEmployee (int id, Employee employee, String jwt) throws RuntimeException {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPatch httpPatch = new HttpPatch(baseUrl + "/update/" + id);
            httpPatch.setHeader("Content-type", "application/json");
            httpPatch.setHeader("Authorization", "Bearer " + jwt);
            httpPatch.setEntity(new StringEntity(new Gson().toJson(employee), ContentType.APPLICATION_JSON));
            String result = httpClient.execute(httpPatch, new BasicHttpClientResponseHandler());
            if(result.equals("Employee updated successfully")) {
                return employee;
            } else {
                throw new RuntimeException(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Employee getByName(String name, String jwt) throws RuntimeException {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(baseUrl + "/getByName?firstName=" + name);
            httpGet.setHeader("Authorization", "Bearer " + jwt);
            return httpClient.execute(httpGet, new EmployeeResponseHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<EmployeeDTO> getEmployees(String jwt) throws RuntimeException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(baseUrl + "/getAll");
            httpGet.setHeader("Authorization", "Bearer " + jwt);
            return httpClient.execute(httpGet, new ListEmployeeResponseHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteEmployee(int id, String jwt) throws RuntimeException {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpDelete httpDelete = new HttpDelete(baseUrl + "/delete/" + id);
            httpDelete.setHeader("Authorization", "Bearer " + jwt);
            String result = httpClient.execute(httpDelete, new BasicHttpClientResponseHandler());
            if(!result.equals("Employee deleted successfully")) {
                throw new RuntimeException(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
