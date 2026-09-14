package com.demo.tasks.domain.enums;

public enum TaskStatus {
    TODO, IN_PROGRESS, DONE;

    public static TaskStatus fromString(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return TaskStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Statut invalide : " + value);
        }
    }
}
