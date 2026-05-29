import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/utilisateurs';
  private authUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  getAllUsers(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  createUser(userData: any): Observable<any> {
    // We use the register endpoint for user creation to handle password hashing
    return this.http.post(`${this.authUrl}/register`, userData);
  }

  updateUser(id: number, userData: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, userData);
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
