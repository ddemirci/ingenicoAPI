package com.ingenico.controller;

import com.ingenico.model.Profile;
import com.ingenico.model.dto.ProfileDto;
import com.ingenico.service.ProfileService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/profile")
public class ProfileController extends BaseController {
    private @Inject ProfileService profileService;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response Get(@PathParam("id") Long id) {
        System.out.println("Profile//GET");
        Profile profile = profileService.getProfile(id);
        return profile == null
                ? NotFound("Requested profile was not found.")
                : OkWithEntity(profile);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetAll() {
        List<Profile> profiles = profileService.getAllProfiles();
        return OkWithEntity(profiles);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(ProfileDto profileDto) {
        Profile insertedProfile = profileService.insertProfile(profileDto);
        return insertedProfile != null ? Created(insertedProfile) : NotFound("Error");
    }
}
