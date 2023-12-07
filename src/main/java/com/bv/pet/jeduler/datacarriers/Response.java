package com.bv.pet.jeduler.datacarriers;

import com.bv.pet.jeduler.datacarriers.dtos.ErrorDto;
import org.springframework.http.HttpStatus;

@Deprecated(forRemoval = true)
public record Response <T>(
        HttpStatus status,
        T body
) {
}
