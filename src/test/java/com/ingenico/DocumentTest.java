package com.ingenico;

import com.ingenico.model.Document;
import com.ingenico.model.Profile;
import com.ingenico.repository.DocumentRepository;
import com.ingenico.repository.ProfileRepository;
import com.ingenico.service.DocumentService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;

import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class DocumentTest {

    @InjectMocks
    private DocumentService documentService;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private DocumentRepository documentRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this); //does not mocks
    }

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
        List<Document> returnedDocumentList = (List<Document>) response.getEntity();
        assertEquals(documentList.size(), returnedDocumentList.size());
    }
}
