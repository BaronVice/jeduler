package com.bv.pet.jeduler.dtos;

import com.bv.pet.jeduler.entities.Category;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
// TODO: add more constrains
public class TaskDto {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private List<Category> categories;

    @NotNull
    private String description;

    @NotNull
    private Instant startsAt;

    @NotNull
    private Instant expiresAt;

    private Instant notifyAt;
}
