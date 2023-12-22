package com.bv.pet.jeduler.datacarriers.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CategoryDto {
    private Short id;

    private String name;

    private String color;
}
