package com.bv.pet.jeduler.controllers.statistics;

import com.bv.pet.jeduler.datacarriers.dtos.StatisticsDto;
import org.springframework.http.ResponseEntity;

public interface IStatisticsController {
    ResponseEntity<StatisticsDto> getStatistics();
}
