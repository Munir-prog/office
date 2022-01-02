package com.mdev.repository;

import com.mdev.entity.Task;
import com.mdev.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
