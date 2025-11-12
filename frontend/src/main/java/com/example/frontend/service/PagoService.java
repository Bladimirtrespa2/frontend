// src/main/java/com/example/frontend/service/PagoService.java
package com.example.frontend.service;

import com.example.frontend.model.PagoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class PagoService {

    // ✅ URL CORREGIDA: coincide con tu endpoint Spring Boot
    private static final String URL = "http://localhost:8080/api/v1/pagos";

    public List<PagoDto> listar() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(URL))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        PagoDto[] array = mapper.readValue(response.body(), PagoDto[].class);
        return Arrays.asList(array);
    }
}