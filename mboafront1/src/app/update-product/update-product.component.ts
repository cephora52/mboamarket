import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../services/product.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-update-product',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './update-product.component.html',
  styleUrl: './update-product.component.css'
})
export class UpdateProductComponent implements OnInit {
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

  productId!: number;
  categories: any[] = [];
  units = ['kg', 'sac', 'tonne', 'cageot'];
  user: any;

  constructor(
    private productService: ProductService,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.user = this.authService.getUser();
    this.productId = this.route.snapshot.params['id'];
    
    // Load categories
    this.productService.getCategories().subscribe({
      next: (data) => {
        this.categories = data;
      },
      error: (err) => console.error('Erreur chargement categories', err)
    });

    // Load previous product
    if (this.productId) {
      this.productService.getProductById(this.productId).subscribe({
        next: (data) => {
          this.product = data;
        },
        error: (err) => console.error('Erreur chargement produit', err)
      });
    }
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        this.product.photo = reader.result as string;
      };
      reader.readAsDataURL(file);
    }
  }

  onSubmit() {
    const userId = this.authService.getUserId();
    if (userId) {
      this.product.idAgriculteur = userId;
    }

    if (!this.product.nomProduit || !this.product.idCategorie) {
      // rough validation - at least name and category
    }

    this.productService.updateProduct(this.productId, this.product).subscribe({
      next: (res) => {
        this.router.navigate(['/produits']);
      },
      error: (err) => console.error('Erreur modification produit', err)
    });
  }

  compareCategories(c1: any, c2: any): boolean {
    return c1 && c2 ? (c1.idCategorie || c1.id) === (c2.idCategorie || c2.id) : c1 === c2;
  }
}
