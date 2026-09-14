package com.demo.tasks.domain.port.out;


import com.demo.tasks.domain.model.PageResult;
import com.demo.tasks.domain.model.SortQuery;
import com.demo.tasks.domain.model.Task;
import com.demo.tasks.domain.model.TaskFilter;
import com.demo.tasks.domain.model.TaskId;

import java.util.Optional;

public interface TaskFinderPort {


    Optional<Task> findById(TaskId query);

    PageResult<Task> findAll(TaskFilter filter, SortQuery pagination);
}
