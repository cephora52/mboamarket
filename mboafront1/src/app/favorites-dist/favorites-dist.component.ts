import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FavoritesService } from '../services/favorites.service';
import { CartService } from '../services/cart.service';

@Component({
  selector: 'app-favorites-dist',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './favorites-dist.component.html',
  styleUrl: './favorites-dist.component.css'
})
export class FavoritesDistComponent implements OnInit {
  favorites: any[] = [];

  constructor(
    private favoritesService: FavoritesService,
    public cartService: CartService
  ) {}

  ngOnInit(): void {
    this.favoritesService.favorites$.subscribe(data => {
      this.favorites = data;
    });
  }

  removeFromFavorites(product: any, event: Event): void {
    event.stopPropagation();
    this.favoritesService.removeFavorite(product);
  }
}
