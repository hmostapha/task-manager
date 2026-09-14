package com.demo.tasks.domain.model;

import com.demo.tasks.domain.enums.SortDirection;

public record SortQuery(int page,
                        int size,
                        SortDirection direction,
                        String sortBy)
{}

