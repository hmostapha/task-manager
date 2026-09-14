package com.demo.tasks.domain.model;

import java.util.List;

public record PageResult<T>(
        List<T> content,
        int page,
        int totalPage,
        long totalElements
) {}