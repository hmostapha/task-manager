package com.demo.tasks.adapter.rest;

import com.demo.tasks.domain.command.CreateTaskCommand;
import com.demo.tasks.domain.command.UpdateTaskCommand;
import com.demo.tasks.domain.enums.SortDirection;
import com.demo.tasks.domain.enums.TaskStatus;
import com.demo.tasks.domain.exception.TaskNotFoundException;
import com.demo.tasks.domain.model.PageResult;
import com.demo.tasks.domain.model.SortQuery;
import com.demo.tasks.domain.model.Task;
import com.demo.tasks.domain.model.TaskFilter;
import com.demo.tasks.domain.model.TaskId;
import com.demo.tasks.domain.port.in.FindTaskUseCasePort;
import com.demo.tasks.domain.usecases.TaskUseCases;
import com.demo.tasks.adapter.rest.dto.TaskDto;
import com.demo.tasks.adapter.rest.request.CreateTaskRequest;
import com.demo.tasks.adapter.rest.request.UpdateTaskRequest;
import com.demo.tasks.adapter.rest.request.UpdateTaskStatusRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(TaskController.BASE_PATH)
public class TaskController {

    static final String BASE_PATH = "/api/v1/tasks";

    private final TaskUseCases taskUseCases;
    private final FindTaskUseCasePort findTaskUseCasePort;

    public TaskController(TaskUseCases taskUseCases, FindTaskUseCasePort findTaskUseCasePort) {
        this.taskUseCases = taskUseCases;
        this.findTaskUseCasePort = findTaskUseCasePort;
    }

    @GetMapping
    @Operation(summary = "Get all tasks by criteria", description = "Get all tasks by criteria", tags = { "tasks-api" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TaskDto.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid query parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthenticated", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<TaskDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortSens,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status) {

        TaskFilter filter = new TaskFilter(TaskStatus.fromString(status), search);
        SortQuery sortQuery = new SortQuery(page, size, SortDirection.fromString(sortSens), sortBy);
        PageResult<Task> result = findTaskUseCasePort.findAll(filter, sortQuery);

        List<TaskDto> dtos = result.content().stream()
                .map(TaskDto::from)
                .toList();

        return ResponseEntity.ok()
                .header("X-Page", String.valueOf(result.page()))
                .header("X-Size", String.valueOf(result.content().size()))
                .header("X-Total-Pages", String.valueOf(result.totalPage()))
                .header("X-Total-Elements", String.valueOf(result.totalElements()))
                .body(dtos);
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "Get task by ID", description = "Get task by ID", tags = { "tasks-api" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid task ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthenticated", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<TaskDto> getById(@PathVariable UUID taskId) {
        TaskDto taskDto = findTaskUseCasePort.findById(TaskId.of(taskId))
                .map(TaskDto::from)
                .orElseThrow(() -> new TaskNotFoundException(TaskId.of(taskId)));

        return ResponseEntity.ok(taskDto);
    }

    @PostMapping
    @Operation(summary = "Add a new task", description = "Add a new task", tags = { "tasks-api" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthenticated", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<TaskDto> create(@Valid @RequestBody CreateTaskRequest request) {

        CreateTaskCommand createTaskCommand = new CreateTaskCommand(request.title(), request.description());
        Task task = taskUseCases.create(createTaskCommand);

        return ResponseEntity
                .created(URI.create(BASE_PATH + "/" + task.getId()))
                .body(TaskDto.from(task));
    }

    @PutMapping("/{taskId}")
    @Operation(summary = "Update an existing task", description = "Update an existing task", tags = { "tasks-api" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthenticated", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<TaskDto> update(
            @PathVariable UUID taskId,
            @Valid @RequestBody UpdateTaskRequest request) {

        UpdateTaskCommand updateTaskCommand = new UpdateTaskCommand(
                TaskId.of(taskId),
                request.title(),
                request.description());

        Task task = taskUseCases.update(updateTaskCommand);

        return ResponseEntity.ok(TaskDto.from(task));
    }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "Delete a task", description = "Delete a task by ID", tags = { "tasks-api" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid task ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthenticated", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID taskId) {
        taskUseCases.delete(TaskId.of(taskId));
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{taskId}/status")
    @Operation(summary = "Update task status", description = "Update the status of an existing task", tags = { "tasks-api" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthenticated", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<TaskDto> updateStatus(
            @PathVariable UUID taskId,
            @Valid @RequestBody UpdateTaskStatusRequest request) {

        Task task = taskUseCases.updateStatus(TaskId.of(taskId), request.status());

        return ResponseEntity.ok(TaskDto.from(task));
    }
}