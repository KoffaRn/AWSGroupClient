package org.koffa.service;

import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPatch;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.koffa.helper.EmployeeResponseHandler;
import org.koffa.helper.ListEmployeeResponseHandler;
import org.koffa.model.Employee;
import org.koffa.model.EmployeeDTO;

import java.util.List;

public class EmployeeService {
    private final String baseUrl;
    public EmployeeService(String baseUrl) {
        this.baseUrl = baseUrl + "/employee";
    }
    public Employee addEmployee(Employee employee, String jwt) throws RuntimeException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(baseUrl + "/add");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + jwt);
            httpPost.setEntity(new StringEntity(new Gson().toJson(employee)));
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
            httpPatch.setEntity(new StringEntity(new Gson().toJson(employee)));
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
