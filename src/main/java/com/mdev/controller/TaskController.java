package com.mdev.controller;

import com.mdev.entity.Task;
import com.mdev.service.TaskService;
import com.mdev.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/viewAll")
    public String tasks(Model model){
        List<Task> tasks = taskService.getTasks();
        model.addAttribute("tasks", tasks);
        return "table/tableTask";
    }

    @GetMapping("/view/{id}")
    public String task(@PathVariable Long id, Model model){
        Task task = taskService.getTask(id);
        model.addAttribute("task", task);
        return "view/task";
    }

    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable Long id, Model model){
        if (id != 0){
            var task = taskService.getTask(id);
            model.addAttribute("task", task);
        } else {
            model.addAttribute("task", new Task());
        }
        return "edit/editTask";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute Task task){
        taskService.save(task);
        return "redirect:/task/viewAll";
    }
}
