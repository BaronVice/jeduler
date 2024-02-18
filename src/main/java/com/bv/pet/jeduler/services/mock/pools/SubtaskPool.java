package com.bv.pet.jeduler.services.mock.pools;

import com.bv.pet.jeduler.entities.Subtask;
import org.springframework.stereotype.Component;

@Component
public class SubtaskPool extends ObjectPool<Subtask> {

    public SubtaskPool(){
        super();
    }

    @Override
    protected Subtask create() {
        return Subtask.builder()
                .name(faker.harryPotter().spell())
                .isCompleted(random.nextBoolean())
                .build();
    }

    @Override
    public void expire(Subtask o) {

    }

    @Override
    protected void nullIds(Subtask o) {
        o.setId(null);
    }
}
