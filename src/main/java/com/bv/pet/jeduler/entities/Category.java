package com.bv.pet.jeduler.entities;

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

    @Pattern(regexp = "^#([a-z0-9]){6}")
    private String color;

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
