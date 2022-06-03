package com.ingenico.service;

import com.ingenico.model.Profile;
import com.ingenico.model.dto.ProfileDto;
import com.ingenico.repository.ProfileRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest {

    @InjectMocks
    private ProfileService profileService;

    @Mock
    private ProfileRepository profileRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void getProfile_profileDoesNotExist_returnsNull(){
        //Setup
        when(profileRepository.get(anyLong())).thenReturn(null);
        //Act
        Profile response = profileService.getProfile(1L);
        //Assert
        assertNull(response);
    }

    @Test
    public void getProfile_profileExists_returnsProfile(){
        //Setup
        Profile profile = GenerateDummyUser(1L);
        when(profileRepository.get(anyLong())).thenReturn(profile);
        //Act
        Profile response = profileService.getProfile(1L);
        //Assert
        assertEquals(response,profile);
    }

    @Test
    public void getAllProfiles_profilesExist_returnsProfile(){
        //Setup
        List<Profile> profileList = new ArrayList<>();
        profileList.add(GenerateDummyUser(1L));
        profileList.add(GenerateDummyUser(2L));

        when(profileRepository.getAll()).thenReturn(profileList);
        //Act
        List<Profile> response = profileService.getAllProfiles();
        //Assert
        assertEquals(response,profileList);
    }

    @Test
    public void getAllProfiles_profilesDoNotExist_returnsEmptyList(){
        //Setup
        when(profileRepository.getAll()).thenReturn(Collections.emptyList());
        //Act
        List<Profile> response = profileService.getAllProfiles();
        //Assert
        assertEquals(response,Collections.emptyList());
    }

    @Test
    public void insertProfile_failed_returnsNull(){
        //Setup
        ProfileDto dto = GenerateDummyProfileDto();
        when(profileRepository.insert(any(Profile.class))).thenReturn(false);

        //Act
        Profile response = profileService.insertProfile(dto);

        //Assert
        assertNull(response);
    }

    @Test
    public void insertProfile_succeeded_returnsProfile(){
        //Setup
        ProfileDto dto = GenerateDummyProfileDto();
        Profile profile = GenerateDummyUser(1L);
        when(profileRepository.insert(any(Profile.class))).thenReturn(true);

        //Act
        Profile response = profileService.insertProfile(dto);

        //Assert
        assertEquals(response.getName(),profile.getName());
        assertEquals(response.getSurname(),profile.getSurname());
    }

    private Profile GenerateDummyUser(long id){
        return new Profile(id,"John","Doe", "info@johndoe.com", 22);
    }

    private ProfileDto GenerateDummyProfileDto(){
        ProfileDto profileDto = new ProfileDto();
        profileDto.name = "John";
        profileDto.surname = "Doe";
        profileDto.email = "info@johndoe.com";
        profileDto.age = 22;
        return profileDto;
    }
}
