package com.bv.pet.jeduler.config.carriers;

import com.bv.pet.jeduler.applicationrunners.data.AdminInfo;
import com.bv.pet.jeduler.applicationrunners.data.UserAmount;
import com.bv.pet.jeduler.applicationrunners.data.UserInfoCategories;
import com.bv.pet.jeduler.applicationrunners.data.UserInfoTasks;

public record ApplicationInfo(
        AdminInfo adminInfo,
        UserAmount userAmount,
        UserInfoCategories userInfoCategories,
        UserInfoTasks userInfoTasks
) {
}
