package com.bv.pet.jeduler.config.carriers;

import com.bv.pet.jeduler.application.cache.*;

import java.util.List;
import java.util.Map;

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

    public void addUsers(List<Short> ids){
        userAmount.change(ids.size());
        ids.forEach(id -> {
                    userInfoCategories.getInfo().put(id, (short) 0);
                    userInfoTasks.getInfo().put(id, (short) 0);
        });
    }
}
