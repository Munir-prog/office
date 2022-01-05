package com.mdev.controller;

import com.mdev.entity.Document;
import com.mdev.entity.Task;
import com.mdev.service.DocumentService;
import com.mdev.service.TaskService;
import com.mdev.service.UserService;
import com.mdev.util.PathUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Controller
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

    private final TaskService taskService;
    private final UserService userService;
    private final DocumentService documentService;

    @GetMapping("/viewAll")
    public String documents(Model model){
        List<Document> documents = documentService.getDocuments();
        model.addAttribute("documents", documents);
        return "table/tableDocument";
    }

    @GetMapping("/view/{id}")
    public String task(@PathVariable Long id, Model model){
        Document document = documentService.getDocument(id);
        model.addAttribute("document", document);
        return "view/document";
    }

    @GetMapping("/edit/{id}")
    public String editDocument(@PathVariable Long id, Model model){
        if (id != 0){
            model.addAttribute("taskId", id);
            var document = new Document();
            document.setTask(taskService.getTask(id));
            model.addAttribute("document", document);

        } else {
//            TODO it maybe unnecessary
            model.addAttribute("task", new Task());
        }
        return "edit/editDocument";
    }

    @GetMapping("/edited/{id}")
    public String editedDocument(@PathVariable Long id, Model model){
        if (id != 0){
            var document = documentService.getDocument(id);
            model.addAttribute("document", document);
        }
        return "edit/editDocument";
    }

    @PostMapping(value = {
            "/save/{id}",
            "/save"
    })
    public String saveDocument(@PathVariable(required = false) Long id, @RequestParam("file") MultipartFile file, @ModelAttribute Document document){
        documentService.save(document, file, id);
        return "redirect:/document/viewAll";
    }


    @SneakyThrows
    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable Long id){
        var document = documentService.getDocument(id);
        var file = new File(PathUtil.PATH_TO_DOCS + document.getPathToFile());
        var inputStreamResource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + document.getPathToFile())
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(file.length())
                .body(inputStreamResource);
    }

    @GetMapping("/delete/{id}")
    public String deleteDoc(@PathVariable Long id){
        if (id != 0){
            documentService.delete(id);
        }
        return "redirect:/document/viewAll";
    }
}
