package com.bv.pet.jeduler.services.mock.generators.tasks;

import com.bv.pet.jeduler.entities.Category;
import com.bv.pet.jeduler.entities.Subtask;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.entities.user.Role;
import com.bv.pet.jeduler.entities.user.User;
import com.bv.pet.jeduler.services.mock.generators.CategoryGenerator;
import com.bv.pet.jeduler.services.mock.generators.SubtaskGenerator;
import com.bv.pet.jeduler.services.mock.generators.TaskGenerator;

import java.util.List;


public class GenerateUserTask extends GenerateTask<User> {
    private CategoryGenerator categoryGenerator;
    private TaskGenerator taskGenerator;
    private SubtaskGenerator subtaskGenerator;

    public GenerateUserTask(List<User> list){
        super(list);
        categoryGenerator = new CategoryGenerator();
        taskGenerator = new TaskGenerator();
        subtaskGenerator = new SubtaskGenerator();
    }

    @Override
    public void run() {
        generate();
    }

    @Override
    public void generate() {
        int first = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        int second = random.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        String name = faker.lorem().word();
        // 123
        String MOCK_USER_PASSWORD =
                "$argon2id$v=19$m=16384,t=2,p=1$ucTBGBGVW2SmbpmGEQlkJw$P61VQNQilS+zeoU9Zyg8joaSkM8nUwHG+HcyJprBDSQ";

        List<Category> categories = categoryGenerator.generate(random.nextInt(5, 10));
        List<Task> tasks = taskGenerator.generate(random.nextInt(4, 30));
        User user = User.builder()
                .username(
                        String.format(
                                "%s%d@mock%d.com",
                                name,
                                first,
                                second
                        )
                )
                .password(MOCK_USER_PASSWORD)
                .role(Role.USER)
                .build();

        connectEntities(user, categories, tasks);
        list.add(user);
    }

    private void connectEntities(
            User user,
            List<Category> categories,
            List<Task> tasks
    ){
        user.setTasks(tasks);
        user.setCategories(categories);

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
                    List<Subtask> subtasks = subtaskGenerator.generate(random.nextInt(3, 6));
                    subtasks.forEach(s -> s.setTask(t));
                    t.setSubtasks(subtasks);
                }
        );
    }
}
