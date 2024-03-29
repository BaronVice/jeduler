package com.bv.pet.jeduler.application.disposers;

import com.bv.pet.jeduler.application.cache.MockInfo;
import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MockDisposer implements DisposableBean {
    private final ApplicationInfo applicationInfo;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final SubtaskRepository subtaskRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public void destroy() {
        MockInfo mockInfo = applicationInfo.mockInfo();
        dispose(mockInfo.getNotificationIds(), notificationRepository);
        dispose(mockInfo.getSubtaskIds(), subtaskRepository);
        dispose(mockInfo.getTaskIds(), taskRepository);
        dispose(mockInfo.getCategoryIds(), categoryRepository);
        dispose(mockInfo.getUserIds(), userRepository);
    }

    @Transactional
    @Async // PostgreSQL should support ACID, so I guess it's alright
    public <ID extends Number> void dispose(List<ID> ids, JpaRepository<?, ID> repository){
        repository.flush();
        repository.deleteAllByIdInBatch(ids);
    }
}
