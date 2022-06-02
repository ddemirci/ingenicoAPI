package com.ingenico.service;

import com.ingenico.helpers.DocumentNumerator;
import com.ingenico.model.Document;
import com.ingenico.model.Profile;
import com.ingenico.model.dto.DocumentDto;
import com.ingenico.repository.DocumentRepository;
import com.ingenico.repository.ProfileRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/document")
public class DocumentService extends BaseService {

    private @Inject DocumentRepository documentRepository;
    private @Inject ProfileRepository profileRepository;

    @GET
    @Path("/profile/{profileId}")
    @Produces(MediaType.APPLICATION_JSON)

    public Response GetAll(@PathParam("profileId") Long profileId) {
        Profile owner = profileRepository.get(profileId);
        if(owner == null)
            return NotFound("Profile not found");

        List<Document> documents = documentRepository.getAll(profileId);
        return OkWithEntity(documents);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(DocumentDto documentDto) {

        Profile owner = profileRepository.get(documentDto.profileId);
        if(owner == null)
            return NotFound("Profile not found");

        long documentId = DocumentNumerator.getNumerator().getCurrentCounter();
        Document document = new Document(documentId, documentDto.name, documentDto.profileId);
        Document result = documentRepository.insert(document);
        return Created(result);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(DocumentDto documentDto, @PathParam("id") Long id) {
        Profile owner = profileRepository.get(documentDto.profileId);
        if(owner == null)
            return NotFound("Profile not found");

        Document document = documentRepository.get(id, owner.getId());
        if(document == null)
            return NotFound("Document not found"); //Or document may not be owned by given profile.

        document.setName(documentDto.name);
        Document result = documentRepository.update(document);
        return OkWithEntity(result);
    }

    @DELETE
    @Path("/{id}/profile/{profileId}")
    public Response delete( @PathParam("id") Long id, @PathParam("profileId") Long profileId){
        Profile owner = profileRepository.get(profileId);
        if(owner == null)
            return NotFound("Profile not found");

        Document document = documentRepository.get(id, owner.getId());
        if(document == null)
            return NotFound("Document not found"); //Or document may not be owned by given profile.

        boolean response = documentRepository.delete(document.getId());
        return response ? Ok() : NotFound("Deletion failed.");
    }

}
