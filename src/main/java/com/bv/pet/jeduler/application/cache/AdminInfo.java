package com.bv.pet.jeduler.application.cache;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class AdminInfo {
    private short id;
    // TODO: Shouldn't they be final?
    //  move to config as bean?
    private String uuid;
}
