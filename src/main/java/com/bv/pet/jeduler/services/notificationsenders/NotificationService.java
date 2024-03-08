package com.bv.pet.jeduler.services.notificationsenders;

import com.bv.pet.jeduler.entities.Notification;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.exceptions.ApplicationException;
import com.bv.pet.jeduler.repositories.CategoryRepository;
import com.bv.pet.jeduler.repositories.NotificationRepository;
import com.bv.pet.jeduler.repositories.TaskRepository;
import com.bv.pet.jeduler.repositories.UserRepository;
import com.bv.pet.jeduler.services.mock.pools.TaskPool;
import com.bv.pet.jeduler.services.statistics.StatisticsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;


@Service
@RequiredArgsConstructor
@Getter
@Setter
public class NotificationService implements NotificationSender {
    private final StatisticsService statisticsService;
    private final NotificationRepository notificationRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Qualifier("jedulerBot")
    private final NotificationSender notificationSender;
    @Qualifier("notificationScheduler")
    private final ThreadPoolTaskScheduler scheduler;
    private final Map<Integer, ScheduledFuture<?>> futureMap;
    private final TaskPool taskPool;

    public void send(int taskId, short userId){
        notificationSender.send(taskId, userId);
        removeNotificationAfterSend(taskId);
    }

    @Transactional
    public void removeNotificationAfterSend(int taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new ApplicationException("Task not found on removing notification", HttpStatus.NO_CONTENT)
        );
        task.getNotification().setTask(null);
        task.setNotification(null);
        futureMap.remove(taskId);
        taskRepository.save(task);
        taskPool.checkIn(task);
    }

    @Async
    public void handNotificationInScheduler(short userId, Task task) {
        if (task.getNotification() != null){
            Notification notification = task.getNotification();

            ScheduledFuture<?> future = scheduler.schedule(
                    new SendNotificationTask(
                            notificationSender,
                            userId,
                            task.getId()
                    ),
                    notification.getNotifyAt()
            );

            futureMap.put(
                    task.getId(),
                    future
            );
        }
    }

    @Async
    public void cancelNotification(int taskId){
        ScheduledFuture<?> future = futureMap.get(taskId);
        if (future != null) {
            future.cancel(true);
            futureMap.remove(taskId);
        }
    }
}
