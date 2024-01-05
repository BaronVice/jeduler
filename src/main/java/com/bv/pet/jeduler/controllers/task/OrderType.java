package com.bv.pet.jeduler.controllers.task;

public enum OrderType {
    starts("starts_at"),
    priority("priority"),
    changed("last_changed"),
    name("name");

    final String columnName;

    OrderType(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String toString() {
        return columnName;
    }
}
