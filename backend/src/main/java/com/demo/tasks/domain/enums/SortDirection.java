package com.demo.tasks.domain.enums;

public enum SortDirection {
    ASC, DESC;

    public static SortDirection fromString(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return SortDirection.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Direction invalide : " + value);
        }
    }
}
