package com.ingenico;

import com.ingenico.model.Profile;
import com.ingenico.model.dto.ProfileDto;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileTest {
    private static Server server;
    private static WebTarget target;

    @BeforeAll
    public static void beforeAllTests() {
        server = MainApp.startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(MainApp.BASE_URI);
    }

    @AfterAll
    public static void afterAllTests() throws Exception {
        server.stop();
    }

   @Test
    public void Profile_CreateProfile_Succeeded() {

        //Setup
        ProfileDto profileDto = new ProfileDto();
        profileDto.email = "mail@mail.com";
        profileDto.age = 22;
        profileDto.name = "John";
        profileDto.surname = "Doe";

        //Act
        Response response = target.path("profile").request().post(Entity.entity(profileDto,MediaType.APPLICATION_JSON));

        //Assert
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

        Profile returnedProfile = response.readEntity(Profile.class);
        assertEquals(profileDto.name, returnedProfile.getName());
        assertEquals(profileDto.surname, returnedProfile.getSurname());
        assertEquals(profileDto.age, returnedProfile.getAge());
        assertEquals(profileDto.email, returnedProfile.getEmail());
    }
}
