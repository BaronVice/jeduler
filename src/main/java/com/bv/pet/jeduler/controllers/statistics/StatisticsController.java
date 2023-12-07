package com.bv.pet.jeduler.controllers.statistics;

import com.bv.pet.jeduler.datacarriers.dtos.StatisticsDto;
import com.bv.pet.jeduler.services.statistics.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jeduler/statistics")
public class StatisticsController implements IStatisticsController {
    private final StatisticsService statisticsService;

    @Override
    @GetMapping
    public ResponseEntity<StatisticsDto> getStatistics() {
        return ResponseEntity.ok(
                statisticsService.getStatistics()
        );
    }
}
