package com.bv.pet.jeduler.config.carriers;

import com.bv.pet.jeduler.services.mock.pools.CategoryPool;
import com.bv.pet.jeduler.services.mock.pools.SubtaskPool;
import com.bv.pet.jeduler.services.mock.pools.TaskPool;
import com.bv.pet.jeduler.services.mock.pools.UserPool;

public record Pools(
        UserPool userPool,
        TaskPool taskPool,
        CategoryPool categoryPool,
        SubtaskPool subtaskPool
) {
}
