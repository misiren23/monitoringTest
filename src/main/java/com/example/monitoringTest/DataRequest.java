package com.example.monitoringTest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataRequest {

    @JsonProperty("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
