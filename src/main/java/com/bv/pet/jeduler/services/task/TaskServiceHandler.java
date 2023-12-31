package com.bv.pet.jeduler.services.task;

import com.bv.pet.jeduler.datacarriers.dtos.TaskDto;
import com.bv.pet.jeduler.entities.Category;
import com.bv.pet.jeduler.entities.Notification;
import com.bv.pet.jeduler.entities.Subtask;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.entities.user.User;
import com.bv.pet.jeduler.exceptions.ApplicationException;
import com.bv.pet.jeduler.mappers.TaskMapper;
import com.bv.pet.jeduler.repositories.NotificationRepository;
import com.bv.pet.jeduler.repositories.SubtaskRepository;
import com.bv.pet.jeduler.repositories.TaskRepository;
import com.bv.pet.jeduler.repositories.projections.task.TaskCategory;
import com.bv.pet.jeduler.services.mail.MailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaskServiceHandler {
    private final TaskRepository taskRepository;
    private final SubtaskRepository subtaskRepository;
    private final NotificationRepository notificationRepository;
    private final MailServiceImpl mailService;
    private final TaskMapper taskMapper;

    public Task get(Integer id){
        return taskRepository.findById(id).orElseThrow(
                () -> new ApplicationException("Task not found", HttpStatus.NOT_FOUND)
        );
    }

    @Transactional
    public Task create(short userId, String mail, TaskDto taskDto) {
        Task task = taskMapper.toTask(taskDto);

        setUserOnTask(task, userId);
        setCategoriesOnTask(task, taskDto);
        setLastChangedAsNow(task);
        setNotificationOnTaskCreate(task, taskDto);
        setSubtasksOnTaskCreate(task);

        saveTask(task);
        mailService.handNotificationInScheduler(mail, task);

        return task;
    }

    @Transactional
    public void update(String mail, TaskDto taskDto) {
        Task updated = taskMapper.toTask(taskDto);
        Task toUpdate = taskRepository.getReferenceById(taskDto.id());

        toUpdate.setName(updated.getName());
        toUpdate.setDescription(updated.getDescription());
        toUpdate.setStartsAt(updated.getStartsAt());
        toUpdate.setTaskDone(updated.isTaskDone());
        toUpdate.setPriority(updated.getPriority());

        setLastChangedAsNow(toUpdate);
        setCategoriesOnTask(toUpdate, taskDto);
        setNotificationOnTaskUpdate(updated, toUpdate);
        setSubtasksOnTaskUpdate(updated, toUpdate);

        saveTask(toUpdate);
        mailService.handNotificationInScheduler(mail, toUpdate);
    }

    @Transactional
    public void delete(int id) {
        taskRepository.deleteById(id);
        mailService.removeNotificationFromScheduler(id);
    }

    private void saveTask(Task task) {
        taskRepository.save(task);
    }

    private void setUserOnTask(Task task, short userId){
        task.setUser(User.builder().id(userId).build());
    }

    private void setCategoriesOnTask(Task task, TaskDto taskDto) {
        task.setCategories(
                taskDto.categoryIds().stream().map(
                        id -> Category.builder().id(id).build()
                ).collect(Collectors.toList())
        );
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
            mailService.removeNotificationFromScheduler(toUpdate.getId());
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
            mailService.removeNotificationFromScheduler(toUpdate.getId());
        }
        toUpdate.getNotification().setNotifyAt(
                updated.getNotification().getNotifyAt()
        );
        notificationRepository.save(toUpdate.getNotification());
    }

    private void setSubtasksOnTaskUpdate(Task updated, Task toUpdate){
        for (short i = 0; i < updated.getSubtasks().size(); i++){
            Subtask subtask = updated.getSubtasks().get(i);
            subtask.setTask(toUpdate);
            subtask.setOrderInList(i);
        }
        subtaskRepository.deleteAll(toUpdate.getSubtasks());
        subtaskRepository.saveAll(updated.getSubtasks());

        toUpdate.setSubtasks(updated.getSubtasks());
    }

    public void setCategoryIdsForTaskDto(List<TaskDto> taskDtoList) {
        Map<Integer, TaskDto> map = taskDtoList.stream().collect(Collectors.toMap(TaskDto::id, Function.identity()));
        List<TaskCategory> taskCategories = taskRepository.getCategoryIdsByTaskIds(
                taskDtoList.stream().map(TaskDto::id).toList()
        );

        for (TaskCategory taskCategory : taskCategories)
            map.get(taskCategory.getTaskId()).categoryIds().add(taskCategory.getCategoryId());
    }
}
