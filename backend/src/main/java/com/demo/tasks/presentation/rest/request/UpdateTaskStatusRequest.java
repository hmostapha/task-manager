package com.demo.tasks.adapter.rest.request;

import com.demo.tasks.domain.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;

public record UpdateTaskStatusRequest( @NotBlank TaskStatus status) { }
