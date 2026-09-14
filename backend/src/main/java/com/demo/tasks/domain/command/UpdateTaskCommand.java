package com.demo.tasks.domain.command;

import com.demo.tasks.domain.model.TaskId;

public record UpdateTaskCommand(TaskId id, String title, String description) { }
