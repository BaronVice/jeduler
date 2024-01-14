package com.bv.pet.jeduler.services.mock.generators;

import com.bv.pet.jeduler.exceptions.ApplicationException;
import com.bv.pet.jeduler.services.mock.generators.tasks.GenerateTask;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class Generator <T, S extends GenerateTask<T>> {
    private final Constructor<? extends S> ctor;

    @SneakyThrows
    protected Generator(Class<? extends S> ctor){
        this.ctor = ctor.getConstructor(List.class);
    }

    public List<T> generate(int amount) {
        List<T> generated = Collections.synchronizedList(new LinkedList<>());
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        submit(amount, executorService, generated);
        shutdown(executorService);

        return generated;
    }

    protected void submit(int amount, ExecutorService executorService, List<T> generated){
        for (int i = 0; i < amount; i++) {
            executorService.submit(() -> getRunnableTask(generated).run());
        }
    }

    @SneakyThrows
    protected Runnable getRunnableTask(List<T> generated){
        return ctor.newInstance(generated);
    }

    protected void shutdown(ExecutorService executorService){
        executorService.shutdown();
        try {
            boolean isExecuted = executorService.awaitTermination(10, TimeUnit.SECONDS);
            if (!isExecuted) {
                throw new InterruptedException();
            }
        } catch (InterruptedException e){
            throw new ApplicationException(
                    "Failed to execute generator",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}
