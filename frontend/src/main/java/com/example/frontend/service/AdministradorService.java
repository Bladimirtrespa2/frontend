// src/main/java/com/example/frontend/service/AdministradorService.java
package com.example.frontend.service;

import com.example.frontend.model.AdministradorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class AdministradorService {

    // ✅ URL CORREGIDA: debe coincidir con tu endpoint en Spring Boot
    private static final String URL = "http://localhost:8080/api/administradores";

    public List<AdministradorDto> listar() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(URL))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        AdministradorDto[] array = mapper.readValue(response.body(), AdministradorDto[].class);
        return Arrays.asList(array);
    }
}