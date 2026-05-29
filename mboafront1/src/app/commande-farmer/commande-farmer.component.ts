import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommandeService } from '../services/commande.service';
import { AuthService } from '../services/auth.service';
import { ReceiptService } from '../services/receipt.service';

@Component({
  selector: 'app-commande-farmer',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './commande-farmer.component.html',
  styleUrl: './commande-farmer.component.css'
})
export class CommandeFarmerComponent implements OnInit {
  orders: any[] = [];
  totalRevenue = 0;
  confirmedOrders = 0;

  currentPage = 1;
  itemsPerPage = 10;
  totalOrders = 0;
  user: any;

  // Preparer modal
  showPreparerModal = false;
  selectedOrder: any = null;

  constructor(
    private commandeService: CommandeService,
    private authService: AuthService,
    private receiptService: ReceiptService,
    public router: Router
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getUser();
    this.fetchOrders();
  }

  fetchOrders(): void {
    const userId = this.authService.getUserId();
    if (!userId) return;

    this.commandeService.getCommandesByAgriculteur(userId).subscribe({
      next: (data) => {
        const orderMap = new Map<number, any>();
        let revenue = 0;

        data.forEach((o: any) => {
          revenue += (o.montantTotal || 0);

          if (!orderMap.has(o.idCommande)) {
            orderMap.set(o.idCommande, {
              id: o.idCommande,
              date: o.dateCommande ? new Date(o.dateCommande).toLocaleDateString() : 'N/A',
              distributor: o.nomDistributeur || 'Distributeur Inconnu',
              distributorId: o.idDistributeur,
              statut: o.statutCmd || 'CONFIRMEE',
              quantiteLivree: o.quantiteLivree,
              icon: ((o.nomDistributeur || 'D').substring(0, 2)).toUpperCase(),
              color: this.getRandomColor(),
              items: [],
              baseAmount: 0
            });
          }

          const existing = orderMap.get(o.idCommande);
          if (o.items && o.items.length > 0) {
            existing.items.push(...o.items);
          }
          existing.baseAmount += (o.montantTotal || 0);
        });

        this.orders = Array.from(orderMap.values()).map(o => {
          let productNames = '';
          let totalQuantity = 0;
          let calculatedAmount = 0;

          if (o.items && o.items.length > 0) {
            productNames = o.items.map((item: any) => item.nomProduit).join(', ');
            totalQuantity = o.items.reduce((sum: number, item: any) => sum + (item.quantite || 0), 0);
            calculatedAmount = o.items.reduce((sum: number, item: any) => sum + (item.prix * item.quantite), 0);
          } else {
            productNames = 'Commande #' + o.id;
            totalQuantity = 0;
            calculatedAmount = o.baseAmount;
          }

          return {
            ...o,
            product: productNames,
            quantity: totalQuantity || '-',
            amount: calculatedAmount
          };
        });

        this.totalOrders = this.orders.length;
        this.totalRevenue = revenue;
        this.confirmedOrders = orderMap.size;
      },

      error: (err) => {
        console.error('Error fetching farmer orders:', err);
      }
    });
  }

  openPreparer(order: any): void {
    this.selectedOrder = order;
    this.showPreparerModal = true;
  }

  submitPreparer(): void {
    this.commandeService.preparerLivraison(this.selectedOrder.id).subscribe({
      next: (res) => {
        this.selectedOrder.statut = 'ENCOURS';
        this.selectedOrder.quantiteLivree = res.quantiteLivree;
        this.showPreparerModal = false;
        this.selectedOrder = null;
      },
      error: (err) => console.error('Error preparing delivery:', err)
    });
  }

  contactDistributor(order: any): void {
    if (order.distributorId) {
      this.router.navigate(['/messages'], { queryParams: { u: order.distributorId } });
    }
  }

  validerLivraisonAgriculteur(order: any): void {
    this.commandeService.validerParAgriculteur(order.id).subscribe({
      next: (res) => {
        order.statut = 'LIVREE';
      },
      error: (err) => {
        console.error('Error confirming delivery:', err);
        alert(err?.error?.message || err?.message || 'Erreur lors de la confirmation.');
      }
    });
  }

  closePreparer(): void {
    this.showPreparerModal = false;
    this.selectedOrder = null;
  }

  private getRandomColor(): string {
    const colors = ['#ffedd5', '#fef3c7', '#ffead9', '#dcfce7', '#fecaca', '#e0f2fe'];
    return colors[Math.floor(Math.random() * colors.length)];
  }

  get totalPages() {
    return Math.ceil(this.orders.length / this.itemsPerPage);
  }

  downloadReceipt(order: any): void {
    this.receiptService.generateReceipt(order, 'farmer');
  }
}
