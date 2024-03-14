package org.koffa.helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.koffa.model.EmployeeDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ListEmployeeResponseHandler implements HttpClientResponseHandler<List<EmployeeDTO>> {
    @Override
    public List<EmployeeDTO> handleResponse(ClassicHttpResponse classicHttpResponse) throws HttpException, IOException {
        final int status = classicHttpResponse.getCode();
        if (status >= 200 && status < 300) {
            try(InputStream body = classicHttpResponse.getEntity().getContent()) {
                Gson gson = new Gson();
                String response = new String(body.readAllBytes());
                System.out.println(response);
                return gson.fromJson(response, new TypeToken<List<EmployeeDTO>>() {
                }.getType());
            } catch (Exception e) {
                throw new IOException("Unexpected response, success status: " + status);
            }
        } else {
            throw new HttpException("Unexpected response status: " + status);
        }
    }
}
