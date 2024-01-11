package com.bv.pet.jeduler.services.populate;

import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.entities.user.User;
import com.bv.pet.jeduler.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PopulateService implements IPopulateService {
    private final ApplicationInfo applicationInfo;
    private final EntitiesGenerator generator;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final SubtaskRepository subtaskRepository;
    private final NotificationRepository notificationRepository;
    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    @Override
    @Transactional
    public void addUsers(int amount, int categoriesPerUser, int tasksPerUser, int subtasksPerTask) {
        amount = setMaxUsersAmount(amount);
        categoriesPerUser = setMaxCategoriesAmount(categoriesPerUser);
        tasksPerUser = setMaxTasksAmount(tasksPerUser);
        subtasksPerTask = setMaxSubtasksAmount(subtasksPerTask);


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

    // TODO: In entities generator perhaps?
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
