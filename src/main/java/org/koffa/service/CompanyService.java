package org.koffa.service;

import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.koffa.helper.EmployeeResponseHandler;
import org.koffa.model.Company;
import org.koffa.model.Employee;

import java.net.http.HttpResponse;

public class CompanyService {
    private final String baseUrl;
    private final CloseableHttpClient httpClient;

    public CompanyService(String baseUrl) {
        this.baseUrl = baseUrl + "/company";
        this.httpClient = HttpClients.createDefault();
    }
//    Alla services tar in baseUrl som String
//    TODO Alla services kastar fel om de inte kan returnera data

    //    CompanyService:
//        - getCompanyByName(String name(as request param)!, String jwt) : Company "/getByName"
    public Company getCompanyByName(String cityName, String jwt) throws RuntimeException {
        try {
            HttpGet httpGet = new HttpGet(baseUrl + "/getByName?name=" + cityName);
            httpGet.setHeader("Content-type", "application/json");
            httpGet.setHeader("Authorization", "Bearer " + jwt);
            try(CloseableHttpResponse response = httpClient.execute(httpGet)) {
                return new Gson().fromJson(new BasicHttpClientResponseHandler().handleResponse(response), Company.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /*public Employee getByName(String name, String jwt) throws RuntimeException {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(baseUrl + "/getByName?firstName=" + name);
            httpGet.setHeader("Authorization", "Bearer " + jwt);
            return httpClient.execute(httpGet, new EmployeeResponseHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/


//        - updateCompany(int id, Company company) : Company "/update/{id}"

//        - deleteCompany(int id) : void "/delete/{id}"

//        - getCompanies() : List<Company> "/getAll"

//        - addCompany(Company company) : Company "/add"

}
