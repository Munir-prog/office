package com.mdev.controller;

import com.mdev.entity.Task;
import com.mdev.service.TaskService;
import com.mdev.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/viewAll")
    public String tasks(Model model){
        List<Task> tasks = userService.getTasks();
        model.addAttribute("tasks", tasks);
        return "table/tableTask";
    }

    @GetMapping("/view/{id}")
    public String task(@PathVariable Long id, Model model){
        Task task = taskService.getTask(id);
        model.addAttribute("task", task);
        return "view/task";
    }
}
