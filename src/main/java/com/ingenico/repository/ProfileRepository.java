package com.ingenico.repository;

import com.ingenico.model.Profile;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProfileRepository {
    private static HashMap<Long, Profile> profileRepository = new HashMap<>();

    public boolean insert(Profile profile){
        Profile insertedProfile = profileRepository.put(profile.getId(), profile);
        return Objects.equals(insertedProfile, profile);
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
