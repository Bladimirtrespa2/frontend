// src/main/java/com/example/frontend/service/PerfilService.java
package com.example.frontend.service;

import com.example.frontend.model.PerfilDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class PerfilService {

    // ✅ URL CORREGIDA: coincide con tu endpoint en Spring Boot
    private static final String URL = "http://localhost:8080/api/perfiles";

    public List<PerfilDto> listar() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(URL))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        PerfilDto[] array = mapper.readValue(response.body(), PerfilDto[].class);
        return Arrays.asList(array);
    }
}