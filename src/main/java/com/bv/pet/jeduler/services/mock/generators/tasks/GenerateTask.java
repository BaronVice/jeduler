package com.bv.pet.jeduler.services.mock.generators.tasks;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Random;

@NoArgsConstructor
public abstract class GenerateTask<T> implements Runnable{
    protected List<T> list;
    protected Random random;
    protected Faker faker;

    public GenerateTask(List<T> list) {
        this.list = list;
        this.random = new Random();
        this.faker = new Faker();
    }
}
