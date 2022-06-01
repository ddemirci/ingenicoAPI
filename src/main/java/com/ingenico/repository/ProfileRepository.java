package com.ingenico.repository;

import com.ingenico.helpers.ProfileNumerator;
import com.ingenico.model.Profile;
import com.ingenico.model.dto.ProfileDto;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileRepository {
    private static HashMap<Long, Profile> profileRepository = new HashMap<>();

    public Boolean insert(Profile profile){
        profileRepository.put(profile.getId(), profile);
        return true;
    }

    public Profile get(Long id){
        if(!profileRepository.containsKey(id))
            return null;
        return profileRepository.get(id);
    }

    public List<Profile> getAll(){
        return profileRepository.values().stream().collect(Collectors.toList());
    }
}
