package com.mdev.service;

import com.mdev.entity.Document;
import com.mdev.entity.Task;
import com.mdev.entity.User;
import com.mdev.repository.TaskRepository;
import com.mdev.repository.UserRepository;
import com.mdev.util.ContextUtil;
import com.mdev.util.NamesUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.mdev.util.NamesUtil.*;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public Task getTask(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Task not found"));
    }

    public void save(Task task, Long id) {
        if (id == null || id == 0) {
            saveTask(task);
        } else {
            updateTask(task, id);
        }
    }

    private void updateTask(Task task, Long id) {
        var taskToUpdate = taskRepository.findById(id).get();
        taskToUpdate.setUser(setTasksUser(task));
        taskToUpdate.setTittle(task.getTittle());
        taskToUpdate.setMessage(task.getMessage());
        taskRepository.save(taskToUpdate);
    }

    private User setTasksUser(Task task) {
        if (task.getUser() != null && task.getUser().getId() != 0)
            return userRepository.findById(task.getUser().getId()).orElseThrow();
        return task.getUser();
    }

    private void saveTask(Task task) {
        task.setUser(setTasksUser(task));
        if (task.getCreateBy() == null || task.getCreateBy().equals("")) {
            var name = ContextUtil.getAuthorizedUserName();

            if (name.startsWith(DIRECTOR)) {
                task.setCreateBy(DIRECTOR);
            } else if (name.startsWith(MANAGER)) {
                task.setCreateBy(MANAGER);
            } else if (name.startsWith(SPECIALIST)) {
                task.setCreateBy(SPECIALIST);
            }
        }
        if (task.getCreatedDate() == null)
            task.setCreatedDate(LocalDate.now());

        taskRepository.save(task);
    }

    public List<Task> getTasks() {
        var name = ContextUtil.getAuthorizedUserName();
        if (name.startsWith(DIRECTOR))
            return taskRepository.findAll();
        else if (name.startsWith(MANAGER))
            return taskRepository.findTasks();
        else
            return taskRepository.findTasksByUser_Id(3L);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
