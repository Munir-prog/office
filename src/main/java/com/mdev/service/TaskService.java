package com.mdev.service;

import com.mdev.entity.Task;
import com.mdev.entity.User;
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

    private final UserRepository userRepository;

    public List<Task> getTasks(){
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        return user.getTasks();
    }

    public Task getTask(Long id) {
        return userRepository.findById(ta);
    }
}
