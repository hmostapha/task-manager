import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/pages/login/login.component';
import { TaskListComponent } from './features/tasks/pages/task-list/task-list.component';
import {authGuard} from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'tasks',
    component: TaskListComponent,
    canActivate: [authGuard]
  },
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  }
];
