package com.mdev.service;

import com.mdev.entity.Document;
import com.mdev.entity.Task;
import com.mdev.repository.TaskRepository;
import com.mdev.util.ContextUtil;
import com.mdev.util.NamesUtil;
import com.mdev.util.PathUtil;
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
import java.util.Optional;

import static com.mdev.util.NamesUtil.*;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final Path root = Paths.get("document");
    private final DocumentRepository documentRepository;
    private final TaskRepository taskRepository;

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

    public void save(Document document, MultipartFile file, Long id) {
        saveFileToStaticFolder(file);
        if (isNameStartWithUpperCase(document.getTittle())) {
            if (id == null || id == 0) {
                saveDocument(document, file);
            } else {
                updateDocument(document, file, id);
            }
        }
    }

    protected boolean isNameStartWithUpperCase(String tittle) {
        String firstChar = tittle.charAt(0) + "";
        var upperCase = firstChar.toUpperCase();
        if (!firstChar.equals(upperCase))
            throw new IllegalArgumentException("the first letter isn't in uppercase");
        return true;
    }

    private void updateDocument(Document document, MultipartFile file, Long id) {
        var doc = documentRepository.findById(id).get();
        doc.setTittle(document.getTittle());
        doc.setTheme(document.getTittle());
        doc.setPathToFile(file.getOriginalFilename());
        var task = taskRepository.findById(doc.getTask().getId()).get();
        task.setDocument(doc);
        taskRepository.save(task);
    }

    private void saveDocument(Document document, MultipartFile file) {
        document.setCreatedDate(LocalDate.now());
        document.setTheme(document.getTittle());
        document.setPathToFile(file.getOriginalFilename());
        documentRepository.save(document);
    }


    @SneakyThrows
    public void saveFileToStaticFolder(MultipartFile file) {
        var path = Path.of(PathUtil.PATH_TO_DOCS, file.getOriginalFilename()).toFile();
        if (!path.exists())
            Files.copy(file.getInputStream(), path.toPath());
    }

    public void delete(Long id) {
        documentRepository.deleteById(id);
    }

    public void signDoc(Long id) {
        var document = documentRepository.findById(id).get();
        document.setSigned(true);
        document.getTask().setDoneDate(LocalDate.now());
        documentRepository.save(document);
    }
}
