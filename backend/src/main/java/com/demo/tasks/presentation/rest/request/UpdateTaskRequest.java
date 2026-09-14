package com.demo.tasks.adapter.rest.request;

import com.demo.tasks.adapter.rest.validation.SafeString;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateTaskRequest (

        @NotBlank
        @Size(max = 100)
        @Schema(description = "Task title")
        @SafeString
        String title,

        @Size(max = 500)
        @Schema(description = "Task description")
        @SafeString
        String description
)
{}
