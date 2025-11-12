// src/main/java/com/example/frontend/service/SuscripcionService.java
package com.example.frontend.service;

import com.example.frontend.model.SuscripcionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class SuscripcionService {

    // ✅ URL CORREGIDA: debe coincidir con tu endpoint en Spring Boot
    private static final String URL = "http://localhost:8080/api/v1/suscripciones";

    public List<SuscripcionDto> listar() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(URL))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        // Deserializa directamente a un arreglo de DTOs
        SuscripcionDto[] array = mapper.readValue(response.body(), SuscripcionDto[].class);
        return Arrays.asList(array);
    }
}