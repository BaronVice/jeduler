package com.bv.pet.jeduler.application.cache;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Getter
@Component
public class MockInfo {
    private final List<Short> userIds;
    private final List<Short> categoryIds;
    private final List<Integer> taskIds;
    private final List<Integer> subtaskIds;
    private final List<Integer> notificationIds;

    public MockInfo() {
        userIds = new LinkedList<>();
        categoryIds = new LinkedList<>();
        taskIds = new LinkedList<>();
        subtaskIds = new LinkedList<>();
        notificationIds = new LinkedList<>();
    }
}
