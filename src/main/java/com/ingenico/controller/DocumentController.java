package com.ingenico.controller;

import com.ingenico.model.Document;
import com.ingenico.model.Profile;
import com.ingenico.model.dto.DocumentDto;
import com.ingenico.service.DocumentService;
import com.ingenico.service.ProfileService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/document")
public class DocumentController extends BaseController {

    private @Inject DocumentService documentService;
    private @Inject ProfileService profileService;

    @GET
    @Path("/profile/{profileId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@PathParam("profileId") Long profileId) {
        Profile owner = profileService.getProfile(profileId);
        if(owner == null)
            return NotFound("Profile not found");

        List<Document> documents = documentService.getAllDocumentsOfProfile(profileId);
        return OkWithEntity(documents);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(DocumentDto documentDto) {

        Profile owner = profileService.getProfile(documentDto.profileId);
        if(owner == null)
            return NotFound("Profile not found");

        Document insertedDocument = documentService.insertDocument(documentDto);
        return insertedDocument != null ? Created(insertedDocument) : NotFound("Error");
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id,DocumentDto documentDto) {
        Profile owner = profileService.getProfile(documentDto.profileId);
        if(owner == null)
            return NotFound("Profile not found");

        Document document = documentService.getDocument(id);
        if(document == null)
            return NotFound("Document not found");

        if(!document.getProfileId().equals(owner.getId()))
            return Forbidden();

        //There is only one property to be updated
        document.setName(documentDto.name);
        Document result = documentService.updateDocument(document);
        return OkWithEntity(result);
    }

    @DELETE
    @Path("/{id}/profile/{profileId}")
    public Response delete( @PathParam("id") Long id, @PathParam("profileId") Long profileId){
        Profile owner = profileService.getProfile(profileId);
        if(owner == null)
            return NotFound("Profile not found");

        Document document = documentService.getDocument(id);
        if(document == null)
            return NotFound("Document not found");

        if(!document.getProfileId().equals(owner.getId()))
            return Forbidden();

        boolean response = documentService.deleteDocument(id);
        return response ? Ok() : NotFound("Deletion failed.");
    }
}
