package com.ingenico.service;

import com.ingenico.helpers.ProfileNumerator;
import com.ingenico.model.Profile;
import com.ingenico.model.dto.ProfileDto;
import com.ingenico.repository.ProfileRepository;
import jakarta.inject.Inject;

import java.util.List;

public class ProfileService {

    private  @Inject ProfileRepository profileRepository;

    //Might be a dto
    public Profile getProfile(Long id){
        return profileRepository.get(id);
    }

    public List<Profile> getAllProfiles(){
        return profileRepository.getAll();
    }

    public Profile insertProfile(ProfileDto profileDto){
        long profileId = ProfileNumerator.getNumerator().getCurrentCounter();
        Profile profile = new Profile(profileId, profileDto.name, profileDto.surname, profileDto.email, profileDto.age);
        boolean result = profileRepository.insert(profile);
        return result ? profile : null;
    }


}
