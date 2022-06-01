package com.ingenico.repository;

import com.ingenico.model.Document;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentRepository {
    private static HashMap<Long, Document> documentRepository = new HashMap<>();

    public Document get(Long id, Long ownerId){
        if(!exists(id))
            return null;
        Document document = documentRepository.get(id);
        if(!OwnsDocument(document,ownerId))
            return null;
        return document;
    }

    public Document insert(Document document){
        documentRepository.put(document.getId(), document);
        return documentRepository.put(document.getId(), document);
    }

    public Document update(Document document){
        return documentRepository.put(document.getId(), document);
    }

    public boolean delete(Long id){
        documentRepository.remove(id);
        return true;
    }

    private boolean exists(Long id) {
        return documentRepository.containsKey(id);
    }

    public List<Document> getAll(Long ownerId) {
        return documentRepository.values().stream().filter(document -> document.getProfileId().equals(ownerId)).collect(Collectors.toList());
    }

    private boolean OwnsDocument(Document document, Long ownerId){
        return document.getProfileId().equals(ownerId);
    }
}
