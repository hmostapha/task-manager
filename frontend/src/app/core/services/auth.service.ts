import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);

  private authenticated = false;

  login(username: string, password: string): Observable<void> {
    const body = new HttpParams()
      .set('username', username)
      .set('password', password);

    return this.http.post<void>(
      '/api/v1/auth/login',
      body.toString(),
      {
        headers: new HttpHeaders({
          'Content-Type': 'application/x-www-form-urlencoded'
        }),
        withCredentials: true
      }
    ).pipe(
      tap(() => {
        this.authenticated = true;
      })
    );
  }

  logout(): void {
    this.http.post<void>(
      '/api/v1/auth/logout',
      {},
      { withCredentials: true }
    ).subscribe({
      next: () => {
        this.authenticated = false;
        this.router.navigate(['/login']);
      },
      error: () => {
        this.authenticated = false;
        this.router.navigate(['/login']);
      }
    });
  }

  isAuthenticated(): boolean {
    return this.authenticated;
  }
}
