package com.bv.pet.jeduler.datacarriers.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubtaskDto {
    private Long id;

    private String name;

    private boolean isCompleted;
}
