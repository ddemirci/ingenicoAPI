package com.ingenico.service;

import com.ingenico.helpers.ProfileNumerator;
import com.ingenico.model.Profile;
import com.ingenico.model.dto.ProfileDto;
import com.ingenico.repository.ProfileRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/profile")
public class ProfileService extends BaseService{

    private  @Inject ProfileRepository profileRepository;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response Get(@PathParam("id") Long id) {
        Profile profile = profileRepository.get(id);
        return profile == null
                ? NotFound("Requested profile was not found.")
                : OkWithEntity(profile);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetAll() {
        List<Profile> profiles = profileRepository.getAll();
        return OkWithEntity(profiles);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(ProfileDto profileDto) {
        long profileId = ProfileNumerator.getNumerator().getCurrentCounter();
        Profile profile = new Profile(profileId, profileDto.name, profileDto.surname, profileDto.email, profileDto.age);
        Boolean result = profileRepository.insert(profile);
        return result ? Created(profile) : NotFound("Error");
    }
}
