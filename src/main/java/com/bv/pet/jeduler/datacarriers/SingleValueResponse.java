package com.bv.pet.jeduler.datacarriers;

public record SingleValueResponse<T>(
        T value
) {
}
