package com.bv.pet.jeduler.entities.user;

import jakarta.persistence.*;
import lombok.*;

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
}
