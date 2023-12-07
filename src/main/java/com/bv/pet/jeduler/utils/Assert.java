package com.bv.pet.jeduler.utils;

import com.bv.pet.jeduler.exceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class Assert {
    private static final short allowedCategoryAmount = 20;

    public static void assertAllowedCategoryAmount(short value){
        if (value + 1 > allowedCategoryAmount)
            throw new ApplicationException(
                    "Category amount limit",
                    // TODO: what status shall be there?
                    HttpStatus.I_AM_A_TEAPOT
            );
    }
}
