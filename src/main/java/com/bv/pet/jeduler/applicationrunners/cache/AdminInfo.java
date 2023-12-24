package com.bv.pet.jeduler.applicationrunners.cache;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class AdminInfo {

    private short id;
    @Value("${custom.admin.username}")
    private String username;
    @Value("${custom.admin.password}")
    private String password;
}
