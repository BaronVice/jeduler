package com.bv.pet.jeduler.utils;

public enum AllowedAmount {
    CATEGORY(20, "Category amount limit"),
    TASK(10000, "Task amount limit"),
    SUBTASK(20, "Subtask amount limit"),
    USER(1000, "User amount limit");

    final int amount;
    final String message;

    AllowedAmount(int amount, String message) {
        this.amount = amount;
        this.message = message;
    }
}
