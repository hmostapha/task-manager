package com.demo.tasks.adapter.persistence.specification;

import com.demo.tasks.adapter.persistence.entity.TaskEntity;
import com.demo.tasks.domain.enums.SortDirection;
import com.demo.tasks.domain.enums.TaskStatus;
import com.demo.tasks.domain.model.SortQuery;
import com.demo.tasks.domain.model.TaskFilter;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {


    public static Specification<TaskEntity> buildSpecification(TaskFilter filter) {
        if (TaskFilter.isFilterEmpty(filter)) {
            return null;
        }

        return Specification.allOf(
                textContains(filter.search()),
                statusEquals(filter.status())
        );

    }

    private static Specification<TaskEntity> statusEquals(TaskStatus status) {
        return status == null ? Specification.unrestricted() :
                ((root, query, cb) ->
                        cb.equal(root.get("status"), status));
    }

    private static Specification<TaskEntity> textContains(String search) {
        return search == null ? Specification.unrestricted() :
                ((root, query, cb) ->
                        cb.or(
                                cb.like(cb.lower(root.get("title")), "%" + search.toLowerCase() + "%"),
                                cb.like(cb.lower(root.get("description")), "%" + search.toLowerCase() + "%")
                        ));
    }

    public static Sort buildSort(SortQuery pageQuery) {
        if(pageQuery.sortBy() == null) {
            return Sort.unsorted();
        }
        Sort.Direction direction = SortDirection.DESC.equals(pageQuery.direction()) ?
                Sort.Direction.DESC: Sort.Direction.ASC;

        return Sort.by(direction, pageQuery.sortBy());
    }
}
