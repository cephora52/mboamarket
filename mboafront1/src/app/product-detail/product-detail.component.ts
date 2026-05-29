import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ProductService } from '../services/product.service';
import { CartService } from '../services/cart.service';

import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [CommonModule, RouterModule, NavbarComponent],
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.css'
})
export class ProductDetailComponent implements OnInit {

  productId: any;
  product: any = null;
  loading: boolean = true;
  quantity: number = 1;
  farmerProducts: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    public cartService: CartService,
    private router: Router
  ) {}

  ngOnInit() {
    this.productId = this.route.snapshot.paramMap.get('id');
    
    if (this.productId) {
      this.productService.getProductById(this.productId).subscribe({
        next: (data) => {
          this.product = data;
          this.loading = false;
          this.loadFarmerProducts();
        },
        error: (err) => {
          console.error("Error fetching product", err);
          this.loading = false;
        }
      });
    }
  }

  loadFarmerProducts(): void {
    const farmerId = this.product?.idAgriculteur;
    if (!farmerId) return;
    this.productService.getProductsByAgriculteur(farmerId).subscribe({
      next: (data) => {
        this.farmerProducts = (data || []).filter(
          (p: any) => (p.idProduit || p.id) !== (this.product?.idProduit || this.product?.id)
        ).slice(0, 4);
      },
      error: () => this.farmerProducts = []
    });
  }

  contactProducer(): void {
    const producerId = this.product?.idAgriculteur;
    if (producerId) {
      this.router.navigate(['/messages'], { queryParams: { u: producerId } });
    }
  }

  increaseQuantity() {
    this.quantity++;
  }

  decreaseQuantity() {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  addToCart() {
    if (this.product) {
      this.cartService.addToCart(this.product, this.quantity);
    }
  }

  buyNow() {
    if (this.product) {
      this.cartService.addToCart(this.product, this.quantity);
      this.router.navigate(['/paiement']);
    }
  }
}

