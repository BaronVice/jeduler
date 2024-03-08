package com.bv.pet.jeduler.services.task;

import com.bv.pet.jeduler.datacarriers.dtos.TaskDto;
import com.bv.pet.jeduler.entities.Notification;
import com.bv.pet.jeduler.entities.Subtask;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.entities.user.User;
import com.bv.pet.jeduler.exceptions.ApplicationException;
import com.bv.pet.jeduler.mappers.TaskMapper;
import com.bv.pet.jeduler.repositories.CategoryRepository;
import com.bv.pet.jeduler.repositories.NotificationRepository;
import com.bv.pet.jeduler.repositories.TaskRepository;
import com.bv.pet.jeduler.services.notificationsenders.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
// I wasn't drunk when I wrote this. Yet, this crap is crying for TODO: refactored
public class TaskServiceHandler {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;
    private final TaskMapper taskMapper;

    public TaskDto get(short userId, int id){
        Task task = taskRepository.findByUserIdAndId(userId, id).orElseThrow(
                () -> new ApplicationException("Task not found", HttpStatus.NOT_FOUND)
        );
        task.setCategoryIds(categoryRepository.findIdsByTaskId(task.getId()));

        return taskMapper.toTaskDto(task);
    }

    @Transactional
    public Task create(short userId, TaskDto taskDto) {
        Task task = taskMapper.toTask(taskDto);
        System.out.println();

        setUserOnTask(task, userId);
        setLastChangedAsNow(task);
        setNotificationOnTaskCreate(task, taskDto);
        setSubtasksOnTaskCreate(task);

        saveTask(task);
        notificationService.handNotificationInScheduler(userId, task);

        return task;
    }

    @Transactional
    public void update(short userId, TaskDto taskDto) {
        Task updated = taskMapper.toTask(taskDto);
        Optional<Task> toUpdateOptional = taskRepository.findByUserIdAndId(userId, taskDto.id());
        if (toUpdateOptional.isEmpty())
            throw new ApplicationException(
                    "Task not found",
                    HttpStatus.BAD_REQUEST
            );

        Task toUpdate = toUpdateOptional.get();
        toUpdate.setName(updated.getName());
        toUpdate.setDescription(updated.getDescription());
        toUpdate.setStartsAt(updated.getStartsAt());
        toUpdate.setTaskDone(updated.isTaskDone());
        toUpdate.setPriority(updated.getPriority());
        toUpdate.setCategories(updated.getCategories());
//        setCategoriesOnTask(toUpdate, taskDto);

        setLastChangedAsNow(toUpdate);
        setNotificationOnTaskUpdate(updated, toUpdate);
        setSubtasksOnTaskUpdate(updated, toUpdate);
        System.out.println(toUpdate.getSubtasks().size());

        saveTask(toUpdate);
        notificationService.handNotificationInScheduler(userId, toUpdate);
    }

    @Transactional
    public void delete(short userId, int id) {
        Optional<Task> task = taskRepository.findByUserIdAndId(userId, id);
        if (task.isPresent()){
            notificationService.cancelNotification(id);
            taskRepository.deleteById(id);
        }
    }

    private void saveTask(Task task) {
        taskRepository.save(task);
    }

    private void setUserOnTask(Task task, short userId){
        task.setUser(User.builder().id(userId).build());
    }

    private void setLastChangedAsNow(Task task){
        task.setLastChanged(Instant.now());
    }

    private void setNotificationOnTaskCreate(Task task, TaskDto taskDto){
        Instant notifyAt = taskDto.notifyAt();
        if (notifyAt != null) {
            task.setNotification(
                    Notification
                            .builder()
                            .task(task)
                            .notifyAt(notifyAt)
                            .build()
            );
        }
    }

    private void setSubtasksOnTaskCreate(Task task){
        for (short i = 0; i < task.getSubtasks().size(); i++){
            Subtask subtask = task.getSubtasks().get(i);
            subtask.setTask(task);
            subtask.setOrderInList(i);
        }
    }

    private void setNotificationOnTaskUpdate(Task updated, Task toUpdate) {
        if (updated.getNotification() == null){
            notificationService.cancelNotification(toUpdate.getId());
            toUpdate.setNotification(null);
        } else {
            changeTaskNotification(updated, toUpdate);
        }
    }

    private void changeTaskNotification(Task updated, Task toUpdate){
        if (toUpdate.getNotification() == null) {
            toUpdate.setNotification(new Notification());
            toUpdate.getNotification().setTask(toUpdate);
        } else {
            notificationService.cancelNotification(toUpdate.getId());
        }
        toUpdate.getNotification().setNotifyAt(
                updated.getNotification().getNotifyAt()
        );
        notificationRepository.save(toUpdate.getNotification());
    }

    private void setSubtasksOnTaskUpdate(Task updated, Task toUpdate){
//        for (short i = 0; i < updated.getSubtasks().size(); i++){
//            Subtask subtask = updated.getSubtasks().get(i);
//            subtask.setTask(toUpdate);
//            subtask.setOrderInList(i);
//        }
//        subtaskRepository.deleteAll(toUpdate.getSubtasks());
//        subtaskRepository.saveAll(updated.getSubtasks());
//
//        toUpdate.setSubtasks(updated.getSubtasks());
        short i = 0;
        for (Subtask toReplace: toUpdate.getSubtasks()){
            if (updated.getSubtasks().size() > i){
                Subtask subtask = updated.getSubtasks().get(i);
                toReplace.setName(subtask.getName());
                toReplace.setOrderInList(i);
                toReplace.setCompleted(subtask.isCompleted());
                i++;
            }
        }

        int difference = toUpdate.getSubtasks().size() - updated.getSubtasks().size();
        if (difference > 0){
            removeLastSubtasks(toUpdate.getSubtasks(), i);
            return;
        }
        while (difference++ != 0){
            Subtask subtask = updated.getSubtasks().get(i++);
            subtask.setTask(toUpdate);
            subtask.setOrderInList((short) toUpdate.getSubtasks().size());

            toUpdate.getSubtasks().add(subtask);
        }
    }

    private void removeLastSubtasks(List<Subtask> subtasks, int startIndex){
        for (int i = startIndex; i < subtasks.size(); i++) {
            subtasks.get(i).setTask(null);
        }
        subtasks.subList(startIndex, subtasks.size()).clear();
    }
}
