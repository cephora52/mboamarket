import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { ModalService } from '../services/modal.service';

@Component({
  selector: 'app-settings-distributeur',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './settings-distributeur.component.html',
  styleUrl: './settings-distributeur.component.css'
})
export class SettingsDistributeurComponent implements OnInit {
  user: any = null;
  showLogoutModal = false;
  notificationsEnabled = true;
  emailNotifications = true;

  constructor(
    private authService: AuthService,
    private modal: ModalService,
    private router: Router
  ) {}

  ngOnInit() {
    const sessionUser = this.authService.getUser();
    if (!sessionUser) {
      this.router.navigate(['/']);
      return;
    }
    
    this.user = { ...sessionUser };
  }

  goToEditProfile() {
    this.router.navigate(['/edit-profile-distributeur']);
  }

  confirmLogout() {
    this.modal.show({
      type: 'confirm',
      title: 'Déconnexion',
      message: 'Êtes-vous sûr de vouloir vous déconnecter ?',
      confirmText: 'Déconnecter',
      onConfirm: () => {
        this.logout();
      }
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  toggleNotifications() {
    this.notificationsEnabled = !this.notificationsEnabled;
  }

  toggleEmailNotifications() {
    this.emailNotifications = !this.emailNotifications;
  }

  deleteAccount() {
    this.modal.show({
      type: 'confirm',
      title: 'Supprimer le compte',
      message: 'Cette action est irréversible. Tous vos données seront supprimées définitivement. Êtes-vous sûr ?',
      confirmText: 'Supprimer',
      onConfirm: () => {
        const userId = this.authService.getUserId();
        if (userId) {
          this.authService.deleteUser(userId).subscribe({
            next: () => {
              this.modal.show({
                type: 'success',
                title: 'Compte supprimé',
                message: 'Votre compte a été supprimé avec succès.'
              });
              this.logout();
            },
            error: (err) => {
              this.modal.show({
                type: 'error',
                title: 'Erreur',
                message: 'Impossible de supprimer le compte.'
              });
            }
          });
        }
      }
    });
  }
}
