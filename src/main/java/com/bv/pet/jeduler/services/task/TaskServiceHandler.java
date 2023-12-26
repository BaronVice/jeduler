package com.bv.pet.jeduler.services.task;

import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.datacarriers.dtos.TaskDto;
import com.bv.pet.jeduler.entities.Notification;
import com.bv.pet.jeduler.entities.Subtask;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.entities.user.User;
import com.bv.pet.jeduler.exceptions.ApplicationException;
import com.bv.pet.jeduler.mappers.TaskMapper;
import com.bv.pet.jeduler.repositories.SubtaskRepository;
import com.bv.pet.jeduler.repositories.TaskRepository;
import com.bv.pet.jeduler.services.mail.MailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskServiceHandler {
    private final TaskRepository taskRepository;
    private final SubtaskRepository subtaskRepository;
    private final MailServiceImpl mailService;
    private final ApplicationInfo applicationInfo;
    private final TaskMapper taskMapper;

    @Transactional(readOnly = true)
    public Task get(Integer id){
        return taskRepository.findById(id).orElseThrow(
                () -> new ApplicationException("Task not found", HttpStatus.NOT_FOUND)
        );
    }

    @Transactional(readOnly = true)
    public List<Task> getAll() {
        return taskRepository.findByOrderByStartsAtAsc();
    }

    @Transactional
    public Task create(short userId, TaskDto taskDto) {
        Task task = taskMapper.toTask(taskDto);

        setUserOnTask(task, userId);
        setNotificationOnTaskCreate(task, taskDto);
        setSubtasksOnTaskCreate(task);

        saveTask(task);
        return task;
    }

    @Transactional
    public void update(Task updated) {
        Task toUpdate = taskRepository.getReferenceById(updated.getId());

        toUpdate.setName(updated.getName());
        toUpdate.setDescription(updated.getDescription());
        toUpdate.setStartsAt(updated.getStartsAt());
        toUpdate.setCategories(updated.getCategories());
        toUpdate.setTaskDone(updated.isTaskDone());

        setNotificationOnTaskUpdate(updated, toUpdate);
        setSubtasksOnTaskUpdate(updated, toUpdate);

        saveTask(toUpdate);
        mailService.handNotificationInScheduler(toUpdate);
    }

    @Transactional
    public void delete(Integer id) {
        mailService.getInstants().remove(id);
        taskRepository.deleteById(id);
    }

    private void saveTask(Task task) {
        taskRepository.save(task);
    }

    private void setUserOnTask(Task task, short userId){
        task.setUser(User.builder().id(userId).build());
    }

    private void setNotificationOnTaskCreate(Task task, TaskDto taskDto){
        Instant notifyAt = taskDto.getNotifyAt();
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
            mailService.getInstants().remove(updated.getId());
            toUpdate.setNotification(null);
        } else {
            changeTaskNotification(updated, toUpdate);
        }
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

    private void changeTaskNotification(Task updated, Task toUpdate){
        if (toUpdate.getNotification() == null) {
            toUpdate.setNotification(new Notification());
            toUpdate.getNotification().setTask(toUpdate);
        }
        toUpdate.getNotification().setNotifyAt(
                updated.getNotification().getNotifyAt()
        );
    }
}
