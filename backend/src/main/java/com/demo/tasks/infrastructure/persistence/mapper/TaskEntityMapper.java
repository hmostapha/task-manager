package com.demo.tasks.adapter.persistence.mapper;

import com.demo.tasks.domain.model.Task;
import com.demo.tasks.adapter.persistence.entity.TaskEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskEntityMapper {

    public TaskEntity toEntity(Task task) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(task.getId());
        taskEntity.setTitle(task.getTitle());
        taskEntity.setDescription(task.getDescription());
        taskEntity.setStatus(task.getStatus());
        taskEntity.setCreatedAt(task.getCreatedAt());
        taskEntity.setStartedAt(task.getStartedAt());
        taskEntity.setEndedAt(task.getEndedAt());

        return taskEntity;
    }

    public Task toDomain(TaskEntity taskEntity) {
        Task task = new Task();
        task.setId(taskEntity.getId());
        task.setTitle(taskEntity.getTitle());
        task.setDescription(taskEntity.getDescription());
        task.setStatus(taskEntity.getStatus());
        task.setCreatedAt(taskEntity.getCreatedAt());
        task.setStartedAt(taskEntity.getCreatedAt());
        task.setEndedAt(taskEntity.getEndedAt());

        return task;
    }

    public List<Task> toDomain(List<TaskEntity> taskEntities) {
        return taskEntities.stream().map(this::toDomain).toList();
    }
}
