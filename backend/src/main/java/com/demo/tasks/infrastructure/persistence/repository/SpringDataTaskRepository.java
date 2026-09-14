package com.demo.tasks.adapter.persistence.repository;

import com.demo.tasks.adapter.persistence.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface SpringDataTaskRepository
        extends JpaRepository<TaskEntity, UUID> , PagingAndSortingRepository<TaskEntity, UUID> {

    Page<TaskEntity> findAll(Specification<TaskEntity> spec, Pageable pageable);
}
