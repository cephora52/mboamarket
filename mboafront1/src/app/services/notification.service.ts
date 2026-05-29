import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private apiUrl = 'http://localhost:8080/notifications';
  private notificationsSubject = new BehaviorSubject<any[]>([]);
  notifications$ = this.notificationsSubject.asObservable();

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {
    this.authService.currentUser$.subscribe(() => {
      this.loadNotifications();
    });
  }

  loadNotifications() {
    const userId = this.authService.getUserId();
    if (!userId) {
      this.notificationsSubject.next([]);
      return;
    }

    this.http.get<any[]>(`${this.apiUrl}/user/${userId}`).subscribe({
      next: (notifs) => this.notificationsSubject.next(notifs || []),
      error: (err) => console.error('Erreur chargement notifications:', err)
    });
  }

  markAsRead(id: number) {
    this.http.put(`${this.apiUrl}/${id}/read`, {}).subscribe(() => {
      this.loadNotifications();
    });
  }

  deleteNotification(id: number) {
    this.http.delete(`${this.apiUrl}/${id}`).subscribe(() => {
      this.loadNotifications();
    });
  }

  getUnreadCount(): number {
    return this.notificationsSubject.value.filter(n => !n.lu).length;
  }
}
