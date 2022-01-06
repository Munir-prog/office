package com.mdev.service;

import com.mdev.entity.Document;
import com.mdev.entity.User;
import com.mdev.entity.UserRoles;
import com.mdev.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DocumentServiceTest {

    private final Path root = Paths.get("document");

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private TaskRepository taskRepository;

    @Test
    void firstLetterShouldBeInUpperCase() {
        var documentService = new DocumentService(null, null);
        var isFirstLetterUpper = documentService.isNameStartWithUpperCase("This is string with upper case first letter");
        assertThat(isFirstLetterUpper).isTrue();
    }


    @Test
    void shouldThrowWhenFirstLetterIsLowerCase(){
        var documentService = new DocumentService(null, null);
        assertThrows(
                    IllegalArgumentException.class,
                    () -> documentService.isNameStartWithUpperCase("idawd w")
            );
    }

    @Test
    void shouldGetDocumentById(){
        var documentService = new DocumentService(documentRepository, taskRepository);
        var document = Document.builder()
                .id(12L)
                .tittle("Car")
                .theme("Car")
                .createdDate(LocalDate.now())
                .pathToFile("path/to/file.jpg")
                .signed(false)
                .task(null)
                .build();

        Mockito.when(documentRepository.findById(12L)).thenReturn(Optional.of(document));

        var serviceDocument = documentService.getDocument(12L);
        assertThat(serviceDocument.getId()).isEqualTo(document.getId());
        assertThat(serviceDocument.getTittle()).isEqualTo(document.getTheme());

    }

}