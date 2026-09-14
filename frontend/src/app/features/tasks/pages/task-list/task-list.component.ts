import { Component, inject, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { HttpResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatOptionModule } from '@angular/material/core';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';

import { TaskService } from '../../services/task.service';
import { Task, TaskStatus } from '../../models/task.model';
import { TaskDialogComponent } from '../../components/task-dialog/task-dialog.component';
import { ConfirmDialogComponent } from '../../components/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-task-list',
  standalone: true,
  imports: [
    DatePipe,
    FormsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatMenuModule,
    MatOptionModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatSelectModule,
    MatTableModule
  ],
  templateUrl: './task-list.component.html',
  styleUrl: './task-list.component.css'
})
export class TaskListComponent {

  private readonly taskService = inject(TaskService);
  private readonly dialog = inject(MatDialog);

  readonly tasks = signal<Task[]>([]);
  readonly loading = signal(false);

  readonly page = signal(0);
  readonly size = signal(20);
  readonly totalElements = signal(0);
  readonly totalPages = signal(0);

  readonly TaskStatus = TaskStatus;

  search = '';
  status: TaskStatus | '' = '';

  readonly displayedColumns = [
    'title',
    'description',
    'status',
    'startAt',
    'endAt',
    'actions'
  ];

  constructor() {
    this.loadTasks();
  }

  loadTasks(): void {
    this.loading.set(true);

    this.taskService
      .getTasks(
        this.page(),
        this.size(),
        this.search,
        this.status
      )
      .subscribe({
        next: (response: HttpResponse<Task[]>) => {
          this.tasks.set(response.body ?? []);

          this.totalElements.set(
            Number(
              response.headers.get('X-Total-Elements') ?? 0
            )
          );

          this.totalPages.set(
            Number(
              response.headers.get('X-Total-Pages') ?? 0
            )
          );

          this.loading.set(false);
        },

        error: error => {
          console.error('Failed to load tasks', error);
          this.loading.set(false);
        }
      });
  }

  searchTasks(): void {
    this.page.set(0);
    this.loadTasks();
  }

  changePage(event: PageEvent): void {
    this.page.set(event.pageIndex);
    this.size.set(event.pageSize);

    this.loadTasks();
  }

  statusLabel(status: TaskStatus): string {
    switch (status) {
      case TaskStatus.TODO:
        return 'To do';

      case TaskStatus.IN_PROGRESS:
        return 'In progress';

      case TaskStatus.DONE:
        return 'Done';

      default:
        return status;
    }
  }

  createTask(): void {
    const dialogRef = this.dialog.open(TaskDialogComponent, {
      width: '600px',
      data: {}
    });

    dialogRef.afterClosed().subscribe(task => {
      if (!task) {
        return;
      }

      this.tasks.update(tasks => [
        task,
        ...tasks
      ]);

      this.totalElements.update(total => total + 1);
    });
  }

  editTask(task: Task): void {
    const dialogRef = this.dialog.open(TaskDialogComponent, {
      width: '600px',
      data: { task }
    });

    dialogRef.afterClosed().subscribe(updatedTask => {
      if (!updatedTask) {
        return;
      }

      this.tasks.update(tasks =>
        tasks.map(current =>
          current.id === updatedTask.id
            ? updatedTask
            : current
        )
      );
    });
  }

  deleteTask(task: Task): void {
    const dialogRef = this.dialog.open(
      ConfirmDialogComponent,
      {
        width: '400px',
        data: {
          message: `Are you sure you want to delete "${task.title}"?`
        }
      }
    );

    dialogRef.afterClosed().subscribe(confirmed => {
      if (!confirmed) {
        return;
      }

      this.taskService.deleteTask(task.id).subscribe({
        next: () => {
          this.tasks.update(tasks =>
            tasks.filter(
              current => current.id !== task.id
            )
          );

          this.totalElements.update(
            total => Math.max(0, total - 1)
          );
        },

        error: error => {
          console.error(
            'Failed to delete task',
            error
          );
        }
      });
    });
  }
}
