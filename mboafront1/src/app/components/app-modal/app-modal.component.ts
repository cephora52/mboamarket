import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalService, ModalConfig } from '../../services/modal.service';

@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="modal-overlay" *ngIf="modal" (click)="close()">
      <div class="modal-content" (click)="$event.stopPropagation()" style="max-width:420px">
        <div class="flex flex-col items-center text-center py-2">

          <div class="w-16 h-16 rounded-2xl flex items-center justify-center mb-4"
               [ngClass]="iconBgClass">
            <i class="fa-solid text-[28px]" [ngClass]="iconClass"></i>
          </div>

          <h3 class="text-lg font-black text-slate-900 mb-2">{{ modal.title }}</h3>
          <p class="text-sm text-slate-400 font-medium mb-6 max-w-xs leading-relaxed">{{ modal.message }}</p>

          <div class="flex gap-3 w-full">
            <button *ngIf="modal.cancelText !== undefined" class="btn-secondary flex-1" (click)="close()">
              {{ modal.cancelText ?? 'Annuler' }}
            </button>
            <button class="flex-1 px-6 py-3 font-black rounded-2xl text-sm transition-all shadow-lg text-white"
                    [ngClass]="confirmBtnClass"
                    (click)="confirm()">
              {{ modal.confirmText ?? (modal.type === 'confirm' ? 'Confirmer' : 'OK') }}
            </button>
          </div>

        </div>
      </div>
    </div>
  `
})
export class AppModalComponent {
  modal: ModalConfig | null = null;

  constructor(private modalService: ModalService) {
    this.modalService.modal$.subscribe((m: ModalConfig | null) => this.modal = m);
  }

  get iconBgClass() {
    switch (this.modal?.type) {
      case 'success': return 'bg-green-50';
      case 'error': return 'bg-red-50';
      case 'confirm': return 'bg-amber-50';
      case 'info': return 'bg-blue-50';
      default: return 'bg-slate-50';
    }
  }

  get iconClass() {
    switch (this.modal?.type) {
      case 'success': return 'fa-check-circle text-green-600';
      case 'error': return 'fa-circle-xmark text-red-500';
      case 'confirm': return 'fa-triangle-exclamation text-amber-600';
      case 'info': return 'fa-circle-info text-blue-600';
      default: return 'fa-circle-info text-slate-500';
    }
  }

  get confirmBtnClass() {
    switch (this.modal?.type) {
      case 'success': case 'info': return 'bg-green-600 hover:bg-green-700 shadow-green-500/25';
      case 'error': case 'confirm': return 'bg-red-600 hover:bg-red-700 shadow-red-500/25';
      default: return 'bg-green-600 hover:bg-green-700 shadow-green-500/25';
    }
  }

  close() {
    this.modalService.close();
  }

  confirm() {
    if (this.modal?.onConfirm) this.modal.onConfirm();
    this.close();
  }
}
