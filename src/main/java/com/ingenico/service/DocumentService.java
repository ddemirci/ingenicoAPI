package com.ingenico.service;

import com.ingenico.helpers.DocumentNumerator;
import com.ingenico.model.Document;
import com.ingenico.model.dto.DocumentDto;
import com.ingenico.repository.DocumentRepository;
import jakarta.inject.Inject;

import java.util.List;

public class DocumentService {
    private @Inject DocumentRepository documentRepository;

    public Document getDocument(Long id){
        return documentRepository.get(id);
    }

    public List<Document> getAllDocumentsOfProfile(Long profileId){
        return documentRepository.getAll(profileId);
    }

    public Document insertDocument(DocumentDto documentDto) {
        long documentId = DocumentNumerator.getNumerator().getCurrentCounter();
        Document document = new Document(documentId, documentDto.name, documentDto.profileId);
        boolean result = documentRepository.insert(document);
        return result ? document : null;
    }

    public Document updateDocument(Document document) {
        boolean result = documentRepository.update(document);
        return result ? document : null;
    }

    public boolean deleteDocument(long id){
        return documentRepository.delete(id);
    }
}
