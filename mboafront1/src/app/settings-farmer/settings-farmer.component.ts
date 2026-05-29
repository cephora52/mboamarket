import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { CommandeService } from '../services/commande.service';

@Component({
  selector: 'app-settings-farmer',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './settings-farmer.component.html',
  styleUrl: './settings-farmer.component.css'
})
export class SettingsFarmerComponent implements OnInit {
  user: any;
  totalRevenue: string = '0';
  memberSince: string = 'Avril 2026';
  showLogoutModal = false;

  constructor(
    public authService: AuthService,
    private commandeService: CommandeService,
    public router: Router
  ) {}

  ngOnInit() {
    this.user = this.authService.getUser();
    const userId = this.authService.getUserId();

    if (!userId) {
      this.router.navigate(['/']);
      return;
    }

    // Capture dynamic Member Since from backend DTO if available
    if (this.user.dateCreation) {
      this.memberSince = this.user.dateCreation;
    }

    // Calculate Real Total Sales (Revenue)
    this.commandeService.getCommandesByAgriculteur(userId).subscribe({
      next: (commandes: any[]) => {
        const total = (commandes || []).reduce((acc, c) => acc + (c.montantTotal || 0), 0);
        this.totalRevenue = new Intl.NumberFormat().format(total);
      },
      error: (err) => console.error('Error loading revenue', err)
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
