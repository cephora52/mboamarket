import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommandeService {

  private apiUrl = 'http://localhost:8080/commandes';

  constructor(private http: HttpClient) {}

  getAllCommandes(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getCommandeById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  getCommandesByDistributeur(id: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/distributeur/${id}`);
  }

  getCommandesByAgriculteur(id: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/agriculteur/${id}`);
  }

  payerPanier(data: any): Observable<any[]> {
    return this.http.post<any[]>(`${this.apiUrl}/payer`, data);
  }

  createCommande(commande: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, commande);
  }

  updateCommande(id: number, commande: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, commande);
  }

  updateOrderStatus(id: number, status: string): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}/status`, status, {
      headers: { 'Content-Type': 'text/plain' }
    });
  }

  preparerLivraison(id: number): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}/preparer`, {});
  }

  demanderConfirmation(id: number): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}/demander-confirmation`, {});
  }

  validerParAgriculteur(id: number): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}/valider-agriculteur`, {});
  }

  deleteCommande(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
