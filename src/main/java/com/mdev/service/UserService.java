package com.mdev.service;

import com.mdev.entity.Task;
import com.mdev.repository.UserRepository;
import com.mdev.util.ContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<Task> getTasks(){
        var email = ContextUtil.getAuthorizedUserName();
        var user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        return user.getTasks();
    }

}
