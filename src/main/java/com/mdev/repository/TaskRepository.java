package com.mdev.repository;

import com.mdev.entity.Task;
import com.mdev.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {


    @Query("SELECT t FROM Task t WHERE t.user.id = 2 or t.user.id = 3")
    List<Task> findTasks();

    List<Task> findTasksByUser_Id(Long id);
}
