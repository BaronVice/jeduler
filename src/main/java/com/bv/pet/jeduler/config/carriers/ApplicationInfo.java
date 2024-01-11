package com.bv.pet.jeduler.config.carriers;

import com.bv.pet.jeduler.application.cache.*;

public record ApplicationInfo(
        AdminInfo adminInfo,
        UserAmount userAmount,
        UserInfoCategories userInfoCategories,
        UserInfoTasks userInfoTasks,
        MockInfo mockInfo
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
