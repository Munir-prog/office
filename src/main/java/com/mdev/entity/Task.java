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
        "document",
        "user"
})
@ToString(exclude = {
        "document",
        "user"
})
@Builder
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tittle;

    private String message;

    private String createBy;

    private LocalDate createdDate;

    private LocalDate doneDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
//    Task for somebody
    private User user;

    @OneToOne(
            mappedBy = "task",
            cascade = CascadeType.ALL
    )
    private Document document;
}
