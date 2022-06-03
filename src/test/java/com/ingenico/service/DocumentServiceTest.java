package com.ingenico.service;

import com.ingenico.model.Document;
import com.ingenico.model.dto.DocumentDto;
import com.ingenico.repository.DocumentRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DocumentServiceTest {
    @InjectMocks
    private DocumentService documentService;

    @Mock
    private DocumentRepository documentRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getDocument_documentDoesNotExist_returnsNull(){
        //Setup
        when(documentRepository.get(anyLong())).thenReturn(null);
        //Act
        Document response = documentService.getDocument(1L);
        //Assert
        assertNull(response);
    }

    @Test
    public void getDocument_documentExists_returnsDocument(){
        //Setup
        Document document = GenerateDummyDocument(1L,1L);
        when(documentRepository.get(anyLong())).thenReturn(document);
        //Act
        Document response = documentService.getDocument(1L);
        //Assert
        assertEquals(response,document);
    }



    @Test
    public void getAllDocumentsOfProfile_documentsExist_returnsDocuments(){
        //Setup
        List<Document> documentList = new ArrayList<>();
        documentList.add(GenerateDummyDocument(1L,1L));
        documentList.add(GenerateDummyDocument(2L,1L));

        when(documentRepository.getAll(anyLong())).thenReturn(documentList);
        //Act
        List<Document> response = documentService.getAllDocumentsOfProfile(1L);
        //Assert
        assertEquals(response,documentList);
    }

    @Test
    public void getAllDocumentsOfProfile_documentsDoNotExist_returnsEmptyList(){
        //Setup
        when(documentRepository.getAll(anyLong())).thenReturn(Collections.emptyList());
        //Act
        List<Document> response = documentService.getAllDocumentsOfProfile(1L);
        //Assert
        assertEquals(response,Collections.emptyList());
    }

    @Test
    public void insertDocument_failed_returnsNull(){
        //Setup
        DocumentDto dto = GenerateDummyDocumentDto(1L);
        when(documentRepository.insert(any(Document.class))).thenReturn(false);

        //Act
        Document response = documentService.insertDocument(dto);

        //Assert
        assertNull(response);
    }

    @Test
    public void insertDocument_succeeded_returnsDocument(){
        //Setup
        DocumentDto dto = GenerateDummyDocumentDto(1L);
        Document profile = GenerateDummyDocument(1L,1L);
        when(documentRepository.insert(any(Document.class))).thenReturn(true);

        //Act
        Document response = documentService.insertDocument(dto);

        //Assert
        assertEquals(response.getName(),profile.getName());
        assertEquals(response.getProfileId(),profile.getProfileId());
    }

    @Test
    public void updateDocument_failed_returnsNull(){
        //Setup
        Document document = GenerateDummyDocument(1L,1L);
        when(documentRepository.update(any(Document.class))).thenReturn(false);

        //Act
        Document response = documentService.updateDocument(document);

        //Assert
        assertNull(response);
    }

    @Test
    public void updateDocument_succeeded_returnsDocument(){
        //Setup
        Document document = GenerateDummyDocument(1L,1L);
        when(documentRepository.update(any(Document.class))).thenReturn(true);

        //Act
        Document response = documentService.updateDocument(document);

        //Assert
        assertEquals(response.getName(),document.getName());
        assertEquals(response.getProfileId(),document.getProfileId());
    }

    @Test
    public void deleteDocument_failed_returnsFalse(){
        //Setup
        when(documentRepository.delete(anyLong())).thenReturn(false);

        //Act
        boolean response = documentService.deleteDocument(1L);

        //Assert
        assertFalse(response);
    }

    @Test
    public void deleteDocument_succeeded_returnsTrue(){
        //Setup
        when(documentRepository.delete(anyLong())).thenReturn(true);

        //Act
        boolean response = documentService.deleteDocument(1L);

        //Assert
        assertTrue(response);
    }


    private Document GenerateDummyDocument(long id, long profileId){
        return new Document(id,"DummyDoc", profileId);
    }

    private DocumentDto GenerateDummyDocumentDto(long profileId){
        DocumentDto docDto = new DocumentDto();
        docDto.profileId = profileId;
        docDto.name = "DummyDoc";
        return docDto;
    }
}
