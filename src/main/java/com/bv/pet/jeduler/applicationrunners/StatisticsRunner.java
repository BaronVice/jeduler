package com.bv.pet.jeduler.applicationrunners;

import com.bv.pet.jeduler.entities.Statistics;
import com.bv.pet.jeduler.repositories.StatisticsRepository;
import com.bv.pet.jeduler.services.statistics.StatisticsType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatisticsRunner implements ApplicationRunner {
    private final StatisticsRepository repository;

    @Override
    public void run(ApplicationArguments args) {
        long statisticsEnumSize = StatisticsType.values().length;
        long realSize = repository.count();

        while (realSize < statisticsEnumSize){
            repository.save(new Statistics(realSize, 0L));
            realSize++;
        }
    }
}
