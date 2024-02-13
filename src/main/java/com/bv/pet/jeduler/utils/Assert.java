package com.bv.pet.jeduler.utils;

import com.bv.pet.jeduler.config.carriers.ApplicationInfo;
import com.bv.pet.jeduler.exceptions.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class Assert {
    private final ApplicationInfo applicationInfo;

    public void allowedCreation(int value, AllowedAmount type){
        if (value + 1 > type.amount)
            throw new ApplicationException(
                    type.message,
                    HttpStatus.BAD_REQUEST
            );
    }

    public void notMainAdmin(short givenId) {
        if (applicationInfo.adminInfo().getId() == givenId){
            throw new ApplicationException(
                    "Break a leg",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    public void userExist(short userId){
        if (!applicationInfo.userInfoTasks().isExist(userId))
            throw new ApplicationException(
                    "User not found",
                    HttpStatus.BAD_REQUEST
            );
    }

    public void amountIsPositive(int amount){
        if (amount <= 0)
            throw new ApplicationException(
                    "Limit is reached",
                    HttpStatus.BAD_REQUEST
            );
    }
}
