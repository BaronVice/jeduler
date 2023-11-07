package com.bv.pet.jeduler.services.handlers;

import com.bv.pet.jeduler.entities.Notification;
import com.bv.pet.jeduler.entities.Subtask;
import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.exceptions.ApplicationException;
import com.bv.pet.jeduler.repositories.SubtaskRepository;
import com.bv.pet.jeduler.repositories.TaskRepository;
import com.bv.pet.jeduler.services.MailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskServiceHandler {
    private final TaskRepository taskRepository;
    private final SubtaskRepository subtaskRepository;
    private final MailServiceImpl mailService;

    public Task get(Long id){
        return taskRepository.findById(id).orElseThrow(
                () -> new ApplicationException("Task not found", HttpStatus.NOT_FOUND)
        );
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public void create(Task task) {
        setNotificationOnTaskCreate(task);
        setSubtasksOnTaskCreate(task);

        saveTask(task);
        saveSubtasks(task.getSubtasks());

        handNotificationInMailService(task);
    }

    public void update(Task updated, Task toUpdate) {
        toUpdate.setName(updated.getName());
        toUpdate.setDescription(updated.getDescription());
        toUpdate.setStartsAt(updated.getStartsAt());
        toUpdate.setExpiresAt(updated.getExpiresAt());
        toUpdate.setCategories(updated.getCategories());

        setNotificationOnTaskUpdate(updated, toUpdate);
        setSubtasksOnTaskUpdate(updated, toUpdate);

        saveTask(toUpdate);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    private void setNotificationOnTaskCreate(Task task){
        if (task.getNotification() != null)
            task.getNotification().setTask(task);
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
            removeTaskNotificationIfNotNull(toUpdate);
            toUpdate.setNotification(null);
        } else {
            changeTaskNotification(updated, toUpdate);
        }
    }
    private boolean removeTaskNotificationIfNotNull(Task task){
        if (task.getNotification() != null){
            mailService.getNotifications().remove(task.getNotification());
            return true;
        }

        return false;
    }
    private void changeTaskNotification(Task updated, Task toUpdate){
        if (! removeTaskNotificationIfNotNull(toUpdate)) {
            toUpdate.setNotification(new Notification());
            toUpdate.getNotification().setTask(toUpdate);
        }
        toUpdate.getNotification().setNotifyAt(
                updated.getNotification().getNotifyAt()
        );
        mailService.getNotifications().add(toUpdate.getNotification());
    }

    public void setSubtasksOnTaskUpdate(Task updated, Task toUpdate){
        for (short i = 0; i < updated.getSubtasks().size(); i++){
            Subtask subtask = updated.getSubtasks().get(i);
            subtask.setTask(toUpdate);
            subtask.setOrderInList(i);
        }
        subtaskRepository.deleteAll(toUpdate.getSubtasks());
        subtaskRepository.saveAll(updated.getSubtasks());

        toUpdate.setSubtasks(updated.getSubtasks());
    }

    public void handNotificationInMailService(Task task) {
        if (task.getNotification() != null)
            mailService.getNotifications().add(task.getNotification());
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    public void saveSubtasks(Iterable<Subtask> subtasks){
        subtaskRepository.saveAll(subtasks);
    }
}
