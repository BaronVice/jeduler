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

    public void setNotificationOnTaskCreate(Task task){
        if (task.getNotification() != null)
            task.getNotification().setTask(task);
    }

    public void setSubtasksOnTaskCreate(Task task){
        for (short i = 0; i < task.getSubtasks().size(); i++){
            Subtask subtask = task.getSubtasks().get(i);
            subtask.setTask(task);
            subtask.setOrderInList(i);
        }
    }

    public void setNotificationOnTaskUpdate(Task updated, Task toUpdate) {
        if (updated.getNotification() == null){
            if (toUpdate.getNotification() != null){
                mailService.getNotifications().remove(toUpdate.getNotification());
            }
        } else {
            if (toUpdate.getNotification() != null) {
                mailService.getNotifications().remove(toUpdate.getNotification());
            } else {
                toUpdate.setNotification(new Notification());
            }
            toUpdate.getNotification().setNotifyAt(
                    updated.getNotification().getNotifyAt()
            );
            mailService.getNotifications().add(toUpdate.getNotification());
        }
    }

    public void setSubtasksOnTaskUpdate(Task updated, Task toUpdate){
        for (short i = 0; i < updated.getSubtasks().size(); i++){
            Subtask subtask = updated.getSubtasks().get(i);
            subtask.setTask(toUpdate);
            subtask.setOrderInList(i);
        }
        subtaskRepository.deleteAll(toUpdate.getSubtasks());
        subtaskRepository.saveAll(updated.getSubtasks());
    }

    public void handNotificationInMailService(Task task) {
        if (task.getNotification() != null)
            mailService.getNotifications().add(task.getNotification());
    }

    public Task get(Long id){
        return taskRepository.findById(id).orElseThrow(
                () -> new ApplicationException("Task not found", HttpStatus.NOT_FOUND)
        );
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    public void saveSubtasks(Iterable<Subtask> subtasks){
        subtaskRepository.saveAll(subtasks);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
