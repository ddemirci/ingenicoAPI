package com.ingenico;

import com.ingenico.model.Document;
import com.ingenico.model.Profile;
import com.ingenico.repository.DocumentRepository;
import com.ingenico.repository.ProfileRepository;
import com.ingenico.service.DocumentService;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class DocumentTest {

    @InjectMocks
    public DocumentService documentService;
    @Mock
    public ProfileRepository profileRepository;
    @Mock
    public DocumentRepository documentRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this); //does not mocks
        documentService = new DocumentService();
    }

    /*
    @Test
    public void Document_GetAllProfileNotExists_ReturnsNotFound(){
        //Setup
        when(profileRepository.get(anyLong())).thenReturn(null);

        //Act
        Response response = documentService.GetAll(123L);

        //Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }


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
        List<Document> returnedDocumentList = response.readEntity(new GenericType<>(){} );
        assertEquals(documentList.size(), returnedDocumentList.size());
    }
    */
}
