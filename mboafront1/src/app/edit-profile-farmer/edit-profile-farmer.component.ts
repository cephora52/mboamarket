import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { ModalService } from '../services/modal.service';

@Component({
  selector: 'app-edit-profile-farmer',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './edit-profile-farmer.component.html',
  styleUrl: './edit-profile-farmer.component.css'
})
export class EditProfileFarmerComponent implements OnInit {
  user: any = {
    nom: '',
    telephone: '',
    ville: '',
    email: '',
    bio: '',
    photo: '',
    password: ''
  };
  
  passwordConfirm: string = '';
  loading = false;
  errorMessage: string | null = null;
  showLogoutModal = false;
  previewPhoto: string | null = null;

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
    this.user.password = '';
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.previewPhoto = e.target.result;
        this.user.photo = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }

  removePhoto() {
    this.previewPhoto = null;
    this.user.photo = '';
  }

  saveProfile() {
    this.errorMessage = null;

    const hasPasswordInput = (this.user.password && this.user.password.trim() !== '') || 
                             (this.passwordConfirm && this.passwordConfirm.trim() !== '');

    if (hasPasswordInput && this.user.password !== this.passwordConfirm) {
      this.errorMessage = "Les mots de passe ne correspondent pas.";
      return;
    }

    const userId = this.authService.getUserId();
    if (!userId) return;

    this.loading = true;

    const payload: any = {};
    if (this.user.nom) payload.nom = this.user.nom;
    if (this.user.telephone) payload.telephone = this.user.telephone;
    if (this.user.ville) payload.ville = this.user.ville;
    if (this.user.email) payload.email = this.user.email;
    if (this.user.bio) payload.bio = this.user.bio;
    if (this.user.password && this.user.password.trim() !== '') payload.password = this.user.password;
    payload.photo = this.user.photo || null;

    this.authService.updateUser(userId, payload).subscribe({
      next: (updatedUser) => {
        this.loading = false;
        this.modal.show({
          type: 'success',
          title: 'Profil mis à jour',
          message: 'Vos modifications ont été enregistrées avec succès.'
        });
        this.router.navigate(['/settings-farmer']);
      },
      error: (err) => {
        this.loading = false;
        console.error('Erreur lors de la mise à jour du profil', err);
        this.modal.show({
          type: 'error',
          title: 'Erreur',
          message: err.error?.message || 'Erreur lors de la mise à jour du profil.'
        });
      }
    });
  }

  cancel() {
    this.router.navigate(['/settings-farmer']);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
