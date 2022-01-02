package com.mdev.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "tasks")
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private String patronymic;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoles role;

    private LocalDate birthday;

    private String signDoc;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;
}
