package com.mdev.service;

import com.mdev.entity.Task;
import com.mdev.entity.User;
import com.mdev.repository.TaskRepository;
import com.mdev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task getTask(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Task not found"));
    }
}
