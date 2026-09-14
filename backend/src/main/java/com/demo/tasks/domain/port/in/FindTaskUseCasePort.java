package com.demo.tasks.domain.port.in;


import com.demo.tasks.domain.model.SortQuery;
import com.demo.tasks.domain.model.Task;
import com.demo.tasks.domain.model.TaskFilter;
import com.demo.tasks.domain.model.TaskId;
import com.demo.tasks.domain.model.PageResult;

import java.util.Optional;

public interface FindTaskUseCasePort {


    Optional<Task> findById(TaskId id);

    PageResult<Task> findAll(TaskFilter filter, SortQuery pagination);
}
