import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';

import { AuthService } from '../services/auth.service';
import { ModalService } from '../services/modal.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule], // ✅ IMPORTANT
  templateUrl: './login.component.html', // ⚠️ CORRIGÉ
  styleUrl: './login.component.css'   // ⚠️ CORRIGÉ
})
export class LoginComponent implements OnInit {

  email: string = '';
  password: string = '';

  isRegistering: boolean = false;
  registerData = {
    nom: '',
    email: '',
    password: '',
    telephone: '',
    role: 'DISTRIBUTEUR',
    ville: ''
  };

  constructor(
    private authService: AuthService,
    private router: Router,
    private modal: ModalService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      if (params['register'] === 'true') {
        this.isRegistering = true;
      }
    });
  }

  toggleMode() {
    this.isRegistering = !this.isRegistering;
  }

  onRegister() {
    if (!this.registerData.email || !this.registerData.password || !this.registerData.nom) {
      this.modal.show({ type: 'error', title: 'Champs obligatoires', message: 'Veuillez remplir tous les champs obligatoires' });
      return;
    }

    this.authService.register(this.registerData).subscribe({
      next: (res: any) => {
        this.modal.show({ type: 'success', title: 'Compte créé !', message: 'Votre compte a été créé avec succès. Vous pouvez maintenant vous connecter.' });
        this.isRegistering = false;
        this.email = this.registerData.email;
      },
      error: (err) => {
        this.modal.show({ type: 'error', title: 'Erreur', message: "Erreur lors de la création du compte. L'email est peut-être déjà utilisé." });
      }
    });
  }

  onLogin() {
    const data = {
      email: this.email,
      password: this.password
    };

    this.authService.login(data).subscribe({
      next: (res: any) => {
        if (res.role === "DISTRIBUTEUR") {
          this.router.navigate(['/home']);

        } else if (res.role === "AGRICULTEUR") {
          this.router.navigate(['/farmer']);
          
        } else if (res.role === "ADMIN") {
          this.router.navigate(['/admin']);

        } else {
          this.modal.show({ type: 'error', title: 'Rôle inconnu', message: 'Connexion réussie mais rôle inconnu' });
        }
      },
      error: (err) => {
        this.modal.show({ type: 'error', title: 'Erreur de connexion', message: err.error?.message || 'Email ou mot de passe incorrect.' });
      }
    });
  }
}
