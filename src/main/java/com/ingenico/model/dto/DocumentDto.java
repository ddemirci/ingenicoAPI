package com.ingenico.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentDto {
    @JsonProperty("name")
    public String name;
    @JsonProperty("profileId")
    public Long profileId;
}
