package com.bv.pet.jeduler.services.mail;

import com.bv.pet.jeduler.entities.Task;
import java.time.Instant;
import java.util.List;

public class MessageFormatter {
    public static String formatSubject(Task task){
        return "Jeduler task: " + task.getName();
    }

    public static String formatText(Task task, List<String> categoryNames){
        StringBuilder stringBuilder = new StringBuilder();

        Instant startDate = task.getStartsAt();

        stringBuilder
                .append("Notifying that you have a task named ").append(task.getName()).append("\n");
        if (task.getDescription() != null){
            stringBuilder.append("Description: ").append(task.getDescription()).append("\n\n");
        }
        stringBuilder
                .append("Starts at: ").append(startDate).append("\n");
        if(task.getCategories() != null){
            stringBuilder
                    .append("Categories: ").append(
                            String.join(", ", categoryNames)
                    );
        }

        return stringBuilder.toString();
    }
}
