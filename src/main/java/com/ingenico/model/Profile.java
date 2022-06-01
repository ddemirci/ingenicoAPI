package com.ingenico.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Profile {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("surname")
    private String surname;
    @JsonProperty("email")
    private String email;
    @JsonProperty("age")
    private int age;

    public Profile(){

    }

    public Profile(Long id, String name, String surname, String email, int age){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
        //this.documentList = new HashMap<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
