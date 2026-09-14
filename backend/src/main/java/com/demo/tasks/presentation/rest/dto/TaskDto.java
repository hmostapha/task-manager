package com.demo.tasks.adapter.rest.dto;

import com.demo.tasks.domain.enums.TaskStatus;
import com.demo.tasks.domain.model.Task;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime createdAt,
        LocalDateTime startedAt,
        LocalDateTime endedAt
) {

    public static TaskDto from(Task task) {
        return new TaskDto(task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getStartedAt(),
                task.getEndedAt());
    }
}
