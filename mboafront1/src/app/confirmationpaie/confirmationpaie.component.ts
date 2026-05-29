import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-confirmationpaie',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './confirmationpaie.component.html',
  styleUrl: './confirmationpaie.component.css'
})
export class ConfirmationpaieComponent implements OnInit {
  orderData: any = null;
  maskedPhone: string = '';

  constructor(private router: Router) {
    const navigation = this.router.getCurrentNavigation();
    this.orderData = navigation?.extras.state?.['data'];
  }

  ngOnInit() {
    if (!this.orderData) {
      this.router.navigate(['/home']);
      return;
    }

    if (this.orderData.phoneNumber) {
      const phone = this.orderData.phoneNumber;
      // Requirement: showing last two digits
      const length = phone.length;
      if (length > 2) {
        this.maskedPhone = `+237 ${phone.substring(0, 1)}••••••${phone.substring(length - 2)}`;
      } else {
        this.maskedPhone = `+237 ${phone}`;
      }
    }
  }
}
