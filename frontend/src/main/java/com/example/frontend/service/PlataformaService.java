// src/main/java/com/example/frontend/service/PlataformaService.java
package com.example.frontend.service;

import com.example.frontend.model.PlataformaDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class PlataformaService {

    // ✅ URL CORREGIDA: debe coincidir con tu endpoint en Spring Boot
    private static final String URL = "http://localhost:8080/api/plataformas";

    public List<PlataformaDto> listar() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(URL))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        PlataformaDto[] array = mapper.readValue(response.body(), PlataformaDto[].class);
        return Arrays.asList(array);
    }
}