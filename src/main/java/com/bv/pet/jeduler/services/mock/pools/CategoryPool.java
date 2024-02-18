package com.bv.pet.jeduler.services.mock.pools;

import com.bv.pet.jeduler.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryPool extends ObjectPool<Category> {

    public CategoryPool(){
        super();
    }

    @Override
    protected Category create() {
        String name = createName();
        int color = random.nextInt(100000, 999999);

        return Category.builder()
                .name(name)
                .color(String.format("#%d", color))
                .build();
    }

    private String createName(){
        String name = faker.color().name() + random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        return String.format("Mock%s", name);
    }

    @Override
    public void expire(Category o) {

    }

    @Override
    protected void nullIds(Category o) {
        o.setName(createName());
        o.setId(null);
    }
}
