package com.ingenico;

import com.ingenico.controller.DocumentController;
import com.ingenico.service.DocumentService;
import com.ingenico.service.ProfileService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;

import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class DocumentTest {

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
    public void document_getAllProfileNotExists_returnsNotFound(){
        //Setup
        when(profileService.getProfile(anyLong())).thenReturn(null);
        //Act
        Response response = documentController.GetAll(anyLong());
        //Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    /*
    @Test
    public void Document_GetAllProfileExists_ReturnsOk() {
        //Setup
        Profile profile = new Profile(1L,"John","Doe","mail@mail.com",22);
        List<Document> documentList = new ArrayList<>();
        documentList.add(new Document(1L,"Book", profile.getId()));
        when(profileRepository.get(anyLong())).thenReturn(profile);
        when(documentRepository.getAll(anyLong())).thenReturn(documentList);
        //Act
        Response response = documentService.GetAll(profile.getId());
        //Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        List<Document> returnedDocumentList = (List<Document>) response.getEntity();
        assertEquals(documentList.size(), returnedDocumentList.size());
    }
    */

}
