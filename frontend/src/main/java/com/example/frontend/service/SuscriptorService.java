// src/main/java/com/example/frontend/service/SuscriptorService.java
package com.example.frontend.service;

import com.example.frontend.model.SuscriptorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class SuscriptorService {

    private static final String URL = "http://localhost:8080/api/v1/suscriptores";

    public List<SuscriptorDto> listar() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(URL))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        SuscriptorDto[] array = mapper.readValue(response.body(), SuscriptorDto[].class);
        return Arrays.asList(array);
    }
}