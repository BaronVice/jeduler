package com.bv.pet.jeduler.services.mock;

import com.bv.pet.jeduler.application.cache.UserInfo;
import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.config.carriers.Generators;
import com.bv.pet.jeduler.entities.Category;
import com.bv.pet.jeduler.entities.Notification;
import com.bv.pet.jeduler.entities.Subtask;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.entities.user.User;
import com.bv.pet.jeduler.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
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
    public void addUsers(int amount) {
        amount = setMaxUsersAmount(amount);
        List<User> users = generators.userGenerator().generate(amount);
        userRepository.saveAll(users);

        List<Short> userIds = users.stream().map(User::getId).collect(Collectors.toList());
        applicationInfo.addUsers(userIds);
        addIdsOfGeneratedEntities(
                applicationInfo.mockInfo().getUserIds(),
                userIds
        );
        System.gc();
    }

    @Override
    @Transactional
    public void addTasks(int amount, short userId) {
        // TODO: its easier to look in applicationInfo if it exist
        User user = userRepository.getReferenceById(userId);
        amount = setMaxTasksAmount(amount, userId);
        if (amount == 0) return;

        List<Task> tasks = generators.taskGenerator().generate(amount);
        tasks.forEach(t -> t.setUser(user));

        // REPLACED INSTEAD OF APPEND
        if (user.getId() != null){
            taskRepository.saveAll(tasks);
            applicationInfo.userInfoTasks().changeValue(
                    user.getId(),
                    (short) amount
            );
            addIdsOfGeneratedEntities(
                    applicationInfo.mockInfo().getTaskIds(),
                    tasks.stream().map(Task::getId).collect(Collectors.toList())
            );

            return;
        }
        user.setTasks(tasks);
        userRepository.save(user);

        short id = user.getId();
        addMockUser(id);
        applicationInfo.userInfoTasks().setValue(
                id,
                (short) user.getTasks().size()
        );
    }

    @Override
    @Transactional
    public void addCategories(int amount, short userId) {
        User user = userRepository.findById(userId).orElse(generators.userGenerator().generateOne());
        amount = setMaxCategoriesAmount(amount, userId);

        List<Category> categories = generators.categoryGenerator().generate(amount);
        categories.forEach(c -> c.setUser(user));

        if (user.getId() != null){
            categoryRepository.saveAll(categories);
            applicationInfo.userInfoCategories().changeValue(
                    user.getId(),
                    (short) amount
            );
            addIdsOfGeneratedEntities(
                    applicationInfo.mockInfo().getCategoryIds(),
                    categories.stream().map(Category::getId).collect(Collectors.toList())
            );
            return;
        }

        user.setCategories(categories);
        userRepository.save(user);

        short id = user.getId();
        addMockUser(id);
        applicationInfo.userInfoCategories().setValue(
                id,
                (short) user.getCategories().size()
        );
    }

    @Override
    @Transactional
    public void addSubtasks(int taskId, int amount) {
        Task task = taskRepository.findById(taskId).orElse(generateTaskIfNull());
        amount = setMaxSubtasksAmount(amount) - listSizeOrZeroIfNull(task.getSubtasks());

        List<Subtask> subtasks = generators.subtaskGenerator().generate(amount);
        task.setSubtasks(subtasks);
        subtasks.forEach(s -> s.setTask(task));

        if (taskNotExistOrElseSaveTask(task)){
            generateUserForTask(task);
            return;
        }

        addIdsOfGeneratedEntities(
                applicationInfo.mockInfo().getSubtaskIds(),
                subtasks.stream().map(Subtask::getId).collect(Collectors.toList())
        );
    }

    @Override
    @Transactional
    // TODO: add in scheduler
    public void addNotification(int taskId, Date date) {
        Task task = taskRepository.findById(taskId).orElse(generateTaskIfNull());
        Notification notification = generators.notificationGenerator().generateOne();
        notification.setNotifyAt(date.toInstant());

        task.setNotification(notification);
        notification.setTask(task);

        if (taskNotExistOrElseSaveTask(task)){
            generateUserForTask(task);
            return;
        }

        addIdsOfGeneratedEntities(
                applicationInfo.mockInfo().getNotificationIds(),
                notification.getId()
        );
    }

    private Task generateTaskIfNull(){
        return generators.taskGenerator().generateOne();
    }

    private void addMockUser(short id){
        applicationInfo.mockInfo().getUserIds().add(id);
        applicationInfo.addUser(id);
    }

    private boolean taskNotExistOrElseSaveTask(Task task){
        if (task.getId() != null){
            taskRepository.save(task);
            return false;
        }
        return true;
    }

    private boolean saveUserIfExist(User user){
        if (user.getId() != null){
            userRepository.save(user);
            return true;
        }
        return false;
    }

    private void generateUserForTask(Task task){
        User user = generators.userGenerator().generateOne();
        user.setTasks(List.of(task));
        task.setUser(user);

        userRepository.save(user);
        short userId = user.getId();

        addMockUser(userId);
        applicationInfo.userInfoTasks().changeValue(userId, (short) 1);
    }

    @Async
    public <T> void addIdsOfGeneratedEntities(List<T> list, T id){
        list.add(id);
    }

    @Async
    public <T> void addIdsOfGeneratedEntities(List<T> list, List<T> ids){
        list.addAll(ids);
    }

    private int listSizeOrZeroIfNull(List<?> list){
        return (list == null ? 0 : list.size());
    }

    private int setMaxUsersAmount(int amount){
        return Math.min(amount, 1000 - applicationInfo.userAmount().getAmount());
    }

    private int setMaxTasksAmount(int amount, short userId){
        return Math.min(amount, 10000 - applicationInfo.userInfoTasks().getOrElseZero(userId));
    }

    private int setMaxCategoriesAmount(int amount, short userId){
        return Math.min(amount, 20 - applicationInfo.userInfoCategories().getOrElseZero(userId));
    }

    @Deprecated(since = "wrong calculations")
    private int setMaxSubtasksAmount(int amount){
        return Math.min(amount, 20);
    }
}
