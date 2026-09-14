package com.demo.tasks.adapter.persistence;

import com.demo.tasks.domain.model.Task;
import com.demo.tasks.domain.model.TaskId;
import com.demo.tasks.domain.port.out.TaskRepositoryPort;
import com.demo.tasks.adapter.persistence.entity.TaskEntity;
import com.demo.tasks.adapter.persistence.mapper.TaskEntityMapper;
import com.demo.tasks.adapter.persistence.repository.SpringDataTaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskPersistenceAdapter implements TaskRepositoryPort {
    private final SpringDataTaskRepository repository;
    private  final TaskEntityMapper mapper;

    public TaskPersistenceAdapter(SpringDataTaskRepository repository, TaskEntityMapper mapper){
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Task save(Task task) {

        TaskEntity taskEntity = mapper.toEntity(task);

        return mapper.toDomain(
                repository.save(taskEntity));
    }

    @Override
    public void delete(TaskId taskId) {
        repository.deleteById(taskId.value());
    }


}
