package com.ingenico.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProfileDto {
    @JsonProperty("name")
    public String name;
    @JsonProperty("surname")
    public String surname;
    @JsonProperty("email")
    public String email;
    @JsonProperty("age")
    public int age;

    public ProfileDto(){

    }
}
