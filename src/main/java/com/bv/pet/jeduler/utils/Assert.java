package com.bv.pet.jeduler.utils;

import com.bv.pet.jeduler.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class Assert {

    public static void assertAllowedAmount(short value, AllowedAmount type){
        if (value + 1 > type.amount)
            throw new ApplicationException(
                    type.message,
                    HttpStatus.BAD_REQUEST
            );
    }

    public static void assertNotAdmin(short adminId, short givenId) {
        if (adminId == givenId){
            throw new ApplicationException(
                    "Break a leg",
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
