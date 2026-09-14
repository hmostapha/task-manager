package com.demo.tasks.adapter.persistence;

import com.demo.tasks.adapter.persistence.entity.TaskEntity;
import com.demo.tasks.adapter.persistence.mapper.TaskEntityMapper;
import com.demo.tasks.adapter.persistence.repository.SpringDataTaskRepository;
import com.demo.tasks.domain.model.PageResult;
import com.demo.tasks.domain.model.SortQuery;
import com.demo.tasks.domain.model.Task;
import com.demo.tasks.domain.model.TaskFilter;
import com.demo.tasks.domain.model.TaskId;
import com.demo.tasks.domain.port.out.TaskFinderPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.demo.tasks.adapter.persistence.specification.TaskSpecification.buildSort;
import static com.demo.tasks.adapter.persistence.specification.TaskSpecification.buildSpecification;

@Service
public class TaskFinderAdapter implements TaskFinderPort {
    private final SpringDataTaskRepository repository;
    private  final TaskEntityMapper mapper;

    public TaskFinderAdapter(SpringDataTaskRepository repository, TaskEntityMapper mapper){
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Task> findById(TaskId taskId) {
        return repository.findById(taskId.value())
                .map(mapper::toDomain);
    }

    @Override
    public PageResult<Task> findAll(TaskFilter filter, SortQuery pageQuery) {
        Page<TaskEntity> page = repository.findAll(
                buildSpecification(filter),
                PageRequest.of(pageQuery.page(),
                        pageQuery.size(),
                        buildSort(pageQuery))
        );

       return new PageResult<>(
               page.get().map(mapper::toDomain).toList(),
               pageQuery.page(),
               page.getTotalPages(),
               page.getTotalElements()
       );
    }
}
