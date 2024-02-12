package com.bv.pet.jeduler.entities;

import com.bv.pet.jeduler.entities.user.User;
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
public class Task implements UserActivity<Integer> {
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
            joinColumns = @JoinColumn(
                    name = "task_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(
                            name = "FK_TASK_CATEGORY_ID",
                            foreignKeyDefinition = "foreign key (task_id) references task(id) on delete cascade"
                    )
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id",
                    referencedColumnName = "id",
                    foreignKey = @ForeignKey(
                            name = "FK_CATEGORY_TASK_ID",
                            foreignKeyDefinition = "foreign key (category_id) references category(id) on delete cascade"
                    )
            )
    )
    private List<Category> categories;

    @Transient
    private List<Short> categoryIds;

    @OneToMany(fetch = FetchType.EAGER, cascade = {REFRESH, MERGE, PERSIST, REMOVE}, mappedBy = "task")
    private List<Subtask> subtasks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "FK_USER_ID",
                    foreignKeyDefinition = "foreign key (user_id) references users(id) on delete cascade"
            )
    )
    private User user;

    private Instant startsAt;

    private Instant lastChanged;

    @OneToOne(mappedBy = "task", cascade = ALL)
    @PrimaryKeyJoinColumn
    private Notification notification;
}
