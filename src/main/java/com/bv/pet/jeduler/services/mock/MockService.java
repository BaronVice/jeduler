package com.bv.pet.jeduler.services.mock;

import com.bv.pet.jeduler.application.cache.UserInfo;
import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.config.carriers.Pools;
import com.bv.pet.jeduler.entities.*;
import com.bv.pet.jeduler.entities.user.User;
import com.bv.pet.jeduler.repositories.*;
import com.bv.pet.jeduler.services.mock.pools.ObjectPool;
import com.bv.pet.jeduler.services.notificationsenders.service.NotificationService;
import com.bv.pet.jeduler.utils.Assert;


import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MockService {
    private final ApplicationInfo applicationInfo;
    private final Pools pools;
    private final NotificationService notificationService;
    private final Assert anAssert;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    public void addUsers(int amount) {
        amount = setMaxUsersAmount(amount);
        anAssert.amountIsPositive(amount);

        List<User> users = pools.userPool().checkOut(amount);
        saveUsers(users);
        pools.userPool().checkIn(users);
    }

    @Transactional
    public void saveUsers(List<User> users){
        userRepository.saveAllAndFlush(users);

        List<Short> userIds = users.stream().map(User::getId).collect(Collectors.toList());
        applicationInfo.addUsers(userIds);
        addIdsOfGeneratedEntities(
                applicationInfo.mockInfo().getUserIds(),
                userIds
        );
    }

    @Transactional
    public void addTasks(int amount, short userId) {
        addUserActivity(
                setMaxTasksAmount(amount, userId),
                userId,
                pools.taskPool(),
                taskRepository,
                applicationInfo.userInfoTasks(),
                applicationInfo.mockInfo().getTaskIds()
        );
    }

    @Transactional
    public void addCategories(int amount, short userId) {
        addUserActivity(
                setMaxCategoriesAmount(amount, userId),
                userId,
                pools.categoryPool(),
                categoryRepository,
                applicationInfo.userInfoCategories(),
                applicationInfo.mockInfo().getCategoryIds()
        );
    }

    private <T extends UserActivity<ID>, ID extends Number> void addUserActivity(
            int amount,
            short userId,
            ObjectPool<T> pool,
            JpaRepository<T, ID> repository,
            UserInfo userInfo,
            List<ID> mockInfoIds
    ){
        anAssert.amountIsPositive(amount);
        anAssert.userExist(userId);

        User user = User.builder().id(userId).build();

        List<T> userActivities = pool.checkOut(amount);
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
        pool.checkIn(userActivities);
    }

    @Transactional
    public void addNotification(Date date) {
        Task task = pools.taskPool().checkOut();
        Notification notification = new Notification();
        notification.setNotifyAt(date.toInstant());
        notification.setTask(task);

        User user = userRepository.getReferenceById(applicationInfo.adminInfo().getId());
        task.setUser(user);
        task.setNotification(notification);

        taskRepository.save(task);

        addNotificationInScheduler(task);
        addIdsOfGeneratedEntities(
                applicationInfo.mockInfo().getTaskIds(),
                task.getId()
        );
        // CheckIn in MailSender
    }

    private void addNotificationInScheduler(Task task) {
        notificationService.handNotificationInScheduler(
                applicationInfo.adminInfo().getId(),
                task
        );
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
}
