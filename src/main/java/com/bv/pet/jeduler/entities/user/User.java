package com.bv.pet.jeduler.entities.user;

import com.bv.pet.jeduler.entities.ApplicationEntity;
import com.bv.pet.jeduler.entities.Category;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.entities.TelegramChat;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

import static jakarta.persistence.CascadeType.*;

@Entity
/*
                            !!! WARNING !!!
        table name "user" is reserved word - use users instead
 */
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = "username", name = "username_unique")
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements ApplicationEntity<Short> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Short id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, cascade = {REFRESH, MERGE, PERSIST, REMOVE}, mappedBy = "user")
    private TelegramChat telegramChat;

    @OneToMany(fetch = FetchType.LAZY, cascade = {REFRESH, MERGE, PERSIST, REMOVE}, mappedBy = "user")
    private List<Category> categories;

    @OneToMany(fetch = FetchType.LAZY, cascade = {REFRESH, MERGE, PERSIST, REMOVE}, mappedBy = "user")
    private List<Task> tasks;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getUsername().equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }
}
