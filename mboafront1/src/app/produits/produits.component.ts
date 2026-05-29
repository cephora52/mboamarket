import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../services/product.service';
import { AuthService } from '../services/auth.service';
import { ModalService } from '../services/modal.service';

@Component({
  selector: 'app-produits',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './produits.component.html',
  styleUrl: './produits.component.css'
})
export class ProduitsComponent implements OnInit {

  produits: any[] = [];
  productToDelete: number | null = null;
  user: any;

  constructor(
    private productService: ProductService,
    private authService: AuthService,
    private router: Router,
    private modal: ModalService
  ) {}

  ngOnInit() {
    this.user = this.authService.getUser();
    this.loadProduits();
  }

  loadProduits() {
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/']);
      return;
    }

    const userId = this.authService.getUserId();
    if (!userId) {
      console.warn("Session active mais ID utilisateur introuvable. Veuillez vous reconnecter.");
      return;
    }

    this.productService.getProductsByAgriculteur(userId).subscribe({
      next: (data) => {
        this.produits = data && data.length ? data : []; 
      },
      error: (err) => console.error('Erreur chargement liste des produits:', err)
    });
  }

  deleteProduct(id: number) {
    this.productToDelete = id;
  }

  confirmDelete() {
    if (this.productToDelete !== null) {
      this.productService.deleteProduct(this.productToDelete).subscribe({
        next: (res) => {
          this.loadProduits();
          this.productToDelete = null;
        },
        error: (err) => {
          this.modal.show({ type: 'error', title: 'Erreur', message: 'Impossible de supprimer ce produit pour le moment.' });
          this.productToDelete = null;
        }
      });
    }
  }

  cancelDelete() {
    this.productToDelete = null;
  }

  editProduct(id: number) {
    this.router.navigate(['/update-product', id]);
  }
}
