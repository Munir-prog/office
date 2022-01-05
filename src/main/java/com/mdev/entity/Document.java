package com.mdev.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@EqualsAndHashCode(exclude = "task")
@ToString(exclude = "task")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tittle;

    private String theme;

    private LocalDate createdDate;

    private String pathToFile;

    private boolean signed;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    public void setTask(Task task){
        task.setDocument(this);
        this.task = task;
    }

}
