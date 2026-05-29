import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { HttpClient } from '@angular/common/http';
import { ModalService } from './modal.service';

export interface CartItem {
  id: number;
  product: any;
  quantity: number;
  selected: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiUrl = 'http://localhost:8080/panier';
  private cartItems: CartItem[] = [];
  private cartSubject = new BehaviorSubject<CartItem[]>([]);
  cart$ = this.cartSubject.asObservable();

  constructor(
    private authService: AuthService,
    private http: HttpClient,
    private modal: ModalService
  ) {
    // Rend le service réactif : recharge le panier dès que l'utilisateur change/se connecte
    this.authService.currentUser$.subscribe(user => {
      this.loadCart();
    });
  }

  loadCart() {
    const userId = this.authService.getUserId();
    if (!userId) {
      this.cartItems = [];
      this.cartSubject.next([]);
      return;
    }

    this.http.get<any[]>(`${this.apiUrl}/${userId}`).subscribe({
      next: (items) => {
        // Le backend renvoie des PanierItemDTO {idPanierItem, produit: ProduitDTO, quantite}
        this.cartItems = items.map(it => ({
          id: it.produit.idProduit,
          product: it.produit, 
          quantity: it.quantite,
          selected: true
        }));
        this.cartSubject.next(this.cartItems);
      },
      error: (err) => console.error('Erreur chargement panier:', err)
    });
  }

  getCartItems(): CartItem[] {
    return this.cartItems;
  }

  addToCart(product: any, quantity: number = 1) {
    const userId = this.authService.getUserId();
    if (!userId) {
      this.modal.show({ type: 'error', title: 'Connexion requise', message: 'Veuillez vous connecter pour ajouter au panier.' });
      return;
    }

    const pid = product.id || product.idProduit;
    const existing = this.cartItems.find(item => item.id === pid);
    const newQty = existing ? existing.quantity + quantity : quantity;

    const payload = { idProduit: pid, quantite: newQty };

    this.http.post(`${this.apiUrl}/${userId}`, payload).subscribe({
      next: () => {
        this.loadCart();
      },
      error: (err) => {
        this.modal.show({ type: 'error', title: 'Erreur', message: 'Erreur lors de l\'ajout au panier.' });
      }
    });
  }

  removeFromCart(productId: number) {
    const userId = this.authService.getUserId();
    if (!userId) return;

    this.http.delete(`${this.apiUrl}/${userId}/${productId}`).subscribe({
      next: () => this.loadCart(),
      error: (err) => console.error('Erreur suppression panier:', err)
    });
  }

  removeSelected() {
    const userId = this.authService.getUserId();
    if (!userId) return;

    const selectedIds = this.cartItems.filter(it => it.selected).map(it => it.id);
    selectedIds.forEach(id => {
      this.http.delete(`${this.apiUrl}/${userId}/${id}`).subscribe({
        next: () => this.loadCart(),
        error: (err) => console.error('Erreur suppression sélectionné:', err)
      });
    });
  }

  clearCart() {
    const userId = this.authService.getUserId();
    if (!userId) return;

    this.http.delete(`${this.apiUrl}/${userId}`).subscribe({
      next: () => this.loadCart(),
      error: (err) => console.error('Erreur vidage panier:', err)
    });
  }

  updateQuantity(productId: number, quantity: number) {
    const userId = this.authService.getUserId();
    if (!userId || quantity <= 0) return;

    const payload = { idProduit: productId, quantite: quantity };
    this.http.post(`${this.apiUrl}/${userId}`, payload).subscribe({
      next: () => this.loadCart(),
      error: (err) => console.error('Erreur mise à jour quantité:', err)
    });
  }

  toggleSelection(productId: number) {
    const item = this.cartItems.find(i => i.id === productId);
    if (item) {
      item.selected = !item.selected;
      this.cartSubject.next(this.cartItems);
    }
  }

  selectAll(selected: boolean) {
    this.cartItems.forEach(item => {
      item.selected = selected;
    });
    this.cartSubject.next(this.cartItems);
  }

  getCartCount(): number {
    return this.cartItems.length;
  }

  getSelectedCount(): number {
    return this.cartItems.filter(item => item.selected).length;
  }

  getTotalPrice(): number {
    return this.cartItems
      .filter(item => item.selected)
      .reduce((acc, item) => acc + (item.product.prix || 0) * item.quantity, 0);
  }
}
