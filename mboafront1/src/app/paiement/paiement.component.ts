import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CartService, CartItem } from '../services/cart.service';
import { ProductService } from '../services/product.service';
import { AuthService } from '../services/auth.service';
import { CommandeService } from '../services/commande.service';
import { ModalService } from '../services/modal.service';

@Component({
  selector: 'app-paiement',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './paiement.component.html',
  styleUrl: './paiement.component.css'
})
export class PaiementComponent implements OnInit {
  selectedMethod: 'MTN' | 'ORANGE' | null = null;
  phoneNumber: string = '';
  showConfirmModal: boolean = false;
  cartItems: CartItem[] = [];

  constructor(
    public cartService: CartService,
    private productService: ProductService,
    private authService: AuthService,
    private commandeService: CommandeService,
    private router: Router,
    private modal: ModalService
  ) {}

  ngOnInit() {
    this.cartItems = this.cartService.getCartItems().filter(item => {
      const stock = item.product.quantiteStock || item.product.qteProduit || 0;
      return item.selected && stock > 0;
    });

    if (this.cartItems.length === 0) {
      this.router.navigate(['/panier']);
    }
  }

  selectMethod(method: 'MTN' | 'ORANGE') {
    this.selectedMethod = method;
  }

  get isValid(): boolean {
    return this.selectedMethod !== null && this.phoneNumber.length >= 9;
  }

  openConfirmModal() {
    if (this.isValid) {
      this.showConfirmModal = true;
    }
  }

  closeModal() {
    this.showConfirmModal = false;
  }

  confirmPayment() {
    const userId = this.authService.getUserId();
    if (!userId) {
      this.modal.show({ type: 'error', title: 'Connexion requise', message: 'Veuillez vous connecter pour payer.' });
      this.router.navigate(['/']);
      return;
    }

    const payload = {
      idDistributeur: userId,
      items: this.cartItems.map(item => ({
        idProduit: item.product.id || item.product.idProduit,
        quantite: item.quantity
      }))
    };

    this.commandeService.payerPanier(payload).subscribe({
      next: (commandes: any[]) => {
        const paymentData = {
          amount: this.cartService.getTotalPrice(),
          phoneNumber: this.phoneNumber,
          method: this.selectedMethod,
          orderCount: commandes.length
        };

        this.cartService.clearCart();
        this.showConfirmModal = false;
        this.router.navigate(['/confirmationpaie'], { state: { data: paymentData } });
      },
      error: (err) => {
        this.modal.show({ type: 'error', title: 'Erreur de paiement', message: 'Une erreur est survenue lors du paiement. Veuillez réessayer.' });
        this.showConfirmModal = false;
      }
    });
  }
}
