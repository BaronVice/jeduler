package com.bv.pet.jeduler.utils;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@Deprecated(forRemoval = true)
public class InstantsCalculator {
    private static final Instant startInstant = Instant.parse("2023-01-01T00:00:00.001+08:00");

    public static short getDaysFromStart(Instant instant){
        return (short) ChronoUnit.DAYS.between(startInstant, instant);
    }
}
