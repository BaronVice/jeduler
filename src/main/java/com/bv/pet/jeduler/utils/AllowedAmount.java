package com.bv.pet.jeduler.utils;

public enum AllowedAmount {
    CATEGORY((short) 20, "Category amount limit"),
    TASK((short) 10000, "Task amount limit"),
    SUBTASK((short) 20, "Subtask amount limit"),
    USER((short) 1000, "User amount limit");

    final short amount;
    final String message;

    AllowedAmount(short amount, String message) {
        this.amount = amount;
        this.message = message;
    }
}
