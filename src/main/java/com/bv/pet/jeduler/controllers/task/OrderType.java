package com.bv.pet.jeduler.controllers.task;

public enum OrderType {
    STARTS("starts_at"),
    PRIORITY("priority"),
    CHANGED("last_changed"),
    NAME("name");

    final String columnName;

    OrderType(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String toString() {
        return columnName;
    }
}
