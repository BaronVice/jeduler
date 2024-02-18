package com.bv.pet.jeduler.entities;

import com.bv.pet.jeduler.entities.user.User;

public interface UserActivity<ID extends Number> extends ApplicationEntity<ID>  {
    void setUser(User user);
}
