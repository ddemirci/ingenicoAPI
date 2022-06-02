package com.ingenico.repository;

import com.ingenico.model.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DocumentRepository {
    private static HashMap<Long, Document> documentRepository = new HashMap<>();

    public Document get(Long id){
        if(!exists(id)) return null;
        return documentRepository.get(id);
    }

    public boolean insert(Document document){
        Document insertedDocument = documentRepository.put(document.getId(), document);
        return Objects.equals(insertedDocument,document);
    }

    public boolean update(Document document){

        Document updatedDocument =  documentRepository.put(document.getId(), document);
        return Objects.equals(updatedDocument,document);
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
