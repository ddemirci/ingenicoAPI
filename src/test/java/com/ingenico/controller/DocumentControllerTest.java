package com.ingenico.controller;

import com.ingenico.model.Document;
import com.ingenico.model.Profile;
import com.ingenico.model.dto.DocumentDto;
import com.ingenico.service.DocumentService;
import com.ingenico.service.ProfileService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;

import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class DocumentControllerTest {

    @InjectMocks
    private DocumentController documentController;
    @Mock
    private DocumentService documentService;
    @Mock
    private ProfileService profileService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAll_profileDoesNotExist_returnsNotFound(){
        //Setup
        when(profileService.getProfile(anyLong())).thenReturn(null);
        //Act
        Response response = documentController.getAll(anyLong());
        //Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        String message = (String) response.getEntity();
        assertEquals(message,"Profile not found");
    }

    @Test
    public void getAll_profileExists_returnsEmptyCollection(){
        //Setup
        Profile profile = GenerateDummyUser(1L);
        when(profileService.getProfile(anyLong())).thenReturn(profile);
        when(documentService.getAllDocumentsOfProfile(anyLong())).thenReturn(Collections.emptyList());

        //Act
        Response response = documentController.getAll(anyLong());

        //Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        List<Document> returnedDocumentList = (List<Document>) response.getEntity();
        assertEquals(returnedDocumentList.size(), 0);
    }

    @Test
    public void getAll_profileExists_returnsDocuments(){
        //Setup
        Profile profile = GenerateDummyUser(1L);
        Document doc1 = GenerateDummyDocument(1L,1L);
        Document doc2 = GenerateDummyDocument(2L,1L);
        List<Document> docList = new ArrayList<>();
        docList.add(doc1);
        docList.add(doc2);

        when(profileService.getProfile(anyLong())).thenReturn(profile);
        when(documentService.getAllDocumentsOfProfile(anyLong())).thenReturn(docList);

        //Act
        Response response = documentController.getAll(anyLong());

        //Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        List<Document> returnedDocumentList = (List<Document>) response.getEntity();
        assertEquals(returnedDocumentList.size(), docList.size());
    }

    @Test
    public void insert_profileDoesNotExist_returnsNotFound(){
        //Setup
        DocumentDto documentDto = GenerateDummyDocumentDto(1L);
        when(profileService.getProfile(anyLong())).thenReturn(null);

        //Act
        Response response = documentController.insert(documentDto);

        //Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        String message = (String) response.getEntity();
        assertEquals(message,"Profile not found");
    }


    public void insert_failed_returnsNotFound(){
        //Setup
        Profile profile = GenerateDummyUser(1L);
        DocumentDto documentDto = GenerateDummyDocumentDto(1L);
        when(profileService.getProfile(anyLong())).thenReturn(profile);
        when(documentService.insertDocument(any(DocumentDto.class))).thenReturn(null);

        //Act
        Response response = documentController.insert(documentDto);

        //Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        String message  = (String) response.getEntity();
        assertEquals(message,"Error");
    }

    @Test
    public void insert_profileExists_returnsInsertedDocument(){
        //Setup
        Profile profile = GenerateDummyUser(1L);
        Document document = GenerateDummyDocument(1L,1L);
        DocumentDto documentDto = GenerateDummyDocumentDto(1L);
        when(profileService.getProfile(anyLong())).thenReturn(profile);
        when(documentService.insertDocument(any(DocumentDto.class))).thenReturn(document);

        //Act
        Response response = documentController.insert(documentDto);

        //Assert
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        Document entity = (Document) response.getEntity();
        assertEquals(document, entity);
    }

    @Test
    public void update_profileDoesNotExist_returnsNotFound(){
        //Setup
        DocumentDto documentDto = GenerateDummyDocumentDto(1L);
        when(profileService.getProfile(anyLong())).thenReturn(null);

        //Act
        Response response = documentController.update(1L, documentDto);

        //Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        String message = (String) response.getEntity();
        assertEquals(message,"Profile not found");
    }

    @Test
    public void update_documentDoesNotExist_returnsNotFound(){
        //Setup
        Profile profile = GenerateDummyUser(1L);
        DocumentDto documentDto = GenerateDummyDocumentDto(1L);
        when(profileService.getProfile(anyLong())).thenReturn(profile);
        when(documentService.getDocument(anyLong())).thenReturn(null);
        //Act
        Response response = documentController.update(1L, documentDto);

        //Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        String message = (String) response.getEntity();
        assertEquals(message,"Document not found");
    }

    @Test
    public void update_documentDoesNotBelongsToProfile_returnsForbidden(){
        //Setup
        Profile profile = GenerateDummyUser(1L);
        Document document = GenerateDummyDocument(1L,2L);
        DocumentDto documentDto = GenerateDummyDocumentDto(2L);
        when(profileService.getProfile(anyLong())).thenReturn(profile);
        when(documentService.getDocument(anyLong())).thenReturn(document);
        //Act
        Response response = documentController.update(1L, documentDto);

        //Assert
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
        String message = (String) response.getEntity();
        assertEquals(message,"User is not authorized to perform this action");
    }

    @Test
    public void update_userDocumentMatches_returnsUpdatedEntity(){
        //Setup
        Profile profile = GenerateDummyUser(1L);
        Document document = GenerateDummyDocument(1L,1L);
        DocumentDto documentDto = GenerateDummyDocumentDto(1L);
        documentDto.name = "UpdatedDocument";
        when(profileService.getProfile(anyLong())).thenReturn(profile);
        when(documentService.getDocument(anyLong())).thenReturn(document);
        //Act
        Response response = documentController.update(1L, documentDto);

        //Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Document updatedDocument  = (Document) response.getEntity();
    }

    private Profile GenerateDummyUser(long id){
        return new Profile(id,"John","Doe", "info@johndoe.com", 22);
    }

    private Document GenerateDummyDocument(long id, long profileId){
        return new Document(id,"DummyDoc", profileId);
    }

    private DocumentDto GenerateDummyDocumentDto(long profileId){
        DocumentDto docDto = new DocumentDto();
        docDto.profileId = profileId;
        return docDto;
    }
}
