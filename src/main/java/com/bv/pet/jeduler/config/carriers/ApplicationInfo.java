package com.bv.pet.jeduler.config.carriers;

import com.bv.pet.jeduler.applicationrunners.cache.AdminInfo;
import com.bv.pet.jeduler.applicationrunners.cache.UserAmount;
import com.bv.pet.jeduler.applicationrunners.cache.UserInfoCategories;
import com.bv.pet.jeduler.applicationrunners.cache.UserInfoTasks;

public record ApplicationInfo(
        AdminInfo adminInfo,
        UserAmount userAmount,
        UserInfoCategories userInfoCategories,
        UserInfoTasks userInfoTasks
) {
    public void addUser(short id){
        userAmount.increment();
        userInfoCategories.getInfo().put(id, (short) 0);
        userInfoTasks.getInfo().put(id, (short) 0);
    }

    public void deleteUser(Short id) {
        userAmount.decrement();
        userInfoCategories.getInfo().remove(id);
        userInfoTasks.getInfo().remove(id);
    }
}
