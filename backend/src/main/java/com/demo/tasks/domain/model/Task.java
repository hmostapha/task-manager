package com.demo.tasks.domain.model;

import com.demo.tasks.domain.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task {
    private UUID id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;


    public Task(String title, String description) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.status = TaskStatus.TODO;
        this.createdAt = LocalDateTime.now();
    }
}