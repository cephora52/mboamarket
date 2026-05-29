import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { CartService } from '../services/cart.service';
import { NotificationService } from '../services/notification.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {
  searchTerm: string = '';
  user: any = null;

  showNotifications = false;
  notifications: any[] = [];
  unreadCount = 0;

  constructor(
    private authService: AuthService, 
    public cartService: CartService,
    private notificationService: NotificationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.authService.currentUser$.subscribe(u => {
      this.user = u;
    });

    this.notificationService.notifications$.subscribe(notifs => {
      this.notifications = notifs;
      this.unreadCount = notifs.filter(n => !n.lu).length;
    });
  }

  onSearch(): void {
    if (this.searchTerm.trim()) {
      this.router.navigate(['/all-product-dist'], {
        queryParams: { search: this.searchTerm.trim() }
      });
    }
  }

  toggleNotifications(): void {
    this.showNotifications = !this.showNotifications;
    if (this.showNotifications) {
      this.notificationService.loadNotifications();
    }
  }

  handleNotificationClick(n: any): void {
    this.notificationService.markAsRead(n.idNotification);
    this.showNotifications = false;
    const titre = (n.titre || '').toLowerCase();
    if (titre.includes('commande') || titre.includes('livraison') || titre.includes('prête') || titre.includes('confirm')) {
      const role = this.authService.getUserRole();
      if (role === 'AGRICULTEUR') {
        this.router.navigate(['/commande-farmer']);
      } else {
        this.router.navigate(['/commande-distributeur']);
      }
    } else if (titre.includes('message')) {
      this.router.navigate(['/messages']);
    } else if (titre.includes('paiement')) {
      this.router.navigate(['/confirmationpaie']);
    } else {
      this.router.navigate(['/home']);
    }
  }

  deleteNotification(event: Event, id: number): void {
    event.stopPropagation();
    this.notificationService.deleteNotification(id);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
