package org.koffa.helper;

import com.google.gson.Gson;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.koffa.model.Employee;

import java.io.IOException;
import java.io.InputStream;

public class EmployeeResponseHandler implements HttpClientResponseHandler<Employee> {

    @Override
    public Employee handleResponse(ClassicHttpResponse classicHttpResponse) throws HttpException, IOException {
        final int status = classicHttpResponse.getCode();
        if (status >= 200 && status < 300) {
            try(InputStream body = classicHttpResponse.getEntity().getContent()) {
                Gson gson = new Gson();
                return gson.fromJson(new String(body.readAllBytes()), Employee.class);
            } catch (Exception e) {
                throw new HttpException("Unexpected response status: " + status);
            }
        } else {
            throw new IOException("Unexpected response, success status: " + status);
        }
    }
}
