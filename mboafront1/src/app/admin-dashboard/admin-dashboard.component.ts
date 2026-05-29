import { Component, OnInit, OnDestroy, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import Chart from 'chart.js/auto';

import { UserService } from '../services/user.service';
import { AuthService } from '../services/auth.service';
import { ModalService } from '../services/modal.service';
import { ProductService } from '../services/product.service';
import { CommandeService } from '../services/commande.service';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent implements OnInit, OnDestroy, AfterViewInit {

  users: any[] = [];
  products: any[] = [];
  orders: any[] = [];
  categories: any[] = [];
  isLoading = true;
  activeTab = 'dashboard';

  // Pagination properties
  userPage = 1;
  userPageSize = 5;
  productPage = 1;
  productPageSize = 10;
  orderPage = 1;
  orderPageSize = 5;

  private previousTab = 'dashboard';

  metrics = {
    totalUsers: 0,
    totalFarmers: 0,
    totalDistributors: 0,
    totalAdmins: 0,
    totalProducts: 0,
    totalOrders: 0,
    totalRevenue: 0,
    pendingOrders: 0,
    confirmedOrders: 0,
    cancelledOrders: 0,
    availableProducts: 0,
    outOfStockProducts: 0
  };

  newUser = {
    nom: '',
    email: '',
    password: '',
    telephone: '',
    role: 'DISTRIBUTEUR',
    ville: ''
  };
  showUserForm = false;
  creatingUser = false;

  @ViewChild('roleChart') roleChartCanvas!: ElementRef;
  @ViewChild('orderChart') orderChartCanvas!: ElementRef;
  @ViewChild('categoryChart') categoryChartCanvas!: ElementRef;
  @ViewChild('revenueChart') revenueChartCanvas!: ElementRef;

  private roleChart!: Chart;
  private orderChart!: Chart;
  private categoryChart!: Chart;
  private revenueChart!: Chart;

  private dateLabels: string[] = [];
  private revenueByMonth: number[] = [];

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private productService: ProductService,
    private commandeService: CommandeService,
    private router: Router,
    private modal: ModalService
  ) {}

  ngOnInit(): void {
    if (!this.authService.isAuthenticated() || this.authService.getUserRole() !== 'ADMIN') {
      this.router.navigate(['/']);
      return;
    }
    this.loadAllData();
  }

  ngAfterViewInit(): void {
    setTimeout(() => this.createCharts(), 500);
  }

  ngOnDestroy(): void {
    this.destroyCharts();
  }

  switchTab(tabName: string): void {
    this.previousTab = this.activeTab;
    this.activeTab = tabName;

    // Reset pagination pour chaque onglet
    if (tabName === 'users') {
      this.userPage = 1;
    } else if (tabName === 'products') {
      this.productPage = 1;
    } else if (tabName === 'orders') {
      this.orderPage = 1;
    } else if (tabName === 'dashboard' && this.previousTab !== 'dashboard') {
      // Recharger les données quand on revient au dashboard
      this.loadAllData();
    }
  }

  private loadAllData(): void {
    this.isLoading = true;
    Promise.all([
      this.userService.getAllUsers().toPromise(),
      this.productService.getAllProducts().toPromise(),
      this.commandeService.getAllCommandes().toPromise(),
      this.productService.getCategories().toPromise()
    ]).then(([users, products, orders, categories]: any) => {
      this.users = users || [];
      this.products = products || [];
      this.orders = orders || [];
      this.categories = categories || [];
      this.computeMetrics();
      this.isLoading = false;
      setTimeout(() => this.createCharts(), 200);
    }).catch(err => {
      console.error('Erreur chargement données admin', err);
      this.isLoading = false;
    });
  }

  private computeMetrics(): void {
    this.metrics.totalUsers = this.users.length;
    this.metrics.totalFarmers = this.users.filter(u => u.role === 'AGRICULTEUR').length;
    this.metrics.totalDistributors = this.users.filter(u => u.role === 'DISTRIBUTEUR').length;
    this.metrics.totalAdmins = this.users.filter(u => u.role === 'ADMIN').length;
    this.metrics.totalProducts = this.products.length;
    this.metrics.totalOrders = this.orders.length;
    this.metrics.availableProducts = this.products.filter(p => p.statutProduit === 'DISPONIBLE').length;
    this.metrics.outOfStockProducts = this.products.filter(p => p.statutProduit === 'EPUISE').length;
    this.metrics.pendingOrders = this.orders.filter(o => o.statutCmd === 'ENCOURS').length;
    this.metrics.confirmedOrders = this.orders.filter(o => o.statutCmd === 'CONFIRMEE').length;
    this.metrics.cancelledOrders = this.orders.filter(o => o.statutCmd === 'ANNULEE').length;
    this.metrics.totalRevenue = this.orders.reduce((sum, o) => sum + (o.montantTotal || 0), 0);

    this.computeRevenueTimeline();
  }

  private computeRevenueTimeline(): void {
    const monthMap: { [key: string]: number } = {};
    const months = ['Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Juin', 'Juil', 'Aoû', 'Sep', 'Oct', 'Nov', 'Déc'];

    this.orders.forEach(o => {
      if (o.dateCommande && o.montantTotal) {
        const d = new Date(o.dateCommande);
        const key = `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}`;
        monthMap[key] = (monthMap[key] || 0) + o.montantTotal;
      }
    });

    const sorted = Object.keys(monthMap).sort();
    this.dateLabels = sorted.map(k => {
      const parts = k.split('-');
      return `${months[parseInt(parts[1]) - 1]} ${parts[0]}`;
    });
    this.revenueByMonth = sorted.map(k => monthMap[k]);
  }

  private createCharts(): void {
    this.destroyCharts();
    if (!this.roleChartCanvas) return;

    const colors = ['#4ade80', '#f59e0b', '#6366f1'];

    this.roleChart = new Chart(this.roleChartCanvas.nativeElement, {
      type: 'doughnut',
      data: {
        labels: ['Agriculteurs', 'Distributeurs', 'Admins'],
        datasets: [{
          data: [this.metrics.totalFarmers, this.metrics.totalDistributors, this.metrics.totalAdmins],
          backgroundColor: colors,
          borderWidth: 0
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: { legend: { position: 'bottom', labels: { usePointStyle: true, padding: 16 } } }
      }
    });

    this.orderChart = new Chart(this.orderChartCanvas.nativeElement, {
      type: 'doughnut',
      data: {
        labels: ['En cours', 'Confirmées', 'Annulées'],
        datasets: [{
          data: [this.metrics.pendingOrders, this.metrics.confirmedOrders, this.metrics.cancelledOrders],
          backgroundColor: ['#f59e0b', '#4ade80', '#ef4444'],
          borderWidth: 0
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: { legend: { position: 'bottom', labels: { usePointStyle: true, padding: 16 } } }
      }
    });

    const catMap: { [key: string]: number } = {};
    this.products.forEach(p => {
      const name = p.nomCategorie || 'Non catégorisé';
      catMap[name] = (catMap[name] || 0) + 1;
    });

    this.categoryChart = new Chart(this.categoryChartCanvas.nativeElement, {
      type: 'bar',
      data: {
        labels: Object.keys(catMap),
        datasets: [{
          label: 'Produits',
          data: Object.values(catMap),
          backgroundColor: '#4ade80',
          borderRadius: 8
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: { legend: { display: false } },
        scales: {
          y: { beginAtZero: true, ticks: { stepSize: 1 } },
          x: { grid: { display: false } }
        }
      }
    });

    this.revenueChart = new Chart(this.revenueChartCanvas.nativeElement, {
      type: 'line',
      data: {
        labels: this.dateLabels.length ? this.dateLabels : ['Aucune donnée'],
        datasets: [{
          label: 'Revenu (FCFA)',
          data: this.revenueByMonth.length ? this.revenueByMonth : [0],
          borderColor: '#4ade80',
          backgroundColor: 'rgba(74, 222, 128, 0.1)',
          fill: true,
          tension: 0.4,
          pointBackgroundColor: '#4ade80',
          pointBorderColor: '#fff',
          pointBorderWidth: 2,
          pointRadius: 5
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: { legend: { display: false } },
        scales: {
          y: { beginAtZero: true, ticks: { callback: (v: any) => v.toLocaleString() + ' F' } },
          x: { grid: { display: false } }
        },
        interaction: { intersect: false, mode: 'index' }
      }
    });
  }

  private destroyCharts(): void {
    if (this.roleChart) this.roleChart.destroy();
    if (this.orderChart) this.orderChart.destroy();
    if (this.categoryChart) this.categoryChart.destroy();
    if (this.revenueChart) this.revenueChart.destroy();
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (data) => { this.users = data; this.computeMetrics(); },
      error: (err) => console.error('Error loading users', err)
    });
  }

  onCreateUser(): void {
    if (!this.newUser.nom || !this.newUser.email || !this.newUser.password) {
      this.modal.show({ type: 'error', title: 'Champs obligatoires', message: 'Veuillez remplir les champs obligatoires.' });
      return;
    }
    this.creatingUser = true;
    this.userService.createUser(this.newUser).subscribe({
      next: () => {
        this.modal.show({ type: 'success', title: 'Utilisateur créé', message: 'Utilisateur créé avec succès !' });
        this.loadUsers();
        this.resetForm();
        this.showUserForm = false;
        this.creatingUser = false;
      },
      error: (err) => {
        this.modal.show({ type: 'error', title: 'Erreur', message: 'Erreur lors de la création de l\'utilisateur.' });
        this.creatingUser = false;
      }
    });
  }

  deleteUser(id: number): void {
    this.modal.show({
      type: 'confirm',
      title: 'Supprimer l\'utilisateur',
      message: 'Êtes-vous sûr de vouloir supprimer cet utilisateur ? Cette action est irréversible.',
      confirmText: 'Supprimer',
      onConfirm: () => {
        this.userService.deleteUser(id).subscribe({
          next: () => { this.loadUsers(); },
          error: (err) => console.error('Error deleting user', err)
        });
      }
    });
  }

  logout(): void {
    this.modal.show({
      type: 'confirm',
      title: 'Déconnexion',
      message: 'Êtes-vous sûr de vouloir vous déconnecter ?',
      confirmText: 'Se déconnecter',
      onConfirm: () => {
        this.authService.logout();
        this.router.navigate(['/']);
      }
    });
  }

  resetForm(): void {
    this.newUser = { nom: '', email: '', password: '', telephone: '', role: 'DISTRIBUTEUR', ville: '' };
  }

  get recentUsers(): any[] {
    return this.users.slice(0, 5);
  }

  get today(): string {
    const d = new Date();
    return d.toLocaleDateString('fr-FR', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric' });
  }

  get recentOrders(): any[] {
    return [...this.orders].sort((a, b) => {
      return new Date(b.dateCommande || 0).getTime() - new Date(a.dateCommande || 0).getTime();
    }).slice(0, 5);
  }

  statutClass(s: string): string {
    switch (s) {
      case 'ENCOURS': return 'bg-amber-100 text-amber-700';
      case 'CONFIRMEE': return 'bg-green-100 text-green-700';
      case 'ANNULEE': return 'bg-red-100 text-red-700';
      default: return 'bg-slate-100 text-slate-600';
    }
  }

  // ========== PAGINATION METHODS ==========

  get paginatedUsers(): any[] {
    const start = (this.userPage - 1) * this.userPageSize;
    return this.users.slice(start, start + this.userPageSize);
  }

  get userTotalPages(): number {
    return Math.ceil(this.users.length / this.userPageSize);
  }

  goToUserPage(page: number): void {
    if (page >= 1 && page <= this.userTotalPages) {
      this.userPage = page;
    }
  }

  get paginatedProducts(): any[] {
    const start = (this.productPage - 1) * this.productPageSize;
    return this.products.slice(start, start + this.productPageSize);
  }

  get productTotalPages(): number {
    return Math.ceil(this.products.length / this.productPageSize);
  }

  goToProductPage(page: number): void {
    if (page >= 1 && page <= this.productTotalPages) {
      this.productPage = page;
    }
  }

  get paginatedOrders(): any[] {
    const start = (this.orderPage - 1) * this.orderPageSize;
    return this.orders.slice(start, start + this.orderPageSize);
  }

  get orderTotalPages(): number {
    return Math.ceil(this.orders.length / this.orderPageSize);
  }

  goToOrderPage(page: number): void {
    if (page >= 1 && page <= this.orderTotalPages) {
      this.orderPage = page;
    }
  }
}
