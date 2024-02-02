package com.bv.pet.jeduler.services.mock;

import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.config.carriers.Generators;
import com.bv.pet.jeduler.entities.Category;
import com.bv.pet.jeduler.entities.Notification;
import com.bv.pet.jeduler.entities.Subtask;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.entities.user.User;
import com.bv.pet.jeduler.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MockService implements IMockService {
    private final ApplicationInfo applicationInfo;
    private final Generators generators;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final SubtaskRepository subtaskRepository;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void addUsers(int amount, int categoriesPerUser, int tasksPerUser, int subtasksPerTask) {
        amount = setMaxUsersAmount(amount);
        categoriesPerUser = setMaxCategoriesAmount(categoriesPerUser);
        tasksPerUser = setMaxTasksAmount(tasksPerUser);
        subtasksPerTask = setMaxSubtasksAmount(subtasksPerTask);

        List<User> users = generators.userGenerator().generate(amount);
        userRepository.saveAllAndFlush(users);

        // TODO: in another method
        List<Short> userIds = users.stream().map(User::getId).collect(Collectors.toList());
        applicationInfo.addUsers(userIds);
        applicationInfo.mockInfo().getUserIds().addAll(userIds);
    }

    @Override
    public void addTasks(short userId, int amount, int subtasksPerTask) {

    }

    @Override
    @Transactional
    public void addCategories(int amount, List<Short> userIds, List<Integer> taskIds) {
        List<Category> categories = generators.categoryGenerator().generate(amount);
        categoryRepository.saveAll(categories);

        addIdsOfGeneratedEntities(
                applicationInfo.mockInfo().getCategoryIds(),
                categories.stream().map(Category::getId).collect(Collectors.toList())
        );
    }

    @Override
    @Transactional
    public void addSubtasks(int taskId, int amount) {
        amount = setMaxSubtasksAmount(amount);
        Task task = taskRepository.findById(taskId).orElse(generateTaskIfNull());
        List<Subtask> subtasks = generators.subtaskGenerator().generate(amount);

        task.setSubtasks(subtasks);
        subtasks.forEach(s -> s.setTask(task));

        boolean isTaskGenerated = task.getId() == null;
        taskRepository.save(task);
        addIfTaskIsGenerated(
                isTaskGenerated,
                task.getId(),
                applicationInfo.mockInfo().getNotificationIds(),
                subtasks.stream().map(Subtask::getId).collect(Collectors.toList())
        );
    }

    @Override
    @Transactional
    public void addNotification(int taskId, Date date) {
        Task task = taskRepository.findById(taskId).orElse(generateTaskIfNull());
        Notification notification = generators.notificationGenerator().generate(1).get(0);
        notification.setNotifyAt(date.toInstant());

        task.setNotification(notification);
        notification.setTask(task);

        boolean isTaskGenerated = task.getId() == null;
        taskRepository.save(task);
        addIfTaskIsGenerated(
                isTaskGenerated,
                task.getId(),
                applicationInfo.mockInfo().getNotificationIds(),
                List.of(notification.getId())
        );
    }

    private Task generateTaskIfNull(){
        return generators.taskGenerator().generate(1).get(0);
    }

    private <T> void addIfTaskIsGenerated(
            boolean isTaskGenerated,
            int taskId,
            List<T> mockInfoList,
            List<T> toAdd
    ){
        if (isTaskGenerated){
            addIdsOfGeneratedEntities(
                    applicationInfo.mockInfo().getTaskIds(),
                    taskId
            );
        } else {
            addIdsOfGeneratedEntities(
                    mockInfoList,
                    toAdd
            );
        }
    }

    @Async
    public <T> void addIdsOfGeneratedEntities(List<T> list, T id){
        list.add(id);
    }

    @Async
    public <T> void addIdsOfGeneratedEntities(List<T> list, List<T> ids){
        list.addAll(ids);
    }

    private int setMaxUsersAmount(int amount){
        return Math.min(amount, 100);
    }

    private int setMaxTasksAmount(int amount){
        return Math.min(amount, 1000);
    }

    private int setMaxCategoriesAmount(int amount){
        return Math.min(amount, 20);
    }

    private int setMaxSubtasksAmount(int amount){
        return Math.min(amount, 20);
    }
}
