package com.bv.pet.jeduler.applicationrunners;

import com.bv.pet.jeduler.entities.Statistics;
import com.bv.pet.jeduler.entities.TasksAtDay;
import com.bv.pet.jeduler.repositories.StatisticsRepository;
import com.bv.pet.jeduler.repositories.TasksAtDayRepository;
import com.bv.pet.jeduler.services.statistics.StatisticsType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Update required due to multiple users
 */
//@Component
@RequiredArgsConstructor
@Deprecated(forRemoval = true)
public class StatisticsRunner implements ApplicationRunner {

//    private final StatisticsRepository statisticsRepository;
//    private final TasksAtDayRepository tasksAtDayRepository;
//
    @Override
    public void run(ApplicationArguments args) {
//        checkStatisticsTable();
//        checkTasksAtDayTable();
    }
//
//    private void checkStatisticsTable(){
//        assertTableSizesForShort(
//                StatisticsType.values().length,
//                statisticsRepository.count()
//        );
//
//        short statisticsEnumSize = (short) StatisticsType.values().length;
//        short realSize = (short) statisticsRepository.count();
//
//        List<Statistics> list = new ArrayList<>();
//        long val = 0L;
//        while (realSize < statisticsEnumSize){
//            list.add(new Statistics(realSize, val));
//            realSize++;
//        }
//
//
//        statisticsRepository.saveAll(list);
//    }
//
//    private void checkTasksAtDayTable(){
//        assertTableSizesForShort(tasksAtDayRepository.count());
//        // TODO: move to properties
//        short days = 3660; // days amount in 10 years
//        short realSize = (short) tasksAtDayRepository.count();
//
//        List<TasksAtDay> list = new ArrayList<>();
//        short val = (short) 0;
//        while (realSize < days){
//            list.add(new TasksAtDay(realSize, val));
//            realSize++;
//        }
//
//        tasksAtDayRepository.saveAll(list);
//    }
//
//    private void assertTableSizesForShort(Number... numbers){
//        if (Arrays.stream(numbers).anyMatch(num -> num.longValue() > Short.MAX_VALUE)){
//            throw new RuntimeException("Overflow for short type");
//        }
//    }
}
