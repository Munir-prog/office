package com.mdev.service;

import com.mdev.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("SELECT d FROM Document d WHERE d.task.user.id = 2 or d.task.user.id = 3")
    List<Document> findDocsForManager();


    @Query("SELECT d FROM Document d WHERE d.task.user.id = 3")
    List<Document> findDocsForSpecialist();


}
