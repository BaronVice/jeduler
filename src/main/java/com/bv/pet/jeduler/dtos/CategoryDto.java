package com.bv.pet.jeduler.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDto {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String color;
}
