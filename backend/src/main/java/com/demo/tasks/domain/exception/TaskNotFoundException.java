package com.demo.tasks.domain.exception;

import com.demo.tasks.domain.model.TaskId;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(TaskId taskId) {
        super("Task not found: " + taskId);
    }
}