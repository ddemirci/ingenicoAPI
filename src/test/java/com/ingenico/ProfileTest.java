package com.ingenico;


import com.ingenico.controller.ProfileController;
import com.ingenico.model.Profile;
import com.ingenico.model.dto.ProfileDto;
import com.ingenico.service.ProfileService;
import jakarta.ws.rs.core.Response;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfileTest {

    @InjectMocks
    private ProfileController profileController;
    @Mock
    private ProfileService profileService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void get_profileDoesNotExist_returnsNotFound(){
        //Setup
        when(profileService.getProfile(anyLong())).thenReturn(null);
        //Act
        Response response = profileController.Get(anyLong());
        //Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        String message = (String) response.getEntity();
        assertEquals(message,"Requested profile was not found.");
    }

    @Test
    public void get_profileExists_returnsProfile(){
        //Setup
        Profile profile = GenerateDummyUser(1L);
        when(profileService.getProfile(anyLong())).thenReturn(profile);
        //Act
        Response response = profileController.Get(anyLong());
        //Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Profile result  = (Profile) response.getEntity();
        assertEquals(result,profile);
    }

    @Test
    public void getAll_succeeded_returnsProfiles(){
        //Setup
        List<Profile> profileList = new ArrayList<>();
        profileList.add(GenerateDummyUser(1L));
        profileList.add(GenerateDummyUser(2L));
        when(profileService.getAllProfiles()).thenReturn(profileList);

        //Act
        Response response = profileController.GetAll();

        //Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        List<Profile> result  = (List<Profile>) response.getEntity();
        assertEquals(result.size(),profileList.size());
    }

    @Test
    public void insert_failed_returnsNotFound(){
        //Setup
        when(profileService.insertProfile(any(ProfileDto.class))).thenReturn(null);

        //Act
        Response response = profileController.insert(new ProfileDto());

        //Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        String message  = (String) response.getEntity();
        assertEquals(message,"Error");
    }

    @Test
    public void insert_succeeded_returnsNotFound(){
        //Setup
        Profile profile = GenerateDummyUser(1L);
        when(profileService.insertProfile(any(ProfileDto.class))).thenReturn(profile);

        //Act
        Response response = profileController.insert(new ProfileDto());

        //Assert
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        Profile insertedProfile  = (Profile) response.getEntity();
        assertEquals(insertedProfile,profile);
    }


    private Profile GenerateDummyUser(long id){
        return new Profile(id,"John","Doe", "info@johndoe.com", 22);
    }
}
