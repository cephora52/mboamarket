import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommandeService } from '../services/commande.service';
import { AuthService } from '../services/auth.service';
import { ReceiptService } from '../services/receipt.service';
import { NotificationService } from '../services/notification.service';

@Component({
  selector: 'app-commande-distributeur',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './commande-distributeur.component.html',
  styleUrl: './commande-distributeur.component.css'
})
export class CommandeDistributeurComponent implements OnInit, OnDestroy {
  orders: any[] = [];
  totalExpenses = 0;
  deliveryCount = 0;
  user: any;
  private refreshInterval: any;

  // Notifications
  showNotifications = false;
  notifications: any[] = [];
  unreadCount = 0;

  // Demande de confirmation
  selectedOrder: any = null;
  confirmError = '';
  showConfirmLoading = false;

  constructor(
    private commandeService: CommandeService,
    private authService: AuthService,
    private receiptService: ReceiptService,
    private notificationService: NotificationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getUser();
    this.fetchOrders();

    this.notificationService.notifications$.subscribe(notifs => {
      this.notifications = notifs;
      this.unreadCount = notifs.filter(n => !n.lu).length;
    });
    this.notificationService.loadNotifications();

    this.refreshInterval = setInterval(() => this.fetchOrders(), 15000);
  }

  ngOnDestroy(): void {
    if (this.refreshInterval) clearInterval(this.refreshInterval);
  }

  fetchOrders(): void {
    const userId = this.authService.getUserId();
    if (!userId) return;

    this.commandeService.getCommandesByDistributeur(userId).subscribe({
      next: (data) => {
        const groupMap = new Map<string, any>();

        data.forEach((o: any) => {
          const key = `${o.idCommande}_${o.nomAgriculteur || 'unknown'}`;

          if (!groupMap.has(key)) {
            groupMap.set(key, {
              id: `#CMD-${o.idCommande}`,
              rawId: o.idCommande,
              date: o.dateCommande ? new Date(o.dateCommande).toLocaleDateString() : 'N/A',
              producer: o.nomAgriculteur || 'Producteur inconnu',
              producerId: o.idAgriculteur,
              statut: o.statutCmd || 'CONFIRMEE',
              quantiteLivree: o.quantiteLivree,
              items: [],
              photo: 'https://images.unsplash.com/photo-1592924357228-91a4daadcfea?w=200'
            });
          }

          const group = groupMap.get(key);
          if (o.items && o.items.length > 0) {
            group.items.push(...o.items);
            if (o.items[0].imageProduit) group.photo = o.items[0].imageProduit;
          }
        });

        this.orders = Array.from(groupMap.values()).map(g => {
          let productNames = '';
          let totalQuantity = 0;
          let totalAmount = 0;

          if (g.items && g.items.length > 0) {
            productNames = g.items.map((i: any) => i.nomProduit).join(', ');
            totalQuantity = g.items.reduce((sum: number, i: any) => sum + (i.quantite || 0), 0);
            totalAmount = g.items.reduce((sum: number, i: any) => sum + (i.prix * i.quantite), 0);
          } else {
            productNames = 'Commande de Gros';
            totalQuantity = 0;
            totalAmount = 0;
          }

          return {
            ...g,
            product: productNames,
            quantity: totalQuantity || '-',
            total: totalAmount
          };
        });

        this.calculateStats(data.length);
      },

      error: (err) => {
        console.error('Error fetching distributor orders:', err);
      }
    });
  }

  demanderConfirmation(order: any): void {
    this.selectedOrder = order;
    this.confirmError = '';
    this.showConfirmLoading = true;
    this.commandeService.demanderConfirmation(order.rawId).subscribe({
      next: (res) => {
        order.statut = 'ATTENTE_CONFIRMATION';
        this.showConfirmLoading = false;
        this.selectedOrder = null;
        this.fetchOrders();
      },
      error: (err) => {
        this.showConfirmLoading = false;
        const msg = err?.error?.message || err?.message || '';
        this.confirmError = msg || 'Erreur lors de la demande de confirmation.';
        setTimeout(() => { this.confirmError = ''; }, 8000);
      }
    });
  }

  private calculateStats(originalDataLength: number): void {
    this.totalExpenses = (this.orders || []).reduce((acc, o) => acc + (o.total || 0), 0);
    this.deliveryCount = originalDataLength;
  }

  downloadReceipt(order: any): void {
    this.receiptService.generateReceipt(order, 'distributor');
  }

  contactProducer(order: any): void {
    if (order.producerId) {
      this.router.navigate(['/messages'], { queryParams: { u: order.producerId } });
    }
  }

  toggleNotifications(): void {
    this.showNotifications = !this.showNotifications;
  }

  handleNotificationClick(n: any): void {
    this.notificationService.markAsRead(n.idNotification);
    this.showNotifications = false;
    const titre = (n.titre || '').toLowerCase();
    if (titre.includes('commande') || titre.includes('livraison') || titre.includes('prête')) {
      this.router.navigate(['/commande-distributeur']);
    } else if (titre.includes('message')) {
      this.router.navigate(['/messages']);
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

  updateStatus(order: any, newStatus: string): void {
    this.commandeService.updateOrderStatus(order.rawId, newStatus).subscribe({
      next: () => {
        order.statut = newStatus;
      },
      error: (err) => {
        console.error('Error updating status:', err);
      }
    });
  }
}
