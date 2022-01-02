package com.mdev.controller;

import com.mdev.entity.Task;
import com.mdev.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/tasks")
    public String tasks(Model model){
        List<Task> tasks = taskService.getTasks();
        model.addAttribute("tasks", tasks);
        return "table/tableTask";
    }
}
