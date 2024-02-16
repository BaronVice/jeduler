package com.bv.pet.jeduler.datacarriers.dtos;

public record SubtaskDto(
        String name,
        boolean isCompleted,
        short orderInList
){
}
