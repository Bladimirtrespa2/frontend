// src/main/java/com/example/frontend/service/CuentaService.java
package com.example.frontend.service;

import com.example.frontend.model.CuentaDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class CuentaService {

    // ✅ URL CORREGIDA: coincide con tu endpoint en Spring Boot
    private static final String URL = "http://localhost:8080/api/cuentas";

    public List<CuentaDto> listar() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(URL))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        CuentaDto[] array = mapper.readValue(response.body(), CuentaDto[].class);
        return Arrays.asList(array);
    }
}
