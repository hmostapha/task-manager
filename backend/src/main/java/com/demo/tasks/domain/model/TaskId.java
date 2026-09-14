package com.demo.tasks.domain.model;

import java.util.Objects;
import java.util.UUID;

public record TaskId(UUID value) {

    public TaskId {
        Objects.requireNonNull(value, "TaskId cannot be null");
    }

    public static TaskId of(UUID value) {
        return new TaskId(value);
    }
}