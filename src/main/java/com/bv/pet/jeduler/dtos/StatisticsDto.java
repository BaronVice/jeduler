package com.bv.pet.jeduler.dtos;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StatisticsDto {
    private List<Short> tasksAtDay;
    private long tasksCompleted;
    private long tasksCreated;
    private long tasksUpdated;
    private long notificationsSent;
//    private long tasksDeleted; -- Do I need it?
}
