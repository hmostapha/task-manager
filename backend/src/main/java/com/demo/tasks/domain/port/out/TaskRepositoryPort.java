package com.demo.tasks.domain.port.out;

import com.demo.tasks.domain.model.Task;
import com.demo.tasks.domain.model.TaskId;

public interface TaskRepositoryPort {

    Task save(Task task);

    void delete(TaskId taskId);
}
