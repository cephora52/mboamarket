import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../services/product.service';
import { AuthService } from '../services/auth.service';
import { ModalService } from '../services/modal.service';

@Component({
  selector: 'app-add-product',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './add-product.component.html',
  styleUrl: './add-product.component.css'
})
export class AddProductComponent implements OnInit {
  product: any = {
    nomProduit: '',
    idCategorie: null,
    localite: '',
    qteProduit: null,
    uniteMesure: 'kg',
    prix: null,
    photo: '',
    idAgriculteur: null
  };

  showSuccessPopup = false;
  categories: any[] = [];
  units = ['kg', 'sac', 'tonne', 'cageot'];
  user: any;

  constructor(
    private productService: ProductService,
    private authService: AuthService,
    private router: Router,
    private modal: ModalService
  ) {}

  ngOnInit() {
    this.user = this.authService.getUser();
    this.productService.getCategories().subscribe({
      next: (data) => {
        this.categories = data;
      },
      error: (err) => console.error('Erreur chargement categories', err)
    });
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        this.product.photo = reader.result as string; // Stocke l'image en Base64
      };
      reader.readAsDataURL(file);
    }
  }

  onSubmit() {
    const userId = this.authService.getUserId();
    if (!userId) {
      this.modal.show({ type: 'error', title: 'Session expirée', message: 'Veuillez vous reconnecter.' });
      this.router.navigate(['/']);
      return;
    }

    if (!this.product.nomProduit || !this.product.idCategorie || !this.product.qteProduit || !this.product.prix) {
      this.modal.show({ type: 'error', title: 'Champs requis', message: 'Veuillez remplir tous les champs obligatoires.' });
      return;
    }

    this.product.idAgriculteur = userId;

    this.productService.createProduct(this.product).subscribe({
      next: (res) => {
        this.showSuccessPopup = true;
        setTimeout(() => {
          this.showSuccessPopup = false;
          this.router.navigate(['/produits']);
        }, 2000);
      },
      error: (err) => {
        this.modal.show({ type: 'error', title: 'Erreur', message: 'Erreur lors de la création du produit.' });
      }
    });
  }
}
