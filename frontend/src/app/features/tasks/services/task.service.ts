import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams, HttpResponse} from '@angular/common/http';
import {Task, TaskStatus} from '../models/task.model';
import {CreateTaskCommand} from '../command/create-task.command';
import {UpdateTaskCommand} from '../command/update-task.command';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class TaskService {

  private readonly http= inject(HttpClient);

  getTasks(
    page: number,
    size: number,
    search: string,
    status: TaskStatus | ''
  ): Observable<HttpResponse<Task[]>> {

    let params = new HttpParams()
      .set('page', page)
      .set('size', size);

    if (search.trim()) {
      params = params.set('search', search.trim());
    }

    if (status) {
      params = params.set('status', status);
    }

    return this.http.get<Task[]>('/api/v1/tasks', {
      params,
      observe: 'response'
    });
  }

  getTask(id: string) {
    return this.http.get(`api/v1/tasks/${id}`)
  }

  createTask(task: CreateTaskCommand) {
    return this.http.post('api/v1/tasks', task)
  }

  updateTask(id: string, task: UpdateTaskCommand) {
    return this.http.put(`api/v1/tasks/${id}`, task)
  }

  deleteTask(id: string) {
    return this.http.delete(`api/v1/tasks/${id}`)
  }

  changeStatus(id: string, status: TaskStatus) {
    return this.http.patch(`api/v1/tasks/${id}`, status)
  }

}
