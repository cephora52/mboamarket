import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { AuthService } from './auth.service';
import { ModalService } from './modal.service';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class FavoritesService {
  private apiUrl = 'http://localhost:8080/favoris';
  private favoritesSubject = new BehaviorSubject<any[]>([]);
  favorites$ = this.favoritesSubject.asObservable();

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private modal: ModalService
  ) {
    this.authService.currentUser$.subscribe(() => {
      this.loadFavorites();
    });
  }

  loadFavorites() {
    const userId = this.authService.getUserId();
    if (!userId) {
      this.favoritesSubject.next([]);
      return;
    }

    this.http.get<any[]>(`${this.apiUrl}/${userId}`).subscribe({
      next: (favs) => this.favoritesSubject.next(favs || []),
      error: (err) => console.error('Erreur chargement favoris:', err)
    });
  }

  toggleFavorite(product: any): void {
    const userId = this.authService.getUserId();
    if (!userId) {
      this.modal.show({ type: 'error', title: 'Connexion requise', message: 'Veuillez vous connecter pour gérer vos favoris.' });
      return;
    }

    const productId = product.id || product.idProduit;
    const isFav = this.isFavorite(product);

    if (isFav) {
      this.removeFavorite(product);
    } else {
      const payload = { idUtilisateur: userId, idProduit: productId };
      this.http.post(this.apiUrl, payload).subscribe({
        next: () => {
          this.loadFavorites();
        },
        error: (err) => {
          this.modal.show({ type: 'error', title: 'Erreur', message: 'Erreur lors de l\'ajout aux favoris.' });
        }
      });
    }
  }

  removeFavorite(product: any): void {
    const userId = this.authService.getUserId();
    if (!userId) return;

    const productId = product.id || product.idProduit;
    this.http.delete(`${this.apiUrl}/${userId}/${productId}`).subscribe(() => {
      this.loadFavorites();
    });
  }

  isFavorite(product: any): boolean {
    const current = this.favoritesSubject.value;
    const pid = product.id || product.idProduit;
    return current.some(p => (p.id || p.idProduit) === pid);
  }

  getFavorites(): any[] {
    return this.favoritesSubject.value;
  }
}
