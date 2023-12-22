package com.bv.pet.jeduler.utils;

import com.bv.pet.jeduler.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class Assert {

    public static void assertAllowedCategoryAmount(short value, AllowedAmount type){
        if (value + 1 > type.amount)
            throw new ApplicationException(
                    type.message,
                    HttpStatus.BAD_REQUEST
            );
    }
}
