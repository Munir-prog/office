package com.mdev.service;

import com.mdev.entity.Document;
import com.mdev.entity.Task;
import com.mdev.repository.TaskRepository;
import com.mdev.util.ContextUtil;
import com.mdev.util.NamesUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static com.mdev.util.NamesUtil.*;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final Path root = Paths.get("document");
    private final DocumentRepository documentRepository;

    public Document getDocument(Long id) {
        return documentRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Task not found"));
    }

    public List<Document> getDocuments() {
        var name = ContextUtil.getAuthorizedUserName();
        if (name.startsWith(DIRECTOR))
            return documentRepository.findAll();
        else if (name.startsWith(MANAGER))
            return documentRepository.findDocsForManager();
        else
            return documentRepository.findDocsForSpecialist();
    }

    public void save(Document document, MultipartFile file) {
        saveFileToStaticFolder(file);
        document.setCreatedDate(LocalDate.now());
        document.setTheme(document.getTittle());
        document.setPathToFile("document/" + file.getOriginalFilename());
        documentRepository.save(document);
    }

    @SneakyThrows
    public void saveFileToStaticFolder(MultipartFile file) {
        var path = Path.of("src", "main", "resources", "static", "document", file.getOriginalFilename()).toFile();
        Files.copy(file.getInputStream(), path.toPath());
    }
}
