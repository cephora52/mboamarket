import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CartService, CartItem } from '../services/cart.service';

@Component({
  selector: 'app-panier',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './panier.component.html',
  styleUrl: './panier.component.css'
})
export class PanierComponent implements OnInit {
  cartItems: CartItem[] = [];
  allSelected: boolean = true;

  constructor(public cartService: CartService) {}

  ngOnInit() {
    this.cartService.cart$.subscribe(items => {
      this.cartItems = items;
      this.updateAllSelectedStatus();
    });
  }

  updateAllSelectedStatus() {
    const inStockItems = this.cartItems.filter(item => (item.product.quantiteStock || item.product.qteProduit || 0) > 0);
    this.allSelected = inStockItems.length > 0 && inStockItems.every(item => item.selected);
  }

  toggleAll(event: any) {
    const checked = event.target.checked;
    this.allSelected = checked;
    this.cartService.selectAll(checked);
  }

  toggleItem(item: CartItem) {
    this.cartService.toggleSelection(item.id);
  }

  increaseQuantity(item: CartItem) {
    this.cartService.updateQuantity(item.id, item.quantity + 1);
  }

  decreaseQuantity(item: CartItem) {
    if (item.quantity > 1) {
      this.cartService.updateQuantity(item.id, item.quantity - 1);
    }
  }

  removeSelected() {
    this.cartService.removeSelected();
  }

  getItemTotalPrice(item: CartItem): number {
    return (item.product.prix || 0) * item.quantity;
  }

  get selectedCount(): number {
    return this.cartService.getSelectedCount();
  }

  get totalAmount(): number {
    return this.cartService.getTotalPrice();
  }
}
