package com.demo.tasks.domain.port.in;

import com.demo.tasks.domain.command.CreateTaskCommand;
import com.demo.tasks.domain.command.UpdateTaskCommand;
import com.demo.tasks.domain.enums.TaskStatus;
import com.demo.tasks.domain.model.Task;
import com.demo.tasks.domain.model.TaskId;

public interface TaskUseCasePort {

    Task create(CreateTaskCommand command);

    Task update(UpdateTaskCommand updateTaskCommand);

    Task updateStatus(TaskId taskId, TaskStatus status);

    void delete(TaskId taskId);

}
