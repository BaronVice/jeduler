package com.bv.pet.jeduler.entities;

import com.bv.pet.jeduler.entities.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(
        indexes = {
                @Index(name = "name_idx", columnList = "name"),
                @Index(name = "starts_at_idx", columnList = "startsAt"),
                @Index(name = "priority_idx", columnList = "priority"),
                @Index(name = "user_id_idx", columnList = "user_id")
        }
)
@Getter
@Setter
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
            ),
            indexes = {
                    @Index(name = "category_id_idx", columnList = "category_id"),
                    @Index(name = "task_id_idx", columnList = "task_id")
            }
    )
    private List<Category> categories;

    @Transient
    private List<Short> categoryIds;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = {REFRESH, MERGE, PERSIST, REMOVE},
            mappedBy = "task"/*,
            orphanRemoval = true*/
    )
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
    @OneToOne(mappedBy = "task", cascade = ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private Notification notification;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return isTaskDone() == task.isTaskDone() && getPriority() == task.getPriority() && Objects.equals(getId(), task.getId()) && Objects.equals(getName(), task.getName()) && Objects.equals(getDescription(), task.getDescription()) && Objects.equals(getCategories(), task.getCategories()) && Objects.equals(getCategoryIds(), task.getCategoryIds()) && Objects.equals(getSubtasks(), task.getSubtasks()) && Objects.equals(getUser(), task.getUser()) && Objects.equals(getStartsAt(), task.getStartsAt()) && Objects.equals(getLastChanged(), task.getLastChanged()) && Objects.equals(getNotification(), task.getNotification());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), isTaskDone(), getPriority(), getStartsAt(), getLastChanged());
    }
}
