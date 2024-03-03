package com.bv.pet.jeduler.entities;

import com.bv.pet.jeduler.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TelegramChat {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "FK_CHAT_IDS",
                    foreignKeyDefinition = "foreign key (user_id) references users(id) on delete cascade"
            )
    )
    private User user;
}
