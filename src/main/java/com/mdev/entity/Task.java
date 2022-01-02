package com.mdev.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {
        "documents",
        "taskFor"
})
@ToString(exclude = {
        "documents",
        "taskFor"
})
@Builder
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String createBy;

    private LocalDate createdDate;

    private LocalDate doneDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User taskFor;

    @OneToMany(mappedBy = "task")
    private List<Document> documents;
}
