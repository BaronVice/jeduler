package com.bv.pet.jeduler.entities;

import com.bv.pet.jeduler.entities.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "UniqueName", columnNames = {"name"})
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Short id;

    private String name;

    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Task> tasks;

    @PreRemove
    private void removeCategoryAssociations(){
        for (Task task : this.tasks){
            task.getCategories().remove(this);
        }
    }
}
