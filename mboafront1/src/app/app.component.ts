import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AppModalComponent } from './components/app-modal/app-modal.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, AppModalComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'mboafront1';
}
