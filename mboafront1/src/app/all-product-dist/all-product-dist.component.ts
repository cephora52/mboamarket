import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../services/product.service';
import { FavoritesService } from '../services/favorites.service';
import { CartService } from '../services/cart.service';
import { NotificationService } from '../services/notification.service';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-all-product-dist',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, NavbarComponent],
  templateUrl: './all-product-dist.component.html',
  styleUrl: './all-product-dist.component.css'
})
export class AllProductDistComponent implements OnInit {
  products: any[] = [];
  categories: any[] = [];
  filteredProducts: any[] = [];

  // Filter states
  searchTerm: string = '';
  selectedCategory: string | null = null;
  selectedRegion: string | null = null;
  minPrice: number = 0;
  maxPrice: number = 1000000;
  hideCategoriesFilter: boolean = false;

  regionsList: string[] = [
    'Adamaoua (Ngaoundéré)', 'Centre (Yaoundé)', 'Est (Bertoua)', 
    'Extrême-Nord (Maroua)', 'Littoral (Douala)', 'Nord (Garoua)', 
    'Nord-Ouest (Bamenda)', 'Ouest (Bafoussam)', 'Sud (Ebolowa)', 'Sud-Ouest (Buéa)'
  ];
  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
    private favoritesService: FavoritesService,
    public cartService: CartService,
    public notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['category']) {
        this.selectedCategory = params['category'];
        this.hideCategoriesFilter = true;
      }
      if (params['search']) {
        this.searchTerm = params['search'];
      }
      this.applyFilters();
    });

    this.productService.getAllProducts().subscribe({
      next: (data) => {
        this.products = data;
        this.applyFilters();
      },
      error: (err) => {
        console.error('ERREUR Backend Produits:', err);
        this.products = [];
        this.applyFilters();
      }
    });

    this.productService.getCategories().subscribe({
      next: (data) => {
        this.categories = data;
      },
      error: (err) => {
        console.error('ERREUR Backend Catégories:', err);
        this.categories = [];
      }
    });
  }

  getArticlesCount(categoryName: string): number {
    if (!categoryName) return 0;
    const normalizedName = categoryName.toLowerCase().trim();
    return this.products.filter(p => {
      const pCat = (p.nomCategorie || (p.categorie && (p.categorie.nom || p.categorie.nomCategorie)) || '').toLowerCase().trim();
      return pCat === normalizedName;
    }).length;
  }

  toggleCategory(categoryName: string, event: any): void {
    const isChecked = event.target.checked;
    if (isChecked) {
      this.selectedCategory = categoryName;
    } else {
      if (this.selectedCategory === categoryName) {
        this.selectedCategory = null;
      }
    }
    this.applyFilters();
  }

  toggleRegion(region: string): void {
    if (this.selectedRegion === region) {
      this.selectedRegion = null;
    } else {
      this.selectedRegion = region;
    }
    this.applyFilters();
  }

  onPriceChange(): void {
    this.applyFilters();
  }

  applyFilters(): void {
    if (!this.products) {
      this.filteredProducts = [];
      return;
    }

    this.filteredProducts = this.products.filter(product => {
      // 1. Catégorie
      const catName = String(product.nomCategorie || (product.categorie && (product.categorie.nom || product.categorie.nomCategorie)) || '').toLowerCase().trim();
      const selected = (this.selectedCategory || '').toLowerCase().trim();
      const matchCategory = !this.selectedCategory || catName === selected || catName.includes(selected);

      // 2. Région
      const prodRegion = String(product.localite || product.localisation || '').toLowerCase().trim();
      const selectedReg = (this.selectedRegion || '').toLowerCase().trim();
      const matchRegion = !this.selectedRegion || prodRegion.includes(selectedReg.split(' ')[0]) || selectedReg.includes(prodRegion);

      // 3. Prix (avec sécurité si prix nul)
      const priceSource = product.prix || 0;
      const price = typeof priceSource === 'number' ? priceSource : parseFloat(priceSource.toString().replace(/[^0-9.]/g, '')) || 0;
      const matchPrice = price >= this.minPrice && price <= this.maxPrice;

      // 4. Recherche (nom produit + nom agriculteur)
      const searchStr = this.searchTerm.toLowerCase().trim();
      const prodName = String(product.nomProduit || product.nom || '').toLowerCase();
      const farmerName = String(product.nomAgriculteur || '').toLowerCase();
      const matchSearch = !searchStr || prodName.includes(searchStr) || farmerName.includes(searchStr);

      return matchCategory && matchRegion && matchPrice && matchSearch;
    });

  }

  toggleFavorite(product: any): void {
    this.favoritesService.toggleFavorite(product);
  }

  isFavorite(product: any): boolean {
    return this.favoritesService.isFavorite(product);
  }

  addToCart(product: any): void {
    if (product) {
       this.cartService.addToCart(product, 1);
    }
  }
}


