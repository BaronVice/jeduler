package com.bv.pet.jeduler.config.carriers;

import com.bv.pet.jeduler.services.mock.generators.*;

public record Generators(
        UserGenerator userGenerator,
        CategoryGenerator categoryGenerator,
        NotificationGenerator notificationGenerator,
        TaskGenerator taskGenerator,
        SubtaskGenerator subtaskGenerator
) {
}
