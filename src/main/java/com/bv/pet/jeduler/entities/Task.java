package com.bv.pet.jeduler.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private boolean taskDone;

    @ManyToMany(fetch = FetchType.EAGER, cascade = REFRESH)
    @JoinTable(
            name = "task_category",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @OneToMany(fetch = FetchType.EAGER, cascade = {REFRESH, MERGE, PERSIST, REMOVE}, mappedBy = "task")
    private List<Subtask> subtasks;

    private Instant startsAt;

    @OneToOne(mappedBy = "task", cascade = ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Notification notification;
}
