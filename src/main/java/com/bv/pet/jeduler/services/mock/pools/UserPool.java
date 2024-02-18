package com.bv.pet.jeduler.services.mock.pools;

import com.bv.pet.jeduler.entities.ApplicationEntity;
import com.bv.pet.jeduler.entities.Category;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.entities.user.Role;
import com.bv.pet.jeduler.entities.user.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserPool extends ObjectPool<User> {
    private final TaskPool taskPool;
    private final CategoryPool categoryPool;

    public UserPool(TaskPool taskPool, CategoryPool categoryPool) {
        super();
        this.taskPool = taskPool;
        this.categoryPool = categoryPool;
    }

    @Override
    protected User create() {
        String name = createName();
        // 123
        String MOCK_USER_PASSWORD =
                "$argon2id$v=19$m=16384,t=2,p=1$ucTBGBGVW2SmbpmGEQlkJw$P61VQNQilS+zeoU9Zyg8joaSkM8nUwHG+HcyJprBDSQ";

        List<Category> categories = getEntities(categoryPool, 5, 10);
        List<Task> tasks = getEntities(taskPool, 4, 30);
        User user = User.builder()
                .username(name)
                .password(MOCK_USER_PASSWORD)
                .role(Role.USER)
                .build();

        connectEntities(user, categories, tasks);
        return user;
    }

    private String createName(){
        String name = faker.lorem().word();
        int first = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        int second = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);

        return String.format(
                "%s%d@mock%d.com",
                name,
                first,
                second
        );
    }

    private void connectEntities(User user, List<Category> categories, List<Task> tasks) {
        user.setCategories(categories);
        user.setTasks(tasks);
        categories.forEach(
                c -> {
                    c.setTasks(tasks);
                    c.setUser(user);
                }
        );
        tasks.forEach(
                t -> {
                    t.setCategories(categories);
                    t.setUser(user);
                }
        );
    }

    private <T extends ApplicationEntity<?>> List<T> getEntities(ObjectPool<T> pool, int origin, int bound){
        int i = random.nextInt(origin, bound);
        return pool.checkOut(i);
    }

    @Override
    public void expire(User o) {
        List<Category> categories = o.getCategories();
        List<Task> tasks = o.getTasks();

        categories.forEach(
                c -> {
                    c.setTasks(null);
                    c.setUser(null);
                }
        );
        tasks.forEach(
                t -> {
                    t.setCategories(null);
                    t.setUser(null);
                }
        );

        o.setCategories(null);
        o.setTasks(null);

        categoryPool.checkIn(categories);
        taskPool.checkIn(tasks);
    }

    @Override
    protected void nullIds(User o) {
        o.setUsername(createName());
        o.setId(null);
        o.getCategories().forEach(categoryPool::nullIds);
        o.getTasks().forEach(taskPool::nullIds);
    }
}
