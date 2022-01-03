package com.mdev.service;

import com.mdev.entity.Document;
import com.mdev.entity.Task;
import com.mdev.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final TaskRepository taskRepository;
    private final DocumentRepository documentRepository;

    public Document getDocument(Long id) {
        return documentRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Task not found"));
    }

    public List<Document> getDocuments() {
        return documentRepository.findAll();
    }
}
