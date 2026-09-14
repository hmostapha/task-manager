package com.demo.tasks.domain.usecases;

import com.demo.tasks.domain.command.CreateTaskCommand;
import com.demo.tasks.domain.command.UpdateTaskCommand;
import com.demo.tasks.domain.enums.TaskStatus;
import com.demo.tasks.domain.exception.TaskNotFoundException;
import com.demo.tasks.domain.model.Task;
import com.demo.tasks.domain.model.TaskId;
import com.demo.tasks.domain.port.in.TaskUseCasePort;
import com.demo.tasks.domain.port.out.TaskFinderPort;
import com.demo.tasks.domain.port.out.TaskRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskUseCases implements TaskUseCasePort {

    private final TaskRepositoryPort taskRepositoryPort;
    private  final TaskFinderPort taskFinderPort;

    public TaskUseCases(TaskRepositoryPort taskRepositoryPort, TaskFinderPort taskFinderPort) {
        this.taskRepositoryPort = taskRepositoryPort;
        this.taskFinderPort = taskFinderPort;
    }

    @Override
    public Task create(CreateTaskCommand createTaskCommand) {
        Task newTask = new Task(createTaskCommand.title(), createTaskCommand.description());
        return taskRepositoryPort.save(newTask);
    }

    @Override
    public Task update(UpdateTaskCommand updateTaskCommand) {
        Task updatedTask = taskFinderPort.findById(updateTaskCommand.id())
                .map(task -> {
                    task.setTitle(updateTaskCommand.title());
                    task.setDescription(updateTaskCommand.description());
                    return task;
                })
                .orElseThrow(() -> new TaskNotFoundException(updateTaskCommand.id()));

        return taskRepositoryPort.save(updatedTask);
    }

    @Override
    public Task updateStatus(TaskId taskId, TaskStatus status) {
        Task updatedTask = taskFinderPort.findById(taskId)
                .map(task -> {
                    task.setStatus(status);
                    return task;
                })
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        return taskRepositoryPort.save(updatedTask);
    }

    @Override
    public void delete(TaskId taskId) {
        taskFinderPort.findById(taskId)
                .ifPresentOrElse(
                        task -> taskRepositoryPort.delete(taskId),
                        () -> { throw new TaskNotFoundException(taskId); }
                );
    }
}
