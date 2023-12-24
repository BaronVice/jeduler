package com.bv.pet.jeduler.entities.user;

import com.bv.pet.jeduler.entities.Category;
import com.bv.pet.jeduler.entities.Task;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Short id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, cascade = {REFRESH, MERGE, PERSIST, REMOVE}, mappedBy = "user")
    private List<Category> categories;

    @OneToMany(fetch = FetchType.LAZY, cascade = {REFRESH, MERGE, PERSIST, REMOVE}, mappedBy = "user")
    private List<Task> tasks;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
