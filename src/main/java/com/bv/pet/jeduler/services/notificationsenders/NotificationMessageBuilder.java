package com.bv.pet.jeduler.services.notificationsenders;

import com.bv.pet.jeduler.entities.Task;
import com.bv.pet.jeduler.exceptions.ApplicationException;
import com.bv.pet.jeduler.repositories.CategoryRepository;
import com.bv.pet.jeduler.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

// This should be located in completely different place. However, let it be for now
@Component
@RequiredArgsConstructor
public class NotificationMessageBuilder {
    private static final String prefix = "http://localhost:8080/jeduler/tasks/";
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public String buildStringMessage(Integer taskId){
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new ApplicationException("Task not found on building notification message", HttpStatus.NOT_FOUND)
        );

        List<String> categoryNames = categoryRepository.findNamesByTaskId(taskId);

        return "Notifying that you have a task." +
                '\n' +
                "Name: " + task.getName() + (task.isTaskDone() ? " (Finished)" : " (Unfinished)") +
                '\n' +
                "Starts at: " + task.getStartsAt() +
                '\n' +
                "Description: " + task.getDescription() +
                '\n' +
                '\n' +
                "Categories: " +
                (categoryNames.size() == 0 ? " ¯\\_(ツ)_/¯" : String.join(", ", categoryNames)) +
                '\n' +
                prefix + taskId;
    }
}
