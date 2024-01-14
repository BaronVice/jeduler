package com.bv.pet.jeduler.services.mock.generators.tasks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public abstract class GenerateTask<T> implements Runnable{
    private List<T> list;
}
