package com.bv.pet.jeduler.entities;

import com.bv.pet.jeduler.entities.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
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
    private Integer id;

    private String name;

    private String description;

    private boolean taskDone;

    private short priority;

    @ManyToMany(fetch = FetchType.LAZY, cascade = REFRESH)
    @JoinTable(
            name = "task_category",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @OneToMany(fetch = FetchType.EAGER, cascade = {REFRESH, MERGE, PERSIST, REMOVE}, mappedBy = "task")
    private List<Subtask> subtasks;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @DateTimeFormat(pattern = "dd.mm.yyyy+hh:mm")
    private LocalDateTime startsAt;

    private LocalDateTime lastChanged;

    @OneToOne(mappedBy = "task", cascade = ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Notification notification;
}
