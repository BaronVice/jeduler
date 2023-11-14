package com.bv.pet.jeduler.controllers.interfaces;

import com.bv.pet.jeduler.dtos.StatisticsDto;
import org.springframework.http.ResponseEntity;

public interface IStatisticsController {
    ResponseEntity<StatisticsDto> getStatistics();
}
