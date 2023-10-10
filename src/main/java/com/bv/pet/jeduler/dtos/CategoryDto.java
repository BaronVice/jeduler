package com.bv.pet.jeduler.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CategoryDto implements Comparable<CategoryDto> {
    private Long id;

    @NotNull (message = "Give it a name")
    private String name;

    @NotNull (message = "If you'll get tired of that, add a random option")
    private String color;

    @Override
    public int compareTo(CategoryDto o) {
        if (this.name.equals(o.name)){
            return 1;
        }
        return this.name.compareTo(o.name);
    }
}
