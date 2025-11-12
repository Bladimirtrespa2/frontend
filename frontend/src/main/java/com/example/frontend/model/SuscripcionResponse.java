// model/SuscripcionResponse.java
package com.example.frontend.model;

import java.util.List;

public class SuscripcionResponse {
    private List<SuscripcionDto> content;

    public List<SuscripcionDto> getContent() {
        return content;
    }

    public void setContent(List<SuscripcionDto> content) {
        this.content = content;
    }
}