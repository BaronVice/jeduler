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
public class Task implements Comparable<Task> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = REFRESH)
    @JoinTable(
            name = "task_category",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    private String description;

    private Instant startsAt;

    private Instant expiresAt;

    @OneToOne(mappedBy = "task", cascade = ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Notification notification;

    @Override
    public int compareTo(Task o) {
        return this.name.compareTo(o.name);
    }
}
