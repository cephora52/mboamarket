import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { ProductService } from '../services/product.service';
import { AuthService } from '../services/auth.service';
import { CommandeService } from '../services/commande.service';
import { NotificationService } from '../services/notification.service';

@Component({
  selector: 'app-farmer',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './farmer.component.html',
  styleUrl: './farmer.component.css'
})
export class FarmerComponent implements OnInit {
  Math = Math;
  myProducts: any[] = [];
  displayProducts: any[] = [];
  user: any;
  showLogoutModal = false;
  showNotifications = false;
  notifications: any[] = [];
  unreadCount = 0;
  
  stats = {
    activeCount: 0,
    pendingOrders: 0,
    totalSales: '0',
    lowStockCount: 0
  };

  constructor(
    private productService: ProductService,
    public authService: AuthService,
    private commandeService: CommandeService,
    private notificationService: NotificationService,
    public router: Router
  ) {}

  ngOnInit() {
    this.user = this.authService.getUser();
    const userId = this.authService.getUserId();
    
    if (!userId) {
      console.warn('FarmerComponent: Aucun userId trouvé dans la session !');
      return;
    }

    // Load Products
    this.productService.getProductsByAgriculteur(userId).subscribe({
      next: (data: any[]) => {
        this.myProducts = data || [];
        this.stats.activeCount = this.myProducts.length;
        this.stats.lowStockCount = this.myProducts.filter(p => (p.qteProduit || 0) < 5).length;
        this.displayProducts = [...this.myProducts]
          .sort((a, b) => b.idProduit - a.idProduit)
          .slice(0, 8);
      },
      error: (err) => console.error('Error loading farmer products', err)
    });

    // Load Orders for Stats
    this.commandeService.getCommandesByAgriculteur(userId).subscribe({
      next: (commandes: any[]) => {
        this.stats.pendingOrders = (commandes || []).length;
        const total = (commandes || []).reduce((acc, c) => acc + (c.montantTotal || 0), 0);
        this.stats.totalSales = new Intl.NumberFormat().format(total);
      },
      error: (err) => console.error('Error loading farmer stats', err)
    });

    // Load Notifications
    this.notificationService.notifications$.subscribe(notifs => {
      this.notifications = notifs;
      this.unreadCount = notifs.filter(n => !n.lu).length;
    });
    this.notificationService.loadNotifications();
  }

  toggleNotifications() {
    this.showNotifications = !this.showNotifications;
  }

  handleNotificationClick(n: any): void {
    this.markAsRead(n.idNotification);
    this.showNotifications = false;
    const titre = (n.titre || '').toLowerCase();
    if (titre.includes('commande') || titre.includes('livraison')) {
      this.router.navigate(['/commande-farmer']);
    } else if (titre.includes('message')) {
      this.router.navigate(['/messages']);
    } else {
      this.router.navigate(['/farmer']);
    }
  }

  markAsRead(id: number) {
    this.notificationService.markAsRead(id);
  }

  deleteNotification(event: Event, id: number) {
    event.stopPropagation();
    this.notificationService.deleteNotification(id);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
