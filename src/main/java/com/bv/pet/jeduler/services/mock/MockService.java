package com.bv.pet.jeduler.services.mock;

import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.config.carriers.Generators;
import com.bv.pet.jeduler.entities.user.User;
import com.bv.pet.jeduler.repositories.*;
import lombok.RequiredArgsConstructor;
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
    public void addCategories(int amount, List<Short> userIds, List<Integer> taskIds) {

    }

    @Override
    public void addSubtasks(int taskId, int amount) {

    }

    @Override
    public void addNotification(int taskId, Date date) {

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
