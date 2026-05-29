import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

export interface ModalConfig {
  type: 'success' | 'error' | 'confirm' | 'info';
  title: string;
  message: string;
  confirmText?: string;
  cancelText?: string;
  icon?: string;
  onConfirm?: () => void;
}

@Injectable({ providedIn: 'root' })
export class ModalService {
  private modalSubject = new Subject<ModalConfig | null>();
  modal$ = this.modalSubject.asObservable();

  show(config: ModalConfig) {
    this.modalSubject.next(config);
  }

  close() {
    this.modalSubject.next(null);
  }
}
