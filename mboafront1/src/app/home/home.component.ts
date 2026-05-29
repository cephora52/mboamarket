import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from "@angular/router";
import { FormsModule } from '@angular/forms';
import { ProductService } from '../services/product.service';
import { FavoritesService } from '../services/favorites.service';
import { CartService } from '../services/cart.service';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, NavbarComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  searchTerm: string = '';
  products: any[] = [];
  categories: any[] = [];
  filteredProducts: any[] = [];

  constructor(
    private router: Router, 
    private productService: ProductService, 
    public favoritesService: FavoritesService,
    public cartService: CartService
  ) {}

  ngOnInit(): void {
    // Fetch Products
    this.productService.getAllProducts().subscribe({
      next: (data) => {
        if (!data || data.length === 0) {
          this.products = [];
          this.filteredProducts = [];
          return;
        }
        // Safely shuffle and get up to 12 items
        this.products = [...data].sort(() => 0.5 - Math.random()).slice(0, 12);
        this.filteredProducts = [...this.products];
      },
      error: (err) => {
        console.error('Error fetching home products', err);
        this.products = [];
        this.filteredProducts = [];
      }
    });

    // Fetch Categories
    this.productService.getCategories().subscribe({
      next: (data) => {
        this.categories = Array.isArray(data) ? data : [];
      },
      error: (err) => {
        console.error('Error fetching categories', err);
        this.categories = [];
      }
    });
  }

  onSearch(): void {
    const searchStr = this.searchTerm.toLowerCase().trim();
    if (!searchStr) {
      this.filteredProducts = [...this.products];
    } else {
      this.filteredProducts = this.products.filter(p => {
        const prodName = String(p.nomProduit || p.nom || '').toLowerCase();
        return prodName.includes(searchStr);
      });
    }
  }

  getCategoryImage(categoryName: string): string {
    if (!categoryName) return 'https://images.unsplash.com/photo-1500651230702-0e2d8a4914ad?w=500';
    
    const name = categoryName.toLowerCase().trim();
    
    if (name.includes('fruit')) {
      return 'https://www.gastronomiac.com/wp/wp-content/uploads/2021/08/Fruits.jpg';
    } 
    
    if (name.includes('legumineuse') || name.includes('légumineuse')) {
      return 'https://maxlaffame.com/wp-content/uploads/2015/05/Legumineuse.jpg';
    }
    
    if (name.includes('légume') || name.includes('legume')) {
      // Using encoded 'é' as %C3%A9 for maximum compatibility
      return 'https://www.cuisine-passion.net/wp-content/uploads/2020/03/l%C3%A9gumes.jpg';
    }
    
    if (name.includes('tubercule') || name.includes('manioc') || name.includes('patate')) {
      return 'https://www.easymarket.pf/wp-content/uploads/2017/11/EM-Image-blog-.jpg';
    }
    
    if (name.includes('oleagineux') || name.includes('oléagineux') || name.includes('huile')) {
      return 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRspHXi9FON4Lu3FXMUNIyKV6aRt4R8BcIZNQ&s';
    }
    
    if (name.includes('céréale') || name.includes('cereale') || name.includes('grain')) {
      return 'https://images.unsplash.com/photo-1574323347407-f5e1ad6d020b?w=500';
    }
    
    if (name.includes('épice') || name.includes('epice') || name.includes('poivre')) {
      return 'https://images.unsplash.com/photo-1506368249639-73a05d6f6488?w=500';
    }
    
    if (name.includes('animal') || name.includes('viande') || name.includes('oeuf')) {
      return 'https://images.unsplash.com/photo-1516467508483-a7212febe31a?w=500';
    }
    
    return 'https://images.unsplash.com/photo-1500651230702-0e2d8a4914ad?w=500';
  }

  toggleFavorite(product: any, event: Event): void {
    event.stopPropagation();
    this.favoritesService.toggleFavorite(product);
  }

  isFavorite(product: any): boolean {
    return this.favoritesService.isFavorite(product);
  }

  addToCart(product: any, event: Event): void {
    event.stopPropagation();
    this.cartService.addToCart(product, 1);
  }

  getCategoryName(product: any): string {
    if (product.nomCategorie) return product.nomCategorie;
    if (product.categorieNom) return product.categorieNom;
    if (product.idCategorie?.nomCategorie) return product.idCategorie.nomCategorie;
    if (typeof product.idCategorie === 'string') return product.idCategorie;
    return '';
  }
}
