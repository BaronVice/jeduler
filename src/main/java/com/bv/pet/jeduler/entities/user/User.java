package com.bv.pet.jeduler.entities.user;

import com.bv.pet.jeduler.entities.Category;
import com.bv.pet.jeduler.entities.Task;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.REMOVE;

@Entity
/*
                            !!! WARNING !!!
        table name "user" is reserved word - use users instead
 */
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = "username")
)
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Short id;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @Enumerated(EnumType.STRING)
    @NonNull
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, cascade = {REFRESH, MERGE, PERSIST, REMOVE}, mappedBy = "user")
    private List<Category> categories;

    @OneToMany(fetch = FetchType.LAZY, cascade = {REFRESH, MERGE, PERSIST, REMOVE}, mappedBy = "user")
    private List<Task> tasks;
}
