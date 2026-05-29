import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { FarmerComponent } from './farmer/farmer.component';
import { AddProductComponent } from './add-product/add-product.component';
import { ProduitsComponent } from './produits/produits.component';
import { UpdateProductComponent } from './update-product/update-product.component';
import { AllProductDistComponent } from './all-product-dist/all-product-dist.component';
import { FavoritesDistComponent } from './favorites-dist/favorites-dist.component';
import { PanierComponent } from './panier/panier.component';
import { PaiementComponent } from './paiement/paiement.component';
import { ConfirmationpaieComponent } from './confirmationpaie/confirmationpaie.component';
import { CommandeFarmerComponent } from './commande-farmer/commande-farmer.component';
import { CommandeDistributeurComponent } from './commande-distributeur/commande-distributeur.component';
import { SettingsFarmerComponent } from './settings-farmer/settings-farmer.component';
import { EditProfileFarmerComponent } from './edit-profile-farmer/edit-profile-farmer.component';
import { SettingsDistributeurComponent } from './settings-distributeur/settings-distributeur.component';
import { EditProfileDistributeurComponent } from './edit-profile-distributeur/edit-profile-distributeur.component';
import { ChatComponent } from './chat/chat.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'farmer', component: FarmerComponent },
  { path: 'settings-farmer', component: SettingsFarmerComponent },
  { path: 'edit-profile-farmer', component: EditProfileFarmerComponent },
  { path: 'settings-distributeur', component: SettingsDistributeurComponent },
  { path: 'edit-profile-distributeur', component: EditProfileDistributeurComponent },
  { path: 'commande-farmer', component: CommandeFarmerComponent },
  { path: 'commande-distributeur', component: CommandeDistributeurComponent },
  { path: 'add-product', component: AddProductComponent },
  { path: 'update-product/:id', component: UpdateProductComponent },
  { path: 'produits', component: ProduitsComponent },
  { path: 'produit/:id', component: ProductDetailComponent },
  { path: 'all-product-dist', component: AllProductDistComponent },
  { path: 'favorites', component: FavoritesDistComponent },
  { path: 'panier', component: PanierComponent },
  { path: 'paiement', component: PaiementComponent },
  { path: 'confirmationpaie', component: ConfirmationpaieComponent },
  { path: 'messages', component: ChatComponent },
  { path: 'admin', component: AdminDashboardComponent },

  { path: '**', redirectTo: '' }
];
