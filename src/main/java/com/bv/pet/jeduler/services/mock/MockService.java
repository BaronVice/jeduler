package com.bv.pet.jeduler.services.mock;

import com.bv.pet.jeduler.application.cache.UserInfo;
import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.config.carriers.Generators;
import com.bv.pet.jeduler.entities.*;
import com.bv.pet.jeduler.entities.user.User;
import com.bv.pet.jeduler.exceptions.ApplicationException;
import com.bv.pet.jeduler.repositories.*;
import com.bv.pet.jeduler.services.mail.MailServiceImpl;
import com.bv.pet.jeduler.services.mock.generators.Generator;
import com.bv.pet.jeduler.utils.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MockService implements IMockService {
    private final ApplicationInfo applicationInfo;
    private final Generators generators;
    private final MailServiceImpl mailService;
    private final Assert anAssert;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void addUsers(int amount) {
        amount = setMaxUsersAmount(amount);
        anAssert.amountIsPositive(amount);

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
        addUserActivity(
                setMaxTasksAmount(amount, userId),
                userId,
                generators.taskGenerator(),
                taskRepository,
                applicationInfo.userInfoTasks(),
                applicationInfo.mockInfo().getTaskIds()
        );
    }

    @Override
    @Transactional
    public void addCategories(int amount, short userId) {
        addUserActivity(
                setMaxCategoriesAmount(amount, userId),
                userId,
                generators.categoryGenerator(),
                categoryRepository,
                applicationInfo.userInfoCategories(),
                applicationInfo.mockInfo().getCategoryIds()
        );
    }

    private <T extends UserActivity<ID>, ID extends Number> void addUserActivity(
            int amount,
            short userId,
            Generator<T, ?> generator,
            JpaRepository<T, ID> repository,
            UserInfo userInfo,
            List<ID> mockInfoIds
    ){
        anAssert.amountIsPositive(amount);
        anAssert.userExist(userId);

        User user = User.builder().id(userId).build();

        List<T> userActivities = generator.generate(amount);
        userActivities.forEach(a -> a.setUser(user));

        repository.saveAll(userActivities);
        userInfo.changeValue(
                userId,
                (short) amount
        );
        addIdsOfGeneratedEntities(
                mockInfoIds,
                userActivities.stream().map(UserActivity::getId).collect(Collectors.toList())
        );
    }

    @Override
    @Transactional
    public void addSubtasks(int taskId, int amount) {
        Task task = taskRepository.findById(taskId).orElse(generateTaskIfNull());
        amount = setMaxSubtasksAmount(amount, task);
        anAssert.amountIsPositive(amount);

        List<Subtask> subtasks = generators.subtaskGenerator().generate(amount);
        if (task.getSubtasks() == null)
            task.setSubtasks(new ArrayList<>());

        task.getSubtasks().addAll(subtasks);
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
    public void addNotification(int taskId, Date date) {
        Task task = taskRepository.findById(taskId).orElse(generateTaskIfNull());
        Notification notification = generators.notificationGenerator().generateOne();
        notification.setNotifyAt(date.toInstant());

        task.setNotification(notification);
        notification.setTask(task);

        if (taskNotExistOrElseSaveTask(task)){
            generateUserForTask(task);
            addNotificationInScheduler(task);

            return;
        }

        addNotificationInScheduler(task);
        addIdsOfGeneratedEntities(
                applicationInfo.mockInfo().getNotificationIds(),
                notification.getId()
        );
    }

    private void addNotificationInScheduler(Task task) {
        mailService.handNotificationInScheduler(
                applicationInfo.adminInfo().getUsername(),
                task
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
        return Math.min(
                Math.min(amount, 1000),
                1000 - applicationInfo.userAmount().getAmount()
        );
    }

    private int setMaxTasksAmount(int amount, short userId){
        return Math.min(
                Math.min(amount, 10000),
                10000 - applicationInfo.userInfoTasks().getOrElseZero(userId)
        );
    }

    private int setMaxCategoriesAmount(int amount, short userId){
        return Math.min(
                Math.min(amount, 20),
                20 - applicationInfo.userInfoCategories().getOrElseZero(userId)
        );
    }

    private int setMaxSubtasksAmount(int amount, Task task){
        return Math.min(
                Math.min(amount, 20),
                20 - listSizeOrZeroIfNull(task.getSubtasks())
        );
    }
}
