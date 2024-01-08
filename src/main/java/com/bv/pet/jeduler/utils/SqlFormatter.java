package com.bv.pet.jeduler.utils;

import java.util.List;
import java.util.stream.Collectors;

public class SqlFormatter {
    public static String wrapInAnyValues(List<? extends Number> list){
        return "any(values ("
                + list.stream().map(String::valueOf).collect(Collectors.joining("), ("))
                + "))";
    }
}
