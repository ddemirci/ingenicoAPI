package com.ingenico.repository;

import com.ingenico.model.Profile;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileRepository {
    private static HashMap<Long, Profile> profileRepository = new HashMap<>();

    public boolean insert(Profile profile){
        int prevCount = profileRepository.size();
        profileRepository.put(profile.getId(), profile);
        int currentCount = profileRepository.size();
        return currentCount == prevCount + 1;
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
