import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authUrl = 'http://localhost:8080/api/auth';
  private userUrl = 'http://localhost:8080/api/utilisateurs';
  private currentUserSubject = new BehaviorSubject<any>(this.getUserFromStorage());
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {}

  login(data: any): Observable<any> {
    return this.http.post(`${this.authUrl}/login`, data).pipe(
      tap((response: any) => {
        if (response && response.id) {
          this.saveUser(response);
          this.http.get(`${this.userUrl}/${response.id}`).subscribe({
            next: (fullUser: any) => this.saveUser(fullUser),
            error: () => {}
          });
        } else if (response) {
          this.saveUser(response);
        }
      })
    );
  }

  register(data: any): Observable<any> {
    return this.http.post(`${this.authUrl}/register`, data);
  }

  updateUser(id: number, data: any): Observable<any> {
    return this.http.put(`${this.userUrl}/${id}`, data).pipe(
      tap((response: any) => {
        if (response) {
          const current = this.getUser();
          const updated = {
            id: current?.id || response.id || response.idUtilisateur,
            idUtilisateur: response.idUtilisateur || response.id,
            nom: response.nom || current?.nom,
            email: response.email || current?.email,
            telephone: response.telephone || current?.telephone,
            ville: response.ville || current?.ville,
            role: response.role || current?.role,
            bio: response.bio || current?.bio,
            photo: response.photo || current?.photo,
            dateCreation: response.dateCreation || current?.dateCreation,
            reliabilityScore: response.reliabilityScore || current?.reliabilityScore
          };
          this.saveUser(updated);
        }
      })
    );
  }

  getUserById(id: number): Observable<any> {
    return this.http.get(`${this.userUrl}/${id}`);
  }

  private saveUser(user: any) {
    localStorage.setItem('mboamarket_user', JSON.stringify(user));
    this.currentUserSubject.next(user);
  }

  private getUserFromStorage() {
    const user = localStorage.getItem('mboamarket_user');
    try {
      return user ? JSON.parse(user) : null;
    } catch (e) {
      return null;
    }
  }

  getUser() {
    return this.currentUserSubject.value;
  }

  getUserId(): number | null {
    const user = this.getUser();
    if (!user) return null;
    // Handle both 'id' (DTO) and 'idUtilisateur' (Entity) mapping
    const id = user.id || user.idUtilisateur;
    return id ? Number(id) : null;
  }

  getUserRole(): string | null {
    const user = this.getUser();
    return user ? (user.role ? String(user.role).toUpperCase() : null) : null;
  }

  logout() {
    localStorage.removeItem('mboamarket_user');
    this.currentUserSubject.next(null);
  }

  isAuthenticated(): boolean {
    return !!this.getUser();
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.userUrl}/${id}`);
  }
}
