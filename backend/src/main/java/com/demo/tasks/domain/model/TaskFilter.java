package com.demo.tasks.domain.model;

import com.demo.tasks.domain.enums.TaskStatus;

public record TaskFilter(TaskStatus status, String search) {
    public static boolean isFilterEmpty(TaskFilter filter) {
        return filter.status() == null && (filter.search() == null || filter.search().isBlank());
    }
}
