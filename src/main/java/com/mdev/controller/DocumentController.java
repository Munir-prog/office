package com.mdev.controller;

import com.mdev.entity.Document;
import com.mdev.entity.Task;
import com.mdev.service.DocumentService;
import com.mdev.service.TaskService;
import com.mdev.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public String editTask(@PathVariable Long id, Model model){
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

    @PostMapping("/save")
    public String saveTask(@RequestParam("file") MultipartFile file, @ModelAttribute Document document){
        documentService.save(document, file);
        return "redirect:/document/viewAll";
    }
}
