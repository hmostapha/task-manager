package com.demo.tasks.domain.usecases;

import com.demo.tasks.domain.model.PageResult;
import com.demo.tasks.domain.model.SortQuery;
import com.demo.tasks.domain.model.Task;
import com.demo.tasks.domain.model.TaskFilter;
import com.demo.tasks.domain.model.TaskId;
import com.demo.tasks.domain.port.in.FindTaskUseCasePort;
import com.demo.tasks.domain.port.out.TaskFinderPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindTaskUseCase implements FindTaskUseCasePort {

    TaskFinderPort taskFinderPort;

    public FindTaskUseCase(TaskFinderPort taskFinderPort){
        this.taskFinderPort = taskFinderPort;
    }

    @Override
    public Optional<Task> findById(TaskId id) {
        return taskFinderPort.findById(id);
    }

    @Override
    public PageResult<Task> findAll(TaskFilter filter, SortQuery pagination) {
        return taskFinderPort.findAll(filter, pagination);
    }
}
