import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef
} from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

import { Task } from '../../models/task.model';

export interface TaskDialogData {
  task?: Task;
}

@Component({
  selector: 'app-task-dialog',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './task-dialog.component.html',
  styleUrl: './task-dialog.component.css'
})
export class TaskDialogComponent {

  private readonly fb = inject(FormBuilder);

  private readonly dialogRef =
    inject(MatDialogRef<TaskDialogComponent>);

  private readonly data =
    inject<TaskDialogData>(MAT_DIALOG_DATA);

  readonly isEdit = !!this.data.task;

  readonly form = this.fb.nonNullable.group({
    title: [
      this.data.task?.title ?? '',
      [
        Validators.required,
        Validators.maxLength(100)
      ]
    ],
    description: [
      this.data.task?.description ?? '',
      Validators.maxLength(500)
    ]
  });

  save(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const value = this.form.getRawValue();

    const task: Task = {
      ...(this.data.task ?? {
        id: crypto.randomUUID(),
        status: 'TODO' as Task['status'],
        createdAt: new Date(),
        startedAt: new Date(),
        endedAt: new Date()
      }),
      title: value.title,
      description: value.description
    };

    this.dialogRef.close(task);
  }

  cancel(): void {
    this.dialogRef.close();
  }
}
