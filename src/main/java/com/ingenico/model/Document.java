package com.ingenico.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Document {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("profileId")
    private Long profileId;

    public Document(){

    }

    public Document(Long id, String name, Long profileId){
        this.id = id;
        this.name = name;
        this.profileId = profileId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }
}
