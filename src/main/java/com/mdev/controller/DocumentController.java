package com.mdev.controller;

import com.mdev.entity.Document;
import com.mdev.entity.Task;
import com.mdev.service.DocumentService;
import com.mdev.service.TaskService;
import com.mdev.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
